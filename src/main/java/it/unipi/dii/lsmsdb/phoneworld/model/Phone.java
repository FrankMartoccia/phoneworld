package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document
public class Phone {

    @Id
    private String id;
    private String brand;
    private String name;
    private String picture;
    private String body;
    private String os;
    private String storage;
    private String displaySize;
    private String displayResolution;
    private String cameraPixels;
    private String videoPixels;
    private String ram;
    private String chipset;
    private String batterySize;
    private String batteryType;
    private String specifications;
    private Date releaseDate;
    private List<Review> reviews = new ArrayList<>();

    public Phone() {
    }

    //Constructor for MongoDB
    public Phone(String brand, String name, String picture, String body, String os, String storage,
                 String displaySize, String displayResolution, String cameraPixels, String videoPixels,
                 String ram, String chipset, String batterySize, String batteryType, String specifications,
                 Date releaseDate) {
        this.brand = brand;
        this.name = name;
        this.picture = picture;
        this.releaseDate = releaseDate;
        this.body = body;
        this.os = os;
        this.storage = storage;
        this.displaySize = displaySize;
        this.displayResolution = displayResolution;
        this.cameraPixels = cameraPixels;
        this.videoPixels = videoPixels;
        this.ram = ram;
        this.chipset = chipset;
        this.batterySize = batterySize;
        this.batteryType = batteryType;
        this.specifications = specifications;
    }

    //Constructor for Neo4J
    public Phone(String id, String brand, String name, String picture) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getBody() {
        return body;
    }

    public String getOs() {
        return os;
    }

    public String getStorage() {
        return storage;
    }

    public String getDisplaySize() {
        return displaySize;
    }

    public String getDisplayResolution() {
        return displayResolution;
    }

    public String getCameraPixels() {
        return cameraPixels;
    }

    public String getVideoPixels() {
        return videoPixels;
    }

    public String getRam() {
        return ram;
    }

    public String getChipset() {
        return chipset;
    }

    public String getBatterySize() {
        return batterySize;
    }

    public String getBatteryType() {
        return batteryType;
    }

    public String getSpecifications() {
        return specifications;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview (Review review) {
        this.reviews.add(review);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", releaseDate=" + releaseDate +
                ", body='" + body + '\'' +
                ", os='" + os + '\'' +
                ", storage='" + storage + '\'' +
                ", displaySize='" + displaySize + '\'' +
                ", displayResolution='" + displayResolution + '\'' +
                ", cameraPixels='" + cameraPixels + '\'' +
                ", videoPixels='" + videoPixels + '\'' +
                ", ram='" + ram + '\'' +
                ", chipset='" + chipset + '\'' +
                ", batterySize='" + batterySize + '\'' +
                ", batteryType='" + batteryType + '\'' +
                ", specifications='" + specifications + '\'' +
                ", reviews=" + reviews +
                '}';
    }
}
