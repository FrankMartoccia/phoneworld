package it.unipi.dii.lsmsdb.phoneworld.services;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceReview {

    @Autowired
    private ReviewMongo reviewMongo;
    @Autowired
    private PhoneMongo phoneMongo;
    @Autowired
    private UserMongo userMongo;

    private final static Logger logger = LoggerFactory.getLogger(ServiceUser.class);

    public boolean insertReview(Review review, Phone phone, User user) {
        boolean result = true;
        if (!reviewMongo.addReview(review)) {
            logger.error("Error in adding the review to the collection of reviews");
            return false;
        }
        if (!userMongo.updateUser(user.getId(), user, "user")) {
            logger.error("Error in adding the review to the collection of users");
            if (!reviewMongo.deleteReview(review)) {
                logger.error("Error in deleting the review from the collection of reviews");
            }
            return false;
        }
        if (!phoneMongo.updatePhone(phone.getId(), phone))  {
            logger.error("Error in adding the review to the collection of phones");
            if (!reviewMongo.deleteReview(review)) {
                logger.error("Error in deleting the review from the collection of reviews");
            }
            return false;
        }
        return result;
    }

}
