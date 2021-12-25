package it.unipi.dii.lsmsdb.phoneworld.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "users")
@TypeAlias("user")
public class User extends GenericUser{

    public User() {
    }

    //Constructor for Neo4J
    public User(String username, String id) {
        super(username);
    }

    //Constructor for MongoDB
    public User(String username, String password, String salt, String sha, boolean admin, String gender,
                String firstName, String lastName, String streetNumber, String streetName, String city,
                String country, String email, Date dateOfBirth, int age) {
        super(username, password, salt, sha, admin);
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
    }

    @Id
    private String id;
    private String gender;
    private String firstName;
    private String lastName;
    private String streetNumber;
    private String streetName;
    private String city;
    private String country;
    private String email;
    private Date dateOfBirth;
    private int age;
    private List<Review> reviews = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();

    public String getId() {
        return id;
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

    public Date getDateOfBirth() {
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", gender='" + gender + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                ", reviews=" + reviews +
                ", phones=" + phones +
                '}';
    }
}
