package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;

@Document(collection = "Reviews")
public class Review {

    @Id
    private String id;
    private String userId;
    private String phoneId;
    private int rating;
    private Calendar dateOfReview;
    private String title;
    private String body;

    //Constructor for MongoDB
    public Review(String id, String userId, String phoneId, int rating, Calendar dateOfReview, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.phoneId = phoneId;
        this.rating = rating;
        this.dateOfReview = dateOfReview;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public int getRating() {
        return rating;
    }

    public Calendar getDateOfReview() {
        return dateOfReview;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDateOfReview(Calendar dateOfReview) {
        this.dateOfReview = dateOfReview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
