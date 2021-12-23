package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Document(collection = "Users")
public class User extends GenericUser{

    private String gender;
    private String firstName;
    private String lastName;
    private String streetNumber;
    private String streetName;
    private String city;
    private String country;
    private String email;
    private Calendar dateOfBirth;
    private int age;
    private List<Review> reviews = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();

    public User() {
    }

    //Constructor for Neo4J
    public User(String id, String username) {
        super(id, username);
    }

    //Constructor for MongoDB
    public User(String id, String username, String password, String salt, String sha, boolean admin, String gender,
                String firstName, String lastName, String streetNumber, String streetName, String city, String country,
                String email, Calendar dateOfBirth, int age, List<Review> reviews) {
        super(id, username, password, salt, sha, admin);
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.country = country;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.reviews = reviews;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
    }
}
