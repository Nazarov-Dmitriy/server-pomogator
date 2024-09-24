//package ru.pomogator.serverpomogator.servise;
//
//import com.google.gson.Gson;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import ru.pomogator.serverpomogator.domain.dto.news.NewsAddDto;
//import ru.pomogator.serverpomogator.domain.model.news.NewsModel;
//import ru.pomogator.serverpomogator.exception.BadRequest;
//import ru.pomogator.serverpomogator.repository.UserRepository;
//import ru.pomogator.serverpomogator.repository.news.CategoryRepository;
//import ru.pomogator.serverpomogator.repository.news.NewsRepository;
//import ru.pomogator.serverpomogator.repository.news.TagsRepository;
//import ru.pomogator.serverpomogator.utils.FileCreate;
//
//@RequiredArgsConstructor
//@Service
//public class NewsServise2 {
//    private final UserRepository userRepository;
//    private final NewsRepository newsRepository;
//    private final TagsRepository tagsRepository;
//    private final CategoryRepository categoryRepository;
////    private final NewsMapper newsMapper;
//
//    @Transactional
//    public ResponseEntity<Void> addNews(NewsAddDto body) {
//        try {
//            NewsModel build = NewsModel.builder()
//                    .title(body.getTitle())
//                    .subtitle(body.getSubtitle())
//                    .article(body.getArticle())
//                    .category_id(body.getCategory())
//                    .tags(new Gson().toJson(body.getTags()))
//                    .build();
//            var news = newsRepository.save(build);
//
//            var path = new StringBuilder();
//            path.append("files/news/").append(news.getId()).append("/");
//            var file = FileCreate.addFile(body.getFile(), path);
//
//            news.setFile(file);
//
//            return new ResponseEntity<>(HttpStatus.OK);
//
//        } catch (EmptyResultDataAccessException e) {
//            throw new BadRequest("Error input data");
//        }
//    }
//
//    @Transactional
//    public ResponseEntity<?> get(Long id) {
////        try {
////            var news = newsRepository.findById(id).orElseThrow();
////            var filePath = news.getFile().getPath();
////
////            NewsItemDto newsResult = NewsItemDto.builder()
////                    .id(news.getId())
////                    .title(news.getTitle())
////                    .subtitle(news.getSubtitle())
////                    .article(news.getArticle())
////                    .date_publication(news.getDate_publication())
////                    .shows(news.getShows())
////                    .likes(news.getLikes())
////                    .category(news.getCategory_id())
////                    .tags(new Gson().fromJson(news.getTags(), new TypeToken<>() {
////                    }))
////                    .image(filePath)
////                    .build();
////
////            return new ResponseEntity<>(newsResult, HttpStatus.OK);
////        } catch (Exception e) {
////            throw new BadRequest("Error input data");
////        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    public ResponseEntity<?> list(Long category, String tags) {
////        try {
////            List<NewsModel> news = null;
////            if (category != null) {
////                news = newsRepository.findByCategoryId(category);
////            } else if (tags != null) {
////                var regex = new StringBuilder();
////                for (var tag : tags.split(",")) {
////                    if (regex.isEmpty()) {
////                        regex.append(tag);
////                    } else {
////                        regex.append("|");
////                        regex.append(tag);
////                    }
////                }
////                news = newsRepository.findTags(String.valueOf(regex));
////            } else {
////                news = newsRepository.findAll();
////            }
//////            var list = new ArrayList<NewsItemDto>();
//////            var list = new ArrayList<NewsResponse>();
////
////            if (!news.isEmpty()) {
////                for (var item : news) {
//////                    System.out.printf(newsMapper.toNewsResponse(item));
////                    var filePath = item.getFile().getPath();
////                    NewsItemDto itemResult = NewsItemDto.builder()
////                            .id(item.getId())
////                            .title(item.getTitle())
////                            .subtitle(item.getSubtitle())
////                            .article(item.getArticle())
////                            .date_publication(item.getDate_publication())
////                            .shows(item.getShows())
////                            .likes(item.getLikes())
////                            .category(item.getCategory_id())
////                            .tags(new Gson().fromJson(item.getTags(), new TypeToken<>() {
////                            }))
////                            .image(filePath)
////                            .build();
//////                    list.add(itemResult);
//////                    list.add(newsMapper.toNewsResponse(item));
////                }
////            }
////
//////            return new ResponseEntity<>(list, HttpStatus.OK);
////            return new ResponseEntity<>( HttpStatus.OK);
////        } catch (Exception e) {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    public ResponseEntity<?> getTags() {
//        try {
//            var tags = tagsRepository.findAll();
//
//            return new ResponseEntity<>(tags, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new BadRequest("Error input data");
//        }
//    }
//
//    public ResponseEntity<?> getCategory() {
//        try {
//            var category = categoryRepository.findAll();
//            return new ResponseEntity<>(category, HttpStatus.OK);
//        } catch (Exception e) {
//            throw new BadRequest("Error input data");
//        }
//    }
//
//    public ResponseEntity<?> setShow(Long id) {
//        var news = newsRepository.findById(id).orElseThrow();
//        news.setShows(news.getShows() + 1);
//        newsRepository.save(news);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @Transactional
//    public ResponseEntity<?> setLike(Long id, Integer like, Integer dislike) {
//        var news = newsRepository.findById(id).orElseThrow();
//        if (like != null) {
//            news.setLikes(news.getLikes() + like);
//        } else if (dislike != null) {
//            if (news.getLikes() - dislike > 0) {
//                news.setLikes(news.getLikes() - dislike);
//            } else {
//                news.setLikes(0);
//            }
//        }
//        newsRepository.save(news);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//}
//
