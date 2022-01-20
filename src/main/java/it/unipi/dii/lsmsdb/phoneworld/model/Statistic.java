package it.unipi.dii.lsmsdb.phoneworld.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Statistic {

    private SimpleStringProperty name;
    private SimpleIntegerProperty reviews;
    private SimpleDoubleProperty rating;

    public Statistic(String name, int reviews, double rating) {
        this.name = new SimpleStringProperty(name);
        this.reviews = new SimpleIntegerProperty(reviews);
        this.rating = new SimpleDoubleProperty(rating);
    }

    public String getName() {
        return name.get();
    }

    public int getReviews() {
        return reviews.get();
    }

    public double getRating() {
        return rating.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setReviews(int reviews) {
        this.reviews = new SimpleIntegerProperty(reviews);
    }

    public void setRating(double rating) {
        this.rating = new SimpleDoubleProperty(rating);
    }
}
