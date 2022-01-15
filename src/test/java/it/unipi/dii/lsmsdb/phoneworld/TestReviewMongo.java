package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
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
        Review review1 = new Review.ReviewBuilder(1, dateOfReview1, "Nice phone",
                "this phone is very nice").username("user1").phoneName("phone1").build();
        Date dateOfReview2 = new GregorianCalendar(2008, Calendar.FEBRUARY, 11).getTime();
        Review review2 = new Review.ReviewBuilder(4, dateOfReview2, "Nice phone",
                "this phone is very nice").username("user2").phoneName("phone2").build();
        Date dateOfReview3 = new GregorianCalendar(2004, Calendar.FEBRUARY, 11).getTime();
        Review review3 = new Review.ReviewBuilder(3, dateOfReview3, "Nice phone",
                "this phone is very nice").username("user3").phoneName("phone2").build();
        Date dateOfReview4 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review4 = new Review.ReviewBuilder(5, dateOfReview4, "Nice phone",
                "this phone is very nice").username("user1").phoneName("phone4").build();
        reviewMongo.saveReview(review1);
        reviewMongo.saveReview(review2);
        reviewMongo.saveReview(review3);
        reviewMongo.saveReview(review4);
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
        List<Review> reviews = reviewMongo.findByWord("iss");
        assertEquals(0, reviews.size());
        reviews = reviewMongo.findByWord("nice");
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
        Review review = new Review.ReviewBuilder(1, dateOfReview, "Bad phone",
                "this phone is very bad").build();
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.updateReview(id, review);
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
    public void testDeleteReviewsByUsername() {
        reviewMongo.deleteReviewsByUsername("user1");
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        assertEquals(2, reviews.size());
    }

    @Test
    public void testDeleteReviewByPhoneName() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        reviewMongo.deleteReviewByPhoneName("phone2");
        reviews = reviewMongo.getReviewMongo().findAll();
        assertEquals(2, reviews.size());
    }

    @Test
    public void testFindMostActiveUsers() {
        Document users = reviewMongo.findMostActiveUsers(5);
        List<Document> results = (List<Document>) users.get("results");
        System.out.println(results);
        assertEquals(2, results.get(0).get("reviews"));
    }

    @Test
    public void testFindTopPhonesByRating() {
        Document phones = reviewMongo.findTopPhonesByRating(2, 2);
        List<Document> results = (List<Document>) phones.get("results");
        System.out.println(results);
        assertEquals(3.5,results.get(0).get("rating"));
//        assertEquals(0, results.size());
    }

}
