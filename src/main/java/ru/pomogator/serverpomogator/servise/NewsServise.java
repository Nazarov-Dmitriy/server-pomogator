package ru.pomogator.serverpomogator.servise;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.domain.dto.news.NewsResponse;
import ru.pomogator.serverpomogator.domain.mapper.NewsMapper;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteKey;
import ru.pomogator.serverpomogator.domain.model.news.FavoriteModel;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.repository.favorite.FavoriteNewsRepository;
import ru.pomogator.serverpomogator.repository.news.CategoryRepository;
import ru.pomogator.serverpomogator.repository.news.NewsRepository;
import ru.pomogator.serverpomogator.repository.subscribe.SubcribeRepository;
import ru.pomogator.serverpomogator.repository.tags.TagsRepository;
import ru.pomogator.serverpomogator.repository.user.UserRepository;
import ru.pomogator.serverpomogator.servise.mail.EmailService;
import ru.pomogator.serverpomogator.utils.FileCreate;
import ru.pomogator.serverpomogator.utils.FileDelete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServise {
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final TagsRepository tagsRepository;
    private final CategoryRepository categoryRepository;
    private final NewsMapper newsMapper;
    private final FavoriteNewsRepository favoriteRepository;
    private final SubcribeRepository subcribeRepository;

    @Autowired
    EmailService emailService;

    @Transactional
    public ResponseEntity<Void> addNews(NewsRequest req, MultipartFile file) {
        try {
            if (req.getVideo().isEmpty() && file == null) {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("file", "Поле не может быть пустым");
                throw new BadRequest("error", errors);
            }
            var news = newsRepository.save(newsMapper.toNewsModel(req));
            if (file != null) {
                var path = new StringBuilder();
                path.append("files/news/").append(news.getId()).append("/");
                var new_file = FileCreate.addFile(file, path);
                news.setFile(new_file);
                newsRepository.save(news);
            }
            var subsribeUsers = subcribeRepository.findAll();
            var pathMaterial = "/blog/article/" + news.getId();
            if (!subsribeUsers.isEmpty()) {
                    emailService.sendMessageMaterial(subsribeUsers, pathMaterial, news);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);
        }
    }

    @Transactional
    public ResponseEntity<Void> editNews(NewsRequest req, MultipartFile file) {
        try {
            var news = newsRepository.findById(req.getId());
            NewsModel edit_news = null;

            if (news.isPresent()) {
                edit_news = newsRepository.save(newsMapper.partialUpdate(req, news.get()));
                var currentFile = news.get().getFile();

                if (file != null) {

                    if (currentFile != null) {
                        var pathFile = (currentFile.getPath().split("/"));
                        var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
                        FileDelete.deleteFile(directoryPath);
                    }

                    var path = new StringBuilder();
                    path.append("files/news/").append(edit_news.getId()).append("/");
                    var new_file = FileCreate.addFile(file, path);
                    edit_news.setFile(new_file);
                    edit_news.setVideo(null);
                }

                if (!req.getVideo().isEmpty()) {
                    if (currentFile != null) {
                        var pathFile = (currentFile.getPath().split("/"));
                        var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
                        FileDelete.deleteFile(directoryPath);
                    }
                    edit_news.setFile(null);
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);        }
    }

    @Transactional
    public ResponseEntity<?> get(Long id, Long user_id) {
        try {
            var news = newsRepository.findById(id);
            if (news.isPresent()) {
                return new ResponseEntity<>(newsMapper.toNewsResponse(news.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);        }
    }

    public ResponseEntity<?> list(Long category, List<String> tags) {
        try {
            List<NewsModel> news = null;
            var list = new ArrayList<NewsResponse>();
            if (category != null) {
                news = newsRepository.findByCategoryId(category);
            } else if (tags != null) {
                news = newsRepository.findByTagsIn(tags);

            } else {
                news = newsRepository.findAll();
            }

            if (!news.isEmpty()) {
                for (var item : news) {
                    list.add(newsMapper.toNewsResponse(item));
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getTags() {
        try {
            return new ResponseEntity<>(tagsRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);        }
    }

    public ResponseEntity<?> getCategory() {
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);        }
    }

    public ResponseEntity<?> setShow(Long id) {
        var news = newsRepository.findById(id);
        if (news.isPresent()) {
            news.get().setShows(news.get().getShows() + 1);
            newsRepository.save(news.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> setLike(Long id, Integer like, Integer dislike) {
        var news = newsRepository.findById(id).orElseThrow();
        if (like != null) {
            news.setLikes(news.getLikes() + like);
        } else if (dislike != null) {
            if (news.getLikes() - dislike > 0) {
                news.setLikes(news.getLikes() - dislike);
            } else {
                news.setLikes(0);
            }
        }
        newsRepository.save(news);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> remove(Long id) {
        try {
            var news = newsRepository.findById(id);
            newsRepository.deleteById(id);

            if (news.isPresent() && news.get().getFile() != null) {
                var pathFile = (news.get().getFile().getPath().split("/"));
                var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
                FileDelete.deleteFile(directoryPath, true);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> addNewsFavorite(Long news_id, Long user_id) {
        try {
            var favoriteKey = new FavoriteKey(news_id, user_id);
            var favorite = new FavoriteModel(favoriteKey);
            favoriteRepository.save(favorite);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    public ResponseEntity<?> removeNewsFavorite(Long news_id, Long user_id) {
        try {
            var favoriteKey = new FavoriteKey(news_id, user_id);
            favoriteRepository.deleteByPkFavorite(favoriteKey);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getNewsFavorite(Long news_id, Long user_id) {
        try {
            var favoriteKey = new FavoriteKey(news_id, user_id);
            var favorite = favoriteRepository.findByPkFavorite(favoriteKey);
            if (favorite != null) {
                return new ResponseEntity<>(favorite.getFavorite(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getFavoriteNewsUser(Long id, List<TagsModel> tags) {
        try {
            List<FavoriteModel> newsFavorite = null;
            if (tags != null) {
                newsFavorite = favoriteRepository.findByPkFavorite_UserIdAndNews_TagsIn(id, tags);
            } else {
                newsFavorite = favoriteRepository.findByPkFavorite_UserId(id);
            }

            var list = new ArrayList<NewsResponse>();
            if (newsFavorite != null) {
                for (var item : newsFavorite) {
                    list.add(newsMapper.toNewsResponse(item.getNews()));
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> listActual() {
        try {
            var list = new ArrayList<NewsResponse>();
            var news = newsRepository.findTop3ByOrderByCreatedAtDesc();

            if (!news.isEmpty()) {
                for (var item : news) {
                    list.add(newsMapper.toNewsResponse(item));
                }
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

