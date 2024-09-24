package ru.pomogator.serverpomogator.servise;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pomogator.serverpomogator.domain.dto.news.NewsRequest;
import ru.pomogator.serverpomogator.domain.dto.news.NewsResponse;
import ru.pomogator.serverpomogator.domain.mapper.NewsMapper;
import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
import ru.pomogator.serverpomogator.domain.model.news.TagsModel;
import ru.pomogator.serverpomogator.exception.BadRequest;
import ru.pomogator.serverpomogator.repository.UserRepository;
import ru.pomogator.serverpomogator.repository.news.CategoryRepository;
import ru.pomogator.serverpomogator.repository.news.NewsRepository;
import ru.pomogator.serverpomogator.repository.news.TagsRepository;
import ru.pomogator.serverpomogator.utils.FileCreate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServise {
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final TagsRepository tagsRepository;
    private final CategoryRepository categoryRepository;
    private final NewsMapper newsMapper;

    @Transactional
    public ResponseEntity<Void> addNews(NewsRequest req, MultipartFile file) {
        try {
            var news = newsRepository.save(newsMapper.toNewsModel(req));
            if (file != null) {
                var path = new StringBuilder();
                path.append("files/news/").append(news.getId()).append("/");
                var new_file = FileCreate.addFile(file, path);
                news.setFile(new_file);
            }

            if (file == null && req.getVideo().equals("null")) {
                throw new BadRequest("Error input data");
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest("Error input data");
        }
    }

    @Transactional
    public ResponseEntity<?> get(Long id) {
        try {
            var news = newsRepository.findById(id).orElseThrow();
            return new ResponseEntity<>(newsMapper.toNewsResponse(news), HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<?> list(Long category, List<String> tags) {
        System.out.println(category);
        System.out.println(tags);
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
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<?> getCategory() {
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            throw new BadRequest("Error input data");
        }
    }

    public ResponseEntity<?> setShow(Long id) {
        var news = newsRepository.findById(id).orElseThrow();
        news.setShows(news.getShows() + 1);
        newsRepository.save(news);
        return new ResponseEntity<>(HttpStatus.OK);
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
}

