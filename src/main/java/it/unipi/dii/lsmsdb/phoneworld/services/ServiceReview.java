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

    private final static Logger logger = LoggerFactory.getLogger(ServiceReview.class);

    public boolean insertReview(Review review, Phone phone, User user) {
        try {
            if (!reviewMongo.addReview(review)) {
                logger.error("Error in adding the review to the collection of reviews");
                return false;
            }
            String reviewId = this.getReviewId(review);
            int rating = review.getRating();
            String title = review.getTitle();
            String body = review.getBody();
            String phoneName = review.getPhoneName();
            String username = review.getUsername();
            if (!addReviewToUser(rating, title, body, phoneName, reviewId, user, review)) {
                logger.error("Error in adding the review to the collection of users");
                return false;
            }
            if (!addReviewToPhone(rating, title, body, username, reviewId, phone, review)) {
                logger.error("Error in adding the review to the collection of phones");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean addReviewToUser(int rating, String title, String body, String phoneName, String reviewId,
                                    User user, Review review) {
        Review reviewUser = new Review.ReviewBuilder(rating, new Date(), title, body).
                phoneName(phoneName).id(reviewId).build();
        checkLastReviewUser(user, false);
        user.addReview(reviewUser);
        if (!userMongo.updateUser(user.getId(), user, "user")) {
            logger.error("Error in adding the review to the collection of users");
            if (!reviewMongo.deleteReview(review)) {
                logger.error("Error in deleting the review from the collection of reviews");
            }
            return false;
        }
        return true;
    }

    public boolean addReviewToPhone(int rating, String title, String body, String username, String reviewId,
                                    Phone phone, Review review) {
        Review reviewPhone = new Review.ReviewBuilder(rating, new Date(), title, body).
                username(username).id(reviewId).build();
        checkLastReviewPhone(phone, false);
        phone.addReview(reviewPhone);
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
        return review;
    }

    public boolean updateReview(Review selectedReview, User user, int rating, Date dateOfReview,
                                String title, String body) {
        try {
            String reviewId = selectedReview.getId();
            String username = user.getUsername();
            String phoneName = selectedReview.getPhoneName();
            Review newReview = new Review.ReviewBuilder(rating, dateOfReview, title, body).
                    username(username).phoneName(phoneName).build();
            if (!reviewMongo.updateReview(reviewId, newReview)) {
                logger.error("Error in updating the review in the collection of reviews");
                return false;
            }
            if (!updateReviewInUser(rating, title, body, phoneName, reviewId, user)) {
                logger.error("Error in updating the review in the collection of users");
                return false;
            }
            if (!updateReviewInPhone(rating, title, body, username, reviewId, phoneName)) {
                logger.error("Error in updating the review in the collection of phones");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean updateReviewInPhone(int rating, String title ,String body, String username,
                                        String reviewId, String phoneName) {
        Optional<Phone> phoneResult = phoneMongo.findPhoneByName(phoneName);
        if (phoneResult.isPresent()) {
            Review newReview = new Review.ReviewBuilder(rating, new Date(), title, body).
                    username(username).id(reviewId).build();
            Phone phone = phoneResult.get();
            if (phone.deleteReview(reviewId)) {
                phone.addReview(newReview);
            }
            return phoneMongo.updatePhone(phone.getId(), phone);
        }
        return false;
    }

    public boolean updateReviewInUser(int rating, String title ,String body, String phoneName,
                                      String reviewId, User user) {
        if ((boolean) App.getInstance().getModelBean().getBean(Constants.IS_EMBEDDED)) {
            Review newReview = new Review.ReviewBuilder(rating, new Date(), title, body).
                    phoneName(phoneName).id(reviewId).build();
            if (user.deleteReview(reviewId)) {
                user.addReview(newReview);
            }
            return userMongo.updateUser(user.getId(), user, "user");
        }
        return false;
    }

    public boolean deleteReview(Review selectedReview, Phone phone, User user) {
        try {
            boolean isEmbedded = (boolean) App.getInstance().getModelBean().getBean(Constants.IS_EMBEDDED);
            String reviewId = selectedReview.getId();
            if (!deleteReviewInPhone(phone, selectedReview, reviewId, isEmbedded)) {
                logger.error("Error in deleting the review from the collection of phones");
                return false;
            }
            if (!deleteReviewInUser(user, selectedReview, reviewId, isEmbedded)) {
                logger.error("Error in deleting the review from the collection of users");
                return false;
            }
            if (!reviewMongo.deleteReview(selectedReview)) {
                logger.error("Error in deleting the review from the collection of reviews");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean deleteReviewInUser(User user, Review selectedReview, String reviewId, boolean isEmbedded) {
        if (user == null) {
            String username = selectedReview.getUsername();
            Optional<GenericUser> userResult = userMongo.findByUsername(username);
            if (userResult.isEmpty()) {
                return false;
            }
            User newUser = (User) userResult.get();
            return deleteUserReview(newUser, reviewId);
        } else if (isEmbedded) {
            return deleteUserReview(user, reviewId);
        }
        return true;
    }

    public boolean deleteUserReview(User user, String reviewId) {
        if (user.getReviewInUser(reviewId) != null) {
            checkLastReviewUser(user, true);
            user.deleteReview(reviewId);
            return userMongo.updateUser(user.getId(), user, "user");
        }
        return true;
    }

    public boolean deletePhoneReview(Phone phone, String reviewId) {
        if (phone.getReviewInPhone(reviewId) != null) {
            checkLastReviewPhone(phone, true);
            phone.deleteReview(reviewId);
            return phoneMongo.updatePhone(phone.getId(), phone);
        }
        return true;
    }

    private void checkLastReviewPhone(Phone phone, boolean isDelete) {
        int numReviews = phone.getReviews().size();
        if (numReviews == 50) {
            if (isDelete) {
                List<Review> oldReviews = reviewMongo.findOldReviews(phone.getName(), true);
                phone.getReviews().add(numReviews, oldReviews.get(0));
            } else {
                phone.getReviews().remove(numReviews - 1);
            }
        }
    }

    private void checkLastReviewUser(User newUser, boolean isDelete) {
        int numReviews = newUser.getReviews().size();
        if (numReviews == 50) {
            if (isDelete) {
                List<Review> oldReviews = reviewMongo.findOldReviews(newUser.getUsername(), false);
                newUser.getReviews().add(numReviews, oldReviews.get(0));
            } else {
                newUser.getReviews().remove(numReviews - 1);
            }
        }
    }

    public boolean deleteReviewInPhone(Phone phone, Review selectedReview, String reviewId, boolean isEmbedded) {
        if (phone == null) {
            Optional<Phone> phoneResult = phoneMongo.findPhoneByName(selectedReview.getPhoneName());
            if (phoneResult.isEmpty()) {
                return false;
            }
            Phone newPhone = phoneResult.get();
            return deletePhoneReview(newPhone ,reviewId);
        } else if (isEmbedded) {
            return deletePhoneReview(phone, reviewId);
        }
        return true;
    }

}
