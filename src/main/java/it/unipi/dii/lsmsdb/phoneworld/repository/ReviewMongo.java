package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
