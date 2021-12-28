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

    public void addReview(Review review) {
        try {
            reviewMongo.save(review);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
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

    public void updateReview(String id, Review newReview) {
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
        }
    }

    public void deleteReviewById(String id) {
        try {
            reviewMongo.deleteById(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteReview(Review review) {
        try {
            reviewMongo.delete(review);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteReviewByUserId(String id) {
        try {
            reviewMongo.deleteReviewByUserId(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void deleteReviewByPhoneId(String id) {
        try {
            reviewMongo.deleteReviewByPhoneId(id);
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

}
