package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewMongo extends MongoRepository<Review, String> {

    List<Review> findByTitleContainingOrBodyContaining(String word1, String word2);

}
