package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.Admin;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
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
public class TestUserMongo {

    @Autowired
    private UserMongo userMongo;
    private String id1;
    private String id2;

    @Before
    public void start() {
        init();
    }

    @After
    public void clean() {
        userMongo.getUserMongo().deleteAll();
    }

    private void init() {
        Admin admin = new Admin("admin123", "ndas732neaj",
                "dsaodd", "admin");
        Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
        User user1 = new User("Frank", "kdasd", "dasdksamda",
                "user", "male", "Paul", "Murray", 21,
                "street", "Las Vegas", "Nevada", "dnsak@gmail.com",
                dateOfBirth, 57);
        User user2 = new User("Mario", "kdasd", "dasdksamda",
                "user", "male", "Paul", "Murray", 21,
                "street", "Las Vegas", "Italy", "dnsak@gmail.com",
                dateOfBirth, 23);
        userMongo.addUser(admin);
        userMongo.addUser(user1);
        id1 = userMongo.getUserMongo().findAll().get(0).getId();
        id2 = userMongo.getUserMongo().findAll().get(1).getId();
        userMongo.addUser(user2);
    }

    @Test
    public void testAddUser() {
        List<GenericUser> users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        assertEquals("ndas732neaj", users.get(0).getSalt());
        assertEquals("Nevada", ((User)users.get(1)).getCountry());
        assertEquals(3, users.size());
    }

    @Test
    public void testFindUsers() {
        List<GenericUser> users = userMongo.findUsers("pinco", "user");
        assertEquals(0, users.size());
        users = userMongo.findUsers("ank", "user");
        users.forEach(System.out::println);
        assertEquals(1, users.size());
    }

    @Test
    public void testFindUserById() {
        Optional<GenericUser> user = userMongo.findUserById("11111");
        assertEquals(user, Optional.empty());
        user = userMongo.findUserById(id1);
        System.out.println(user);
        if (user.isPresent()) {
            System.out.println(user.get());
            assertEquals(user.get().getId(), id1);
        }
    }

    @Test
    public void testUpdateUser() {
        Admin newAdmin = new Admin("admin1234", "ndas732neaj",
                "dsaodd", "admin");

        Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
        User newUser = new User("Franko", "kdasddd", "dasdksamda",
                "user", "male", "Paul", "Murray", 21,
                "street", "Las Vegas", "Nevada", "dnsak@gmail.com",
                dateOfBirth, 57);
        List<GenericUser> users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        userMongo.updateUser(id1, newAdmin, "admin");
        userMongo.updateUser(id2, newUser,"user");
        users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        assertEquals(3, users.size());
        assertEquals(users.get(2).getUsername(), "admin1234");
        assertEquals(users.get(0).getUsername(), "Franko");
    }

    @Test
    public void testDeleteUserById() {
        List<GenericUser> users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        userMongo.deleteUserById(id1);
        users = userMongo.getUserMongo().findAll();
        assertEquals(2,users.size());
        users.forEach(System.out::println);
    }

    @Test
    public void testDeleteUser() {
        List<GenericUser> users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        userMongo.deleteUser(userMongo.getUserMongo().findAll().get(0));
        users = userMongo.getUserMongo().findAll();
        assertEquals(2,users.size());
        users.forEach(System.out::println);
    }

    @Test
    public void testFindYoungerCountriesByUsers() {
        Document users = userMongo.findYoungerCountriesByUsers(2);
        List<Document> results = (List<Document>) users.get("results");
        System.out.println(results);
        assertEquals(23.0, results.get(0).get("age"));
    }

    @Test
    public void testFindTopCountriesByUsers() {
        Document users = userMongo.findTopCountriesByUsers(1);
        List<Document> results = (List<Document>) users.get("results");
        System.out.println(results);
        assertEquals(1, results.get(0).get("users"));
        assertEquals(1, results.size());
    }

}
