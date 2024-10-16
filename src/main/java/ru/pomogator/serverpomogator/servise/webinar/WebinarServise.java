package ru.pomogator.serverpomogator.servise.webinar;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarRequest;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarResponse;
import ru.pomogator.serverpomogator.domain.mapper.WebinarMapper;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarKey;
import ru.pomogator.serverpomogator.domain.model.webinar.FavoriteWebinarModel;
import ru.pomogator.serverpomogator.domain.model.webinar.WebinarModel;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.favorite.FavoriteWebinarRepository;
import ru.pomogator.serverpomogator.repository.news.CategoryRepository;
import ru.pomogator.serverpomogator.repository.news.NewsRepository;
import ru.pomogator.serverpomogator.repository.subscribe.SubcribeRepository;
import ru.pomogator.serverpomogator.repository.tags.TagsRepository;
import ru.pomogator.serverpomogator.repository.user.UserRepository;
import ru.pomogator.serverpomogator.repository.webinar.WebinarRepository;
import ru.pomogator.serverpomogator.servise.mail.EmailService;
import ru.pomogator.serverpomogator.utils.FileCreate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WebinarServise {
    private final WebinarRepository webinarRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final TagsRepository tagsRepository;
    private final CategoryRepository categoryRepository;
    private final WebinarMapper webinarMapper;
    private final SubcribeRepository subcribeRepository;
    private final FavoriteWebinarRepository favoriteWebinarRepository;

    @Autowired
    EmailService emailService;

    @Transactional
    public ResponseEntity<Void> addWebinar(WebinarRequest req, MultipartFile preview_img) {
        try {
            if (preview_img != null) {
                var webinar = webinarRepository.save(webinarMapper.toWebinarModel(req));
                var path = new StringBuilder();
                path.append("files/webinar/").append(webinar.getId()).append("/");
                var new_file = FileCreate.addFile(preview_img, path);
                webinar.setPreview_img(new_file);
            } else {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("preview_img", "Файл не может быть пустым");
                throw new BadRequest("error", errors);
            }

//            var subsribe = subcribeRepository.findAll();
//            var pathMaterial = "/blog/article/" + news.getId();
//            if (!subsribe.isEmpty()) {
//                for (var item : subsribe) {
//                    emailService.sendMessageMaterial(item.getEmail(), news.getTitle(), news.getAnnotation(), pathMaterial);
//                }
//            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<?> list(List<String> tags) {
        try {
            List<WebinarModel> webinar = null;
            var list = new ArrayList<WebinarResponse>();
            if (tags != null) {
                webinar = webinarRepository.findByTagsIn(tags);
            } else {
                webinar = webinarRepository.findAll();
            }

            if (!webinar.isEmpty()) {
                for (var item : webinar) {
                    list.add(webinarMapper.toWebinarResponse(item));
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> get(Long id) {
        try {
            var webinar = webinarRepository.findById(id);
            if (webinar.isPresent()) {
                return new ResponseEntity<>(webinarMapper.toWebinarResponse(webinar.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<?> setShow(Long id) {
        var webinar = webinarRepository.findById(id);
        if (webinar.isPresent()) {
            webinar.get().setShows(webinar.get().getShows() + 1);
            webinarRepository.save(webinar.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> setLike(Long id, Integer like, Integer dislike) {
        var webinar = webinarRepository.findById(id);
        if (webinar.isPresent()) {
            if (like != null) {
                webinar.get().setLikes(webinar.get().getLikes() + like);
            } else if (dislike != null) {
                if (webinar.get().getLikes() - dislike > 0) {
                    webinar.get().setLikes(webinar.get().getLikes() - dislike);
                } else {
                    webinar.get().setLikes(0);
                }
            }
            webinarRepository.save(webinar.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> addWebinarFavorite(Long webinar_id, Long user_id) {
        try {
            var favoriteKey = new FavoriteWebinarKey(webinar_id, user_id);
            var favorite = new FavoriteWebinarModel(favoriteKey);
            System.out.println(favoriteKey);
            System.out.println(favorite);
            System.out.println(webinar_id);
            System.out.println(user_id);
            favoriteWebinarRepository.save(favorite);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getWebinarFavorite(Long webinar_id, Long user_id) {
        try {
            var favoriteKey = new FavoriteWebinarKey(webinar_id, user_id);
            var favorite = favoriteWebinarRepository.findByPkFavorite(favoriteKey);
            if (favorite != null) {
                return new ResponseEntity<>(favorite.getFavorite(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> removeWebinarFavorite(Long webinar_id, Long user_id) {
        try {
            var favoriteKey = new FavoriteWebinarKey(webinar_id, user_id);
            favoriteWebinarRepository.deleteByPkFavorite(favoriteKey);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getFavoriteWebinarUser(Long id, List<TagsModel> tags) {
        try {
            List<FavoriteWebinarModel> newsFavorite = null;
            if (tags != null) {
                newsFavorite = favoriteWebinarRepository.findByPkFavorite_UserIdAndWebinar_TagsIn(id, tags);
            } else {
                newsFavorite = favoriteWebinarRepository.findByPkFavorite_UserId(id);
            }

            var list = new ArrayList<WebinarResponse>();
            if (newsFavorite != null) {
                for (var item : newsFavorite) {
                    list.add(webinarMapper.toWebinarResponse(item.getWebinar()));
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    public ResponseEntity<?> userNews(Long id, List<String> tags) {
//        try {
//            List<NewsModel> news = null;
//
//            if (tags != null) {
//                news = newsRepository.findByAuthorIdAndTagsIn(id, tags);
//            } else {
//                news = newsRepository.findByAuthorId(id);
//            }
//
//            var list = new ArrayList<NewsResponse>();
//            if (!news.isEmpty()) {
//                for (var item : news) {
//                    list.add(newsMapper.toNewsResponse(item));
//                }
//            }
//            return new ResponseEntity<>(list, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @Transactional
//    public ResponseEntity<Void> editNews(NewsRequest req, MultipartFile file) {
//        try {
//            var news = newsRepository.findById(req.getId());
//            NewsModel edit_news = null;
//
//
//            if (news.isPresent()) {
//                edit_news = newsRepository.save(newsMapper.partialUpdate(req, news.get()));
//                var currentFile = news.get().getFile();
//
//                if (file != null) {
//
//                    if (currentFile != null) {
//                        var pathFile = (currentFile.getPath().split("/"));
//                        var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
//                        FileDelete.deleteFile(directoryPath);
//                    }
//
//                    var path = new StringBuilder();
//                    path.append("files/news/").append(edit_news.getId()).append("/");
//                    var new_file = FileCreate.addFile(file, path);
//                    edit_news.setFile(new_file);
//                    edit_news.setVideo(null);
//                }
//
//                if (!req.getVideo().isEmpty()) {
//                    if (currentFile != null) {
//                        var pathFile = (currentFile.getPath().split("/"));
//                        var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
//                        FileDelete.deleteFile(directoryPath);
//                    }
//                    edit_news.setFile(null);
//                }
//            }
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            throw new InternalServerError("Error input data");
//        }
//    }
//


//

//

//
//    @Transactional
//    public ResponseEntity<?> remove(Long id) {
//        try {
//            var news = newsRepository.findById(id);
//
//            newsRepository.deleteById(id);
//
//            if (news.isPresent()) {
//                var pathFile = (news.get().getFile().getPath().split("/"));
//                var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
//                FileDelete.deleteFile(directoryPath, true);
//            }
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//

//


}

