package it.unipi.dii.lsmsdb.phoneworld.repository.mongo;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewMongo extends MongoRepository<Review, String> {

    Optional<Review> findByUsernameAndPhoneName(String username, String phoneName);
    List<Review> findByUsername(String id);
    List<Review> findByPhoneName(String id);
    List<Review> findByTitleContainingOrBodyContaining(String word, String word1);
    void deleteReviewsByUsername(String id);
    void deleteReviewByPhoneName(String id);
//    void deleteReviewByUsernameAndPhoneName(String username, String phoneName);


}
