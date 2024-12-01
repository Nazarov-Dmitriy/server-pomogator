package ru.pomogator.serverpomogator.servise.webinar;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarRequest;
import ru.pomogator.serverpomogator.domain.dto.webinar.WebinarResponse;
import ru.pomogator.serverpomogator.domain.mapper.WebinarMapper;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.domain.model.sertificat.CertificateModel;
import ru.pomogator.serverpomogator.domain.model.user.User;
import ru.pomogator.serverpomogator.domain.model.webinar.*;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.exception.InternalServerError;
import ru.pomogator.serverpomogator.repository.certificate.CertificateRepository;
import ru.pomogator.serverpomogator.repository.favorite.FavoriteWebinarRepository;
import ru.pomogator.serverpomogator.repository.subscribe.SubcribeRepository;
import ru.pomogator.serverpomogator.repository.webinar.WebinarRepository;
import ru.pomogator.serverpomogator.repository.webinar.WebinarSubscribeRepository;
import ru.pomogator.serverpomogator.servise.mail.EmailService;
import ru.pomogator.serverpomogator.utils.FileCreate;
import ru.pomogator.serverpomogator.utils.FileDelete;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WebinarServise {
    private final WebinarRepository webinarRepository;
    private final WebinarMapper webinarMapper;
    private final SubcribeRepository subcribeRepository;
    private final FavoriteWebinarRepository favoriteWebinarRepository;
    private final CertificateRepository certificateRepository;
    private final WebinarSubscribeRepository webinarSubscribeRepository;

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

                ZonedDateTime date = ZonedDateTime.ofInstant(req.getDate_translation().toInstant(),
                        ZoneId.systemDefault());
                var dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm (МСК)").format(date);

                var subscribeUsers = subcribeRepository.findAll();
                var pathMaterial = "/webinar/" + webinar.getId();

                if (!subscribeUsers.isEmpty()) {
                    emailService.sendMessageWebinar(subscribeUsers, pathMaterial, webinar, dateFormat);
                }
            } else {
                HashMap<String, String> errors = new HashMap<>();
                errors.put("preview_img", "Файл не может быть пустым");
                throw new BadRequest("error", errors);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);
        }
    }

    public ResponseEntity<?> list(List<String> tags, String typePublished) {
        try {
            List<WebinarModel> webinar = null;
            var list = new ArrayList<WebinarResponse>();

            if (typePublished != null) {
                if (typePublished.equals("all")) {
                    if (tags != null) {
                        webinar = webinarRepository.findByTagsIn(tags);
                    } else {
                        webinar = webinarRepository.findAll();
                    }
                } else {
                    var published = typePublished.equals("true");
                    if (tags != null) {
                        webinar = webinarRepository.findByTagsInAndPublished(tags, published);
                    } else {
                        webinar = webinarRepository.findByPublished(published);
                    }
                }
            } else {
                if (tags != null) {
                    webinar = webinarRepository.findByTagsInAndPublished(tags, true);
                } else {
                    webinar = webinarRepository.findByPublished(true);
                }
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
            HashMap<String, String> errors = new HashMap<>();
            errors.put("error", "ошибка данных");
            throw new BadRequest("error", errors);
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

    public ResponseEntity<?> editWebinar(WebinarRequest req, MultipartFile previewImg) {
        try {
            var webinar = webinarRepository.findById(req.getId());
            WebinarModel edit_news;

            if (webinar.isPresent()) {
                edit_news = webinarRepository.save(webinarMapper.partialUpdate(req, webinar.get()));
                var currentFile = webinar.get().getPreview_img();

                if (previewImg != null) {
                    if (currentFile != null) {
                        var pathFile = (currentFile.getPath().split("/"));
                        var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
                        FileDelete.deleteFile(directoryPath);
                    }

                    var path = new StringBuilder();
                    path.append("files/webinar/").append(edit_news.getId()).append("/");
                    var new_file = FileCreate.addFile(previewImg, path);
                    edit_news.setPreview_img(new_file);
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            throw new InternalServerError("Error input data");
        }
    }

    @Transactional
    public ResponseEntity<?> remove(Long id) {
        try {
            var webinar = webinarRepository.findById(id);
            var webinar_subscribers = webinarSubscribeRepository.findByPkSubscribe_WebinarId(id);

            if (webinar_subscribers != null) {
                webinarSubscribeRepository.deleteAll(webinar_subscribers);
            }
            webinarRepository.deleteById(id);

            if (webinar.isPresent()) {
                var pathFile = (webinar.get().getPreview_img().getPath().split("/"));
                var directoryPath = pathFile[0] + "/" + pathFile[1] + "/" + pathFile[2];
                FileDelete.deleteFile(directoryPath, true);
            }
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> subscribeWebinar(Long webinarId, User user) {
        try {
            var webinar = webinarRepository.findById(webinarId);
            if (webinar.isPresent()) {
                var subscribeWebinarKey = new SubscribeWebinarKey(webinarId, user.getId());
                var subscribe = new SubscribeWebinarModel(subscribeWebinarKey);
                webinarSubscribeRepository.save(subscribe);
                return new ResponseEntity<>(true, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getSubscribeUser(Long webinarId, Long user) {
        try {
            var pk = new SubscribeWebinarKey(webinarId, user);

            var subscriber = webinarSubscribeRepository.findByPkSubscribe(pk);
            if (subscriber != null) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> setStatus(Long webinarId) {
        try {
            var webinar = webinarRepository.findById(webinarId);
            webinar.ifPresent(webinarModel -> webinarModel.setStatus(Status.completed));
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            var subscribers = webinarSubscribeRepository.findByPkSubscribe_WebinarId(webinarId);
            if (subscribers != null && webinar.isPresent()) {
                for (var subscriber : subscribers) {
                    var certificate = new CertificateModel();
                    certificate.setTitle(webinar.get().getTitle());
                    certificate.setDate(df.format(new Date()));
                    certificate.setUser(subscriber.getUser());
                    certificateRepository.save(certificate);
                }
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Scheduled(fixedDelayString = "PT02H")
    public void computePrice() {
        var webinar = webinarRepository.findByStatus(Status.create);
        if (!webinar.isEmpty()) {
            var currentDate = new Date();
            for (var item : webinar) {
                var differenceTime = item.getDate_translation().getTime() - currentDate.getTime();
                if (differenceTime <= 7_200_200 && differenceTime >= 0) {
                    var subscribers = webinarSubscribeRepository.findByPkSubscribe_WebinarId(item.getId());

                    if (subscribers != null) {
                        ZonedDateTime date = ZonedDateTime.ofInstant(item.getDate_translation().toInstant(),
                                ZoneId.systemDefault());
                        var dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm (МСК)").format(date);
                        var pathMaterial = "/webinar/" + item.getId();
                        emailService.sendRemindersWebinar(subscribers, pathMaterial, item, dateFormat);
                    }
                }
            }
        }
    }

    @Transactional
    public ResponseEntity<?> setPublished(Long id) {
        try {
            var webinar = webinarRepository.findById(id);
            if (webinar.isPresent()) {
                webinar.get().setPublished(!webinar.get().isPublished());
                webinarRepository.save(webinar.get());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerError("Error");
        }
    }
}

