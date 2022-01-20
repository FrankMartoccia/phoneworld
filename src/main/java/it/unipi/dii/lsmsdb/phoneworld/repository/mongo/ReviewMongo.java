package it.unipi.dii.lsmsdb.phoneworld.repository.mongo;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
public class ReviewMongo {

    public ReviewMongo() {
    }

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
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public Optional<Review> findReviewById(String id) {
        Optional<Review> review = Optional.empty();
        try {
            review = reviewMongo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return review;
    }

    public Optional<Review> findByUsernameAndPhoneName(String username, String phoneName) {
        Optional<Review> review = Optional.empty();
        try {
            review = reviewMongo.findByUsernameAndPhoneName(username, phoneName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return review;
    }

    public List<Review> findReviewByUsername(String username) {
        List<Review> reviews = null;
        try {
            reviews = reviewMongo.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> findReviewByPhoneName(String phoneName) {
        List<Review> reviews = null;
        try {
            reviews = reviewMongo.findByPhoneName(phoneName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean updateReview(String id, Review newReview) {
        boolean result = true;
        try {
            Optional<Review> review = reviewMongo.findById(id);
            if (review.isPresent()) {
                Review resultReview = review.get();
                Review.ReviewBuilder builder = new Review.ReviewBuilder(newReview);
                builder.id(id).phoneName(resultReview.getPhoneName()).username(resultReview.getUsername());
                this.addReview(builder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean deleteReviewById(String id) {
        boolean result = true;
        try {
            reviewMongo.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean deleteReview(Review review) {
        boolean result = true;
        try {
            reviewMongo.delete(review);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean deleteReviewByUsername(String id) {
        boolean result = true;
        try {
            reviewMongo.deleteReviewByUsername(id);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public boolean deleteReviewByPhoneName(String id) {
        boolean result = true;
        try {
            reviewMongo.deleteReviewByPhoneName(id);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public List<Review> findByWord(String word) {
        List<Review> reviews = new ArrayList<>();
        try {
            if (word.isEmpty()) {
                reviews.addAll(reviewMongo.findAll());
            } else {
                reviews.addAll(reviewMongo.findByTitleContainingOrBodyContaining(word, word));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> findOldReviews(String parameter, boolean isPhone) {
        List<Review> reviews = new ArrayList<>();
        try {
            Query query = new Query();
            if (isPhone) {
                query.addCriteria(new Criteria("phoneName").is(parameter));
            } else {
                query.addCriteria(new Criteria("username").is(parameter));
            }
            query.with(Sort.by(Sort.Direction.DESC, "dateOfReview"));
            query.skip(50);
            reviews = mongoOperations.find(query, Review.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean updateReviewsOldUser(String username) {
        try {
            Query query = Query.query(
                    Criteria.where("username").is(username));
            Update update = new Update().set("username", "Deleted User");
            mongoOperations.updateMulti(query, update, Review.class, "reviews");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Document findTopPhonesByRating(int minReviews, int results) {
        GroupOperation groupOperation = group("$phoneName").avg("$rating")
                .as("avgRating").count().as("numReviews");
        MatchOperation matchOperation = match(new Criteria("numReviews").gte(minReviews));
        SortOperation sortOperation = sort(Sort.by(Sort.Direction.DESC, "avgRating",
                "numReviews"));
        LimitOperation limitOperation = limit(results);
        ProjectionOperation projectionOperation = project()
                .andExpression("_id").as("phoneName")
                .andExpression("avgRating").as("rating").andExclude("_id")
                .andExpression("numReviews").as("reviews");
        Aggregation aggregation = newAggregation(groupOperation, matchOperation,
                sortOperation, limitOperation, projectionOperation);
        AggregationResults<Review> result = mongoOperations
                .aggregate(aggregation, "reviews", Review.class);
        return result.getRawResults();
    }

    public Document findMostActiveUsers(int results) {
        GroupOperation groupOperation = group("$username").count().as("numReviews").
                avg("$rating").as("ratingUser");
        SortOperation sortOperation = sort(Sort.by(Sort.Direction.DESC, "numReviews"));
        LimitOperation limitOperation = limit(results);
        ProjectionOperation projectionOperation = project()
                .andExpression("_id").as("username")
                .andExpression("numReviews").as("reviews").andExclude("_id")
                .andExpression("ratingUser").as("rating");
        Aggregation aggregation = newAggregation(groupOperation, sortOperation, limitOperation,
                projectionOperation);
        AggregationResults<Review> result = mongoOperations
                .aggregate(aggregation, "reviews", Review.class);
        return result.getRawResults();
    }

}
