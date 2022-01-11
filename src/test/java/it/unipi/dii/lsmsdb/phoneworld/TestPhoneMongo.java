package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
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
//        Date dateOfRelease = new GregorianCalendar(2005, Calendar.FEBRUARY, 11).getTime();
        int dateOfRelease = 2000;
        Phone phone = new Phone("Nokia", "Nokia Lumia 800", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", "specs", dateOfRelease);

//        Date dateOfRelease2 = new GregorianCalendar(2000, Calendar.MAY, 11).getTime();
        int dateOfRelease2 = 2006;
        Phone phone2 = new Phone("Apple", "Apple iPhone 11", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", "specs", dateOfRelease2);

        int dateOfRelease3 = 2009;
        Phone phone3 = new Phone("Samsung", "Samsung Galaxy S21", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", "specs", dateOfRelease3);

        Date dateOfReview1 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review1 = new Review("1", "2", 4, dateOfReview1, "Nice phone",
                "this phone is very nice", "user1", "phone1");
        Date dateOfReview2 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review2 = new Review("1", "3", 1, dateOfReview2, "Nice phone",
                "this phone is very nice", "user2", "phone2");
        Date dateOfReview3 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review3 = new Review("2", "3", 2, dateOfReview3, "Bad phone",
                "this phone is very nice", "user3", "phone3");
        Date dateOfReview4 = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
        Review review4 = new Review("3", "3", 5, dateOfReview4, "Nice phone",
                "this phone is very nice", "user4", "phone4");
        phone.addReview(review1);
        phone2.addReview(review2);
        phone3.addReview(review3);
        phone3.addReview(review4);
        phoneMongo.addPhone(phone);
        phoneMongo.addPhone(phone2);
        phoneMongo.addPhone(phone3);
        id = phoneMongo.getPhoneMongo().findAll().get(0).getId();
    }

    @Test
    public void testAddPhone() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phones.forEach(System.out::println);
        assertEquals("resolution", phones.get(0).getDisplayResolution());
        assertEquals(3, phones.size());
    }

    @Test
    public void testFindPhones() {
        List<Phone> phones = phoneMongo.findPhones("iss");
        assertEquals(0, phones.size());
        phones = phoneMongo.findPhones("iPhone");
        phones.forEach(System.out::println);
        assertEquals(1, phones.size());
    }

    @Test
    public void testFindPhoneById() {
        Optional<Phone> phones = phoneMongo.findPhoneById("11111");
        assertEquals(phones, Optional.empty());
        phones = phoneMongo.findPhoneById(id);
        if (phones.isPresent()) {
            System.out.println(phones.get());
            assertEquals(phones.get().getId(), id);
        }
    }

    @Test
    public void testUpdatePhone() {
//        Date dateOfRelease = new GregorianCalendar(2005, Calendar.FEBRUARY, 11).getTime();
        int dateOfRelease = 2003;
        Phone phone = new Phone("brand3", "Nokia Lumia 753", "url3", "body3", "os",
                "storage3", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", "specs", dateOfRelease);
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phones.forEach(System.out::println);
        phoneMongo.updatePhone(id, phone);
        phones = phoneMongo.getPhoneMongo().findAll();
        phones.forEach(System.out::println);
        assertEquals(3, phones.size());
        assertEquals("brand3", phoneMongo.getPhoneMongo().findAll().get(0).getBrand());
    }

    @Test
    public void testDeletePhoneById() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phones.forEach(System.out::println);
        phoneMongo.deletePhoneById(id);
        phones = phoneMongo.getPhoneMongo().findAll();
        assertEquals(2,phones.size());
        phones.forEach(System.out::println);
    }

    @Test
    public void testDeletePhone() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phones.forEach(System.out::println);
        phoneMongo.deletePhone(phoneMongo.getPhoneMongo().findAll().get(0));
        phones = phoneMongo.getPhoneMongo().findAll();
        assertEquals(2,phones.size());
        phones.forEach(System.out::println);
    }

    @Test
    public void testFindRecentPhones() {
        List<Phone> phones = phoneMongo.findRecentPhones();
        System.out.println(phones);
        assertEquals((int)Optional.of(2009).get(), phones.get(0).getReleaseYear());
    }

    @Test
    public void testTopRatedBrands() {
        Document phones = phoneMongo.findTopRatedBrands(2,3);
        List<Document> results = (List<Document>) phones.get("results");
        System.out.println(results);
        assertEquals("Samsung",results.get(0).get("brand"));
    }

}
