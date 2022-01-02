package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

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

    public Document findMostActiveUsers() {
        GroupOperation groupOperation = group("$userId").count().
                as("numReviews");
//            ProjectionOperation projectionOperation = project()
//                    .andExpression("avgAge").as("avgAge").andExclude("_id")
//                    .andExpression("_id").as("country");
        Aggregation aggregation = newAggregation(groupOperation);
        AggregationResults<Review> result = mongoOperations
                .aggregate(aggregation, "reviews", Review.class);
        return result.getRawResults();
    }

}
