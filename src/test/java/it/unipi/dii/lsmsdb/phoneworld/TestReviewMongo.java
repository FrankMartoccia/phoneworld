package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.repository.ReviewMongo;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReviewMongo {

    @Autowired
    private ReviewMongo reviewMongo;
    private String id;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        reviewMongo.getReviewMongo().deleteAll();
    }

    private void init() {
        Date dateOfReview1 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review1 = new Review("1", "2", 5, dateOfReview1, "Nice phone",
                "this phone is very nice");
        Date dateOfReview2 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review2 = new Review("1", "3", 5, dateOfReview2, "Nice phone",
                "this phone is very nice");
        Date dateOfReview3 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review3 = new Review("2", "3", 5, dateOfReview3, "Nice phone",
                "this phone is very nice");
        Date dateOfReview4 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review4 = new Review("3", "3", 5, dateOfReview4, "Nice phone",
                "this phone is very nice");
        reviewMongo.addReview(review1);
        reviewMongo.addReview(review2);
        reviewMongo.addReview(review3);
        reviewMongo.addReview(review4);
        id = reviewMongo.getReviewMongo().findAll().get(0).getId();
    }

    @Test
    public void testAddReview() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        assertEquals("Nice phone", reviews.get(0).getTitle());
        assertEquals(4, reviews.size());
    }

    @Test
    public void testFindReviews() {
        List<Review> reviews = reviewMongo.findReviews("iss");
        assertEquals(0, reviews.size());
        reviews = reviewMongo.findReviews("nice");
        reviews.forEach(System.out::println);
        assertEquals(4, reviews.size());
    }

    @Test
    public void testFindReviewById() {
        Optional<Review> review = reviewMongo.findReviewById("11111");
        assertEquals(review, Optional.empty());
        review = reviewMongo.findReviewById(id);
        if (review.isPresent()) {
            System.out.println(review.get());
            assertEquals(review.get().getId(), id);
        }
    }

    @Test
    public void testUpdateReview() {
        Date dateOfReview = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review newReview = new Review("1", "3", 2, dateOfReview, "Bad phone",
                "this phone is very bad");
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.updateReview(id, newReview);
        reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        assertEquals(4, reviews.size());
        assertEquals(reviews.get(0).getTitle(), "Bad phone");
    }

    @Test
    public void testDeleteReviewById() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.deleteReviewById(id);
        reviews = reviewMongo.getReviewMongo().findAll();
        assertEquals(3,reviews.size());
        reviews.forEach(System.out::println);
    }

    @Test
    public void testDeleteReview() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.deleteReview(reviewMongo.getReviewMongo().findAll().get(0));
        reviews = reviewMongo.getReviewMongo().findAll();
        assertEquals(3,reviews.size());
        reviews.forEach(System.out::println);
    }

    @Test
    public void testDeleteReviewByUserId() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.deleteReviewByUserId("1");
        reviews = reviewMongo.getReviewMongo().findAll();
        assertEquals(2, reviews.size());
    }

    @Test
    public void testDeleteReviewByPhoneId() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.deleteReviewByPhoneId("3");
        reviews = reviewMongo.getReviewMongo().findAll();
        assertEquals(1, reviews.size());
    }

    @Test
    public void testFindMostActiveUsers() {
        Document users = reviewMongo.findMostActiveUsers();
        List<Document> results = (List<Document>) users.get("results");
        System.out.println(results);
        assertEquals(2, results.get(0).get("Reviews"));
    }

}
