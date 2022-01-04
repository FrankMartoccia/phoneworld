package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class ReviewMongo {

    public ReviewMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IReviewMongo reviewMongo;
    @Autowired
    private MongoOperations mongoOperations;

    public IReviewMongo getReviewMongo() {
        return reviewMongo;
    }

    public boolean addReview(Review review) {
        boolean result = true;
        try {
            reviewMongo.save(review);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public List<Review> findReviews(String word) {
        List<Review> reviews = new ArrayList<>();
        try {
            if (word.isEmpty()) {
                reviews.addAll(reviewMongo.findAll());
            } else {
                reviews.addAll(reviewMongo.findByTitleContainingOrBodyContaining(word, word));
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return reviews;
    }

    public Optional<Review> findReviewById(String id) {
        Optional<Review> review = Optional.empty();
        try {
            review = reviewMongo.findById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return review;
    }

    public List<Review> findReviewByUserId(String id) {
        List<Review> reviews = null;
        try {
            reviews = reviewMongo.findByUserId(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return reviews;
    }

    public List<Review> findReviewByPhoneId(String id) {
        List<Review> reviews = null;
        try {
            reviews = reviewMongo.findByPhoneId(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return reviews;
    }

    public boolean updateReview(String id, Review newReview) {
        boolean result = true;
        try {
            Optional<Review> review = reviewMongo.findById(id);
            if (review.isPresent()) {
                review.get().setTitle(newReview.getTitle());
                review.get().setDateOfReview(newReview.getDateOfReview());
                review.get().setBody(newReview.getBody());
                review.get().setRating(newReview.getRating());

                this.addReview(review.get());
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deleteReviewById(String id) {
        boolean result = true;
        try {
            reviewMongo.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deleteReview(Review review) {
        boolean result = true;
        try {
            reviewMongo.delete(review);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deleteReviewByUserId(String id) {
        boolean result = true;
        try {
            reviewMongo.deleteReviewByUserId(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public boolean deleteReviewByPhoneId(String id) {
        boolean result = true;
        try {
            reviewMongo.deleteReviewByPhoneId(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            result = false;
        }
        return result;
    }

    public Document findTopPhonesByRating(int minReviews, int results) {
        GroupOperation groupOperation = group("$phoneId").avg("$rating")
                .as("avgRating").count().as("numReviews");
        MatchOperation matchOperation = match(new Criteria("numReviews").gte(minReviews));
        SortOperation sortOperation = sort(Sort.by(Sort.Direction.DESC, "avgRating",
                "numReviews"));
        LimitOperation limitOperation = limit(results);
        ProjectionOperation projectionOperation = project()
                .andExpression("_id").as("phoneId")
                .andExpression("avgRating").as("rating").andExclude("_id")
                .andExpression("numReviews").as("reviews");
        Aggregation aggregation = newAggregation(groupOperation, matchOperation,
                sortOperation, limitOperation, projectionOperation);
        AggregationResults<Review> result = mongoOperations
                .aggregate(aggregation, "reviews", Review.class);
        return result.getRawResults();
    }

    public Document findMostActiveUsers(int number) {
        GroupOperation groupOperation = group("$userId").count().
                as("numReviews");
        SortOperation sortOperation = sort(Sort.by(Sort.Direction.DESC, "numReviews"));
        LimitOperation limitOperation = limit(number);
        ProjectionOperation projectionOperation = project()
                .andExpression("_id").as("userId")
                .andExpression("numReviews").as("Reviews").andExclude("_id");
        Aggregation aggregation = newAggregation(groupOperation, sortOperation, limitOperation,
                projectionOperation);
        AggregationResults<Review> result = mongoOperations
                .aggregate(aggregation, "reviews", Review.class);
        return result.getRawResults();
    }



}
