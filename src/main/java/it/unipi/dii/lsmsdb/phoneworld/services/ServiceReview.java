package it.unipi.dii.lsmsdb.phoneworld.services;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        if (!reviewMongo.addReview(review)) {
            logger.error("Error in adding the review to the collection of reviews");
            return false;
        }
        String reviewId = this.getReviewId(review);
        review.setId(reviewId);
        phone.addReview(review);
        user.addReview(review);
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
        return true;
    }

    private String getReviewId(Review review) {
        Optional<Review> reviewResult = reviewMongo.findByUsernameAndPhoneName(review.getUsername(),
                review.getPhoneName());
        if (reviewResult.isPresent()) {
            return reviewResult.get().getId();
        } else {
            logger.error("Review not found");
        }
        return "";
    }

    public Review getSelectedReview(int counterPages, int tableIndex, User user, Phone phone,
                                    List<Review> reviews) {
        boolean isEmbedded = true;
        int index;
        Review review;
        if (counterPages > 4) {
            index = tableIndex + (counterPages * 10 - 50);
            review = reviews.get(index);
            isEmbedded = false;
        } else {
            index = tableIndex + (counterPages * 10);
            if (phone == null) {
                review = user.getReviews().get(index);
            } else {
                review = phone.getReviews().get(index);
            }
        }
        if (review == null) {
            logger.error("Review null, not found ");
            return null;
       }
        App.getInstance().getModelBean().putBean(Constants.IS_EMBEDDED, isEmbedded);
        App.getInstance().getModelBean().putBean(Constants.SELECTED_INDEX, index);
        return review;
    }

    public boolean updateReview(Review selectedReview, User user, int rating, Date dateOfReview,
                                String title, String body) {
        try {
            if ((boolean) App.getInstance().getModelBean().getBean(Constants.IS_EMBEDDED)) {
                int index = (int) App.getInstance().getModelBean().getBean(Constants.SELECTED_INDEX);
                user.getReviews().get(index).setRating(rating);
                user.getReviews().get(index).setDateOfReview(dateOfReview);
                user.getReviews().get(index).setBody(body);
                user.getReviews().get(index).setTitle(title);
                if (!userMongo.updateUser(user.getId(), user, "user")) {
                    return false;
                }
            }
            Review newReview = new Review.ReviewBuilder(rating, dateOfReview, title, body).
                    username(selectedReview.getUsername()).phoneName(selectedReview.getPhoneName()).build();
            if (!reviewMongo.updateReview(selectedReview.getId(), newReview)) {
                return false;
            }
            Optional<Phone> phoneResult = phoneMongo.findPhoneByName(selectedReview.getPhoneName());
            if (phoneResult.isPresent()) {
                Phone phone = phoneResult.get();
                for (Review review : phone.getReviews()) {
                    if (review.getId().equals(selectedReview.getId())) {
                        review.setTitle(title);
                        review.setDateOfReview(dateOfReview);
                        review.setBody(body);
                        review.setRating(rating);
                        if (!phoneMongo.updatePhone(phone.getId(), phone)) {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public boolean deleteReview(Review selectedReview, Phone phone, User user) {
        try {
            boolean isEmbedded = (boolean) App.getInstance().getModelBean().getBean(Constants.IS_EMBEDDED);
            String reviewId = selectedReview.getId();
            if (phone == null) {
                Optional<Phone> phoneResult = phoneMongo.findPhoneByName(selectedReview.getPhoneName());
                if (phoneResult.isPresent()) {
                    Phone newPhone = phoneResult.get();
                    if (newPhone.deleteReview(reviewId)) {
                        if (!phoneMongo.updatePhone(newPhone.getId(), newPhone)) {
                            return false;
                        }
                    }
                }
            } else if (isEmbedded) {
                if (phone.deleteReview(reviewId)) {
                    if (!phoneMongo.updatePhone(phone.getId(), phone)) {
                        return false;
                    }
                }
            }
            if (user == null) {
                Optional<GenericUser> userResult = userMongo.findByUsername(selectedReview.getUsername());
                if (userResult.isPresent()) {
                    User newUser = (User) userResult.get();
                    newUser.deleteReview(selectedReview.getId());
                    if (!userMongo.updateUser(newUser.getId(), newUser, "user")) {
                        return false;
                    }
                }
            } else if (isEmbedded) {
                if (user.deleteReview(reviewId)) {
                    if (!userMongo.updateUser(user.getId(), user, "user")) {
                        return false;
                    }
                }
            }
            if (!reviewMongo.deleteReview(selectedReview)) {
                return false;
            }
        } catch (Exception ex) {
            logger.error("Exception occurred: " + ex.getLocalizedMessage());
            return false;
        }
        return true;
    }
}
