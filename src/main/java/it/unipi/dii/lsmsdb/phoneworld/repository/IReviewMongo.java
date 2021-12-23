package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IReviewMongo extends MongoRepository<Review, String> {
}
