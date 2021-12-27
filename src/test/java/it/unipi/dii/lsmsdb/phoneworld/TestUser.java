package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Admin;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserMongo;
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
public class TestUser {

    @Autowired
    private UserMongo userMongo;
    private String id;

    @Before
    public void clean() {
        userMongo.getUserMongo().deleteAll();
        init();
    }

    private void init() {
        Admin admin = new Admin("admin123", "dkasflafm", "ndas732neaj",
                "dsaodd", true);
        userMongo.addUser(admin);

        Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
        User user = new User("Frank", "123456", "kdasd", "dasdksamda",
                false, "male", "Paul", "Murray", "21",
                "street", "Las Vegas", "Nevada", "dnsak@gmail.com",
                dateOfBirth, 57);
        userMongo.addUser(user);
        id = userMongo.getUserMongo().findAll().get(0).getId();
    }

    @Test
    public void testAddUser() {
        List<GenericUser> users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        assertEquals("ndas732neaj", users.get(0).getSalt());
        assertEquals("Nevada", ((User)users.get(1)).getCountry());
        assertEquals(2, users.size());
    }

    @Test
    public void testFindUsers() {
        List<GenericUser> users = userMongo.findUsers("frank", false);
        assertEquals(0, users.size());
        users = userMongo.findUsers("ank", false);
        users.forEach(System.out::println);
        assertEquals(1, users.size());
    }

    @Test
    public void testFindUserById() {
        Optional<GenericUser> users = Optional.ofNullable(userMongo.findUserById("11111", false));
        assertEquals(users, Optional.empty());
        users = Optional.ofNullable(userMongo.findUserById(id, true));
        if (users.isPresent()) {
            System.out.println(users.get());
            assertEquals(users.get().getId(), id);
        }
    }


}
