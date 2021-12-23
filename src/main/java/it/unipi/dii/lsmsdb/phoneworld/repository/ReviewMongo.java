package it.unipi.dii.lsmsdb.phoneworld.repository;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ReviewMongo {

    @Autowired
    private IReviewMongo reviewMongo;
    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

    public String createReview(Review review) {
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
