package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewMongo extends MongoRepository<Review, String> {

//    @Query(value = "{'title': {$regex : ?0, $options: 'i'}, " +
//            "'body': {$regex : ?0, $options: 'i'}}")
//    List<Review> findByTitleMatchesRegexOrBodyMatchesRegex(String word1, String word2);

    List<Review> findByTitleContainingOrBodyContaining(String word1, String word2);
    void deleteReviewByUserId(String id);
    void deleteReviewByPhoneId(String id);
    List<Review> findByUserId(String id);
    List<Review> findByPhoneId(String id);
}
