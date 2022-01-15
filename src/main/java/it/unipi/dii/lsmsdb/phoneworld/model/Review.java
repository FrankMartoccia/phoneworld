package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private String userId;
    private String phoneId;
    private int rating;
    private Date dateOfReview;
    private String title;
    private String body;
    private String username;
    private String phoneName;

    public Review() {
    }

    public Review(String userId, String phoneId, int rating, Date dateOfReview, String title,
                  String body, String username, String phoneName) {
        this.userId = userId;
        this.phoneId = phoneId;
        this.rating = rating;
        this.dateOfReview = dateOfReview;
        this.title = title;
        this.body = body;
        this.username = username;
        this.phoneName = phoneName;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toStringTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(this.title).append("\n");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.dateOfReview);
        sb.append("Date: ").append(calendar.get(Calendar.YEAR)).append("-").append(calendar.get(Calendar.MONTH)).
                append("-").append(calendar.get(Calendar.DAY_OF_MONTH)).append("\n");
        sb.append("Phone: ").append(this.phoneName).append("\n");
        sb.append("Vote: ").append(this.rating).append("\n");
        StringBuilder sBody = new StringBuilder(this.body);
        int i = 0;
        while ((i = sBody.indexOf(" ", i + 120)) != -1) {
            sBody.replace(i, i+1, "\n");
        }
        sb.append("Body: ").append(sBody);
        return sb.toString();
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
                ", username='" + username + '\'' +
                ", phoneName='" + phoneName + '\'' +
                '}';
    }
}
