package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
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
public class TestPhone {

    @Autowired
    private PhoneMongo phoneMongo;
    private String id;

    @Before
    public void clean() {
        phoneMongo.getPhoneMongo().deleteAll();
        init();
    }

    private void init() {
        Date dateOfRelease = new GregorianCalendar(2005, Calendar.FEBRUARY, 11).getTime();
        Phone phone = new Phone("brand", "Nokia Lumia 800", "url", "body", "os",
                "storage", "display", "resolution", "camera",
                "video", "ram", "chipset", "batterySize",
                "batteryType", "specs", dateOfRelease);
        phoneMongo.addPhone(phone);
        id = phoneMongo.getPhoneMongo().findAll().get(0).getId();
    }

    @Test
    public void testAddPhone() {
        List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
        phones.forEach(System.out::println);
        assertEquals("resolution", phones.get(0).getDisplayResolution());
        assertEquals(1, phones.size());
    }

    @Test
    public void testFindPhones() {
        List<Phone> phones = phoneMongo.findPhones("iss");
        assertEquals(0, phones.size());
        phones = phoneMongo.findPhones("Lumia");
        phones.forEach(System.out::println);
        assertEquals(1, phones.size());
    }

    @Test
    public void testFindReviewById() {
        Optional<Phone> phones = phoneMongo.findPhoneById("11111");
        assertEquals(phones, Optional.empty());
        phones = phoneMongo.findPhoneById(id);
        if (phones.isPresent()) {
            System.out.println(phones.get());
            assertEquals(phones.get().getId(), id);
        }
    }

}
