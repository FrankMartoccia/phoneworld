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
    private String id1;
    private String id2;

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
        id1 = userMongo.getUserMongo().findAll().get(0).getId();
        id2 = userMongo.getUserMongo().findAll().get(1).getId();
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
        users = Optional.ofNullable(userMongo.findUserById(id1, true));
        if (users.isPresent()) {
            System.out.println(users.get());
            assertEquals(users.get().getId(), id1);
        }
    }

    @Test
    public void testUpdateUser() {
        Admin newAdmin = new Admin("admin1234", "ciao", "ndas732neaj",
                "dsaodd", true);

        Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
        User newUser = new User("Franko", "123456789", "kdasddd", "dasdksamda",
                false, "male", "Paul", "Murray", "21",
                "street", "Las Vegas", "Nevada", "dnsak@gmail.com",
                dateOfBirth, 57);
        List<GenericUser> users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        userMongo.updateUser(id2, newUser,false);
        userMongo.updateUser(id1, newAdmin, true);
        users = userMongo.getUserMongo().findAll();
        users.forEach(System.out::println);
        assertEquals(2, users.size());
        assertEquals(users.get(0).getUsername(), "admin1234");
        assertEquals(users.get(1).getUsername(), "Franko");
    }

}
