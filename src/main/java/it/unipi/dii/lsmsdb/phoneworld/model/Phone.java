package it.unipi.dii.lsmsdb.phoneworld.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Phones")
public class Phone {

    @Id
    private String id;
    private String brand;
    private String name;
    private String picture;
    private Calendar releaseDate;
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
    private List<Review> reviews = new ArrayList<>();

    //Constructor for MongoDB
    public Phone(String id, String brand, String name, String picture, Calendar releaseDate, String body, String os,
                 String storage, String displaySize, String displayResolution, String cameraPixels, String videoPixels,
                 String ram, String chipset, String batterySize, String batteryType, String specifications) {
        this.id = id;
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

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Calendar getReleaseDate() {
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
}
