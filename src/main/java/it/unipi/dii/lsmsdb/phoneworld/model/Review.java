package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Review {

    @Id
    private String id;
    private String userId;
    private String phoneId;
    private int rating;
    private Date dateOfReview;
    private String title;
    private String body;

    public Review(String userId, String phoneId, int rating, Date dateOfReview, String title, String body) {
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

    public void setId(String id) {
        this.id = id;
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

    public Date getDateOfReview() {
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

    public void setDateOfReview(Date dateOfReview) {
        this.dateOfReview = dateOfReview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", phoneId='" + phoneId + '\'' +
                ", rating=" + rating +
                ", dateOfReview=" + dateOfReview +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
