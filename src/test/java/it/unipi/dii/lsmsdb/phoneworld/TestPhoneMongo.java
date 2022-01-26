package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
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
public class TestPhoneMongo {

    @Autowired
    private PhoneMongo phoneMongo;
    private String id;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        phoneMongo.getPhoneMongo().deleteAll();
    }

    private void init() {
        int dateOfRelease = 2000;
        Phone phone = new Phone("Nokia", "Nokia Lumia 800", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", dateOfRelease);

        int dateOfRelease2 = 2006;
        Phone phone2 = new Phone("Apple", "Apple iPhone 11", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", dateOfRelease2);

        int dateOfRelease3 = 2009;
        Phone phone3 = new Phone("Samsung", "Samsung Galaxy S21", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", dateOfRelease3);

        Date dateOfReview1 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review1 = new Review.ReviewBuilder(1, dateOfReview1, "Nice phone",
                "this phone is very nice").username("user1").build();
        Date dateOfReview2 = new GregorianCalendar(2008, Calendar.FEBRUARY, 11).getTime();
        Review review2 = new Review.ReviewBuilder(4, dateOfReview2, "Nice phone",
                "this phone is very nice").username("user2").build();
        Date dateOfReview3 = new GregorianCalendar(2004, Calendar.FEBRUARY, 11).getTime();
        Review review3 = new Review.ReviewBuilder(3, dateOfReview3, "Nice phone",
                "this phone is very nice").username("user3").build();
        Date dateOfReview4 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review4 = new Review.ReviewBuilder(5, dateOfReview4, "Nice phone",
                "this phone is very nice").username("user4").build();
        phone.addReview(review1);
        phone.addReview(review2);
        phone.addReview(review3);
        phone.addReview(review4);
        phoneMongo.addPhone(phone);
        phoneMongo.addPhone(phone2);
        phoneMongo.addPhone(phone3);
        id = phoneMongo.getPhoneMongo().findAll().get(0).getId();
    }

    @Test
    public void testAddPhone() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        assertEquals("resolution", phones.get(0).getDisplayResolution());
        assertEquals(3, phones.size());
    }

    @Test
    public void testFindPhones() {
        List<Phone> phones = phoneMongo.findPhones("iss", "Name");
        assertEquals(0, phones.size());
        phones = phoneMongo.findPhones("iPhone", "Name");
        assertEquals(1, phones.size());
    }

    @Test
    public void testFindPhoneById() {
        Optional<Phone> phones = phoneMongo.findPhoneById("11111");
        assertEquals(phones, Optional.empty());
        phones = phoneMongo.findPhoneById(id);
        if (phones.isPresent()) {
            assertEquals(phones.get().getId(), id);
        }
    }

    @Test
    public void testUpdatePhone() {
        int dateOfRelease = 2003;
        Phone phone = new Phone("brand3", "Nokia Lumia 753", "url3", "body3", "os",
                "storage3", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", dateOfRelease);
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phoneMongo.updatePhone(id, phone);
        phones = phoneMongo.getPhoneMongo().findAll();
        assertEquals(3, phones.size());
        assertEquals("brand3", phoneMongo.getPhoneMongo().findAll().get(0).getBrand());
    }

    @Test
    public void testDeletePhoneById() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phoneMongo.deletePhoneById(id);
        phones = phoneMongo.getPhoneMongo().findAll();
        assertEquals(2,phones.size());
    }

    @Test
    public void testDeletePhone() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phoneMongo.deletePhone(phoneMongo.getPhoneMongo().findAll().get(0));
        phones = phoneMongo.getPhoneMongo().findAll();
        assertEquals(2,phones.size());
    }

    @Test
    public void testFindRecentPhones() {
        List<Phone> phones = phoneMongo.findRecentPhones();
        assertEquals((int)Optional.of(2009).get(), phones.get(0).getReleaseYear());
    }

}
