package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.repository.ReviewMongo;
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
public class TestReview {

    @Autowired
    private ReviewMongo reviewMongo;
    private String id;

    @Before
    public void clean() {
        reviewMongo.getReviewMongo().deleteAll();
        init();
    }

    public void init() {
        Date dateOfReview = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review = new Review("1", "3", 5, dateOfReview, "Nice phone",
                "this phone is very nice");
        reviewMongo.addReview(review);
        id = reviewMongo.getReviewMongo().findAll().get(0).getId();
    }

    @Test
    public void testAddReview() {
        List<Review> reviews = reviewMongo.getReviewMongo().findAll();
        reviews.forEach(System.out::println);
        assertEquals("Nice phone", reviews.get(0).getTitle());
        assertEquals(1, reviews.size());
    }

    @Test
    public void testFindReviews() {
        List<Review> reviews = reviewMongo.findReviews("iss");
        assertEquals(0, reviews.size());
        reviews = reviewMongo.findReviews("nice");
        reviews.forEach(System.out::println);
        assertEquals(1, reviews.size());
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
        assertEquals(1, reviews.size());
        assertEquals(reviews.get(0).getTitle(), "Bad phone");
    }

}
