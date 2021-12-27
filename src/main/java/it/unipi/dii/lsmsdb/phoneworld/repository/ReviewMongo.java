package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ReviewMongo {

    public ReviewMongo() {
    }

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    @Autowired
    private IReviewMongo reviewMongo;

    public IReviewMongo getReviewMongo() {
        return reviewMongo;
    }

    public String addReview(Review review) {
        String id;
        try {
            reviewMongo.save(review);
            id = review.getId();
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            id = "";
        }
        return id;
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

}
