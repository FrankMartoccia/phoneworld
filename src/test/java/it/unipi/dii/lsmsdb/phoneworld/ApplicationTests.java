package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.*;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserMongo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private UserMongo userMongo;

	@Autowired
	private PhoneMongo phoneMongo;

	@Autowired
	private ReviewMongo reviewMongo;

	@Before
	public void clean() {
		userMongo.getUserMongo().deleteAll();
		phoneMongo.getPhoneMongo().deleteAll();
		reviewMongo.getReviewMongo().deleteAll();
	}

	@Test
	public void testAddUser() {
		Admin admin = new Admin("admin123", "dkasflafm", "ndas732neaj",
				"dsaodd", true);
		userMongo.addUser(admin);
		List<GenericUser> users = userMongo.getUserMongo().findAll();
		assertEquals("ndas732neaj", users.get(0).getSalt());
		assertEquals(1, users.size());

		Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
		User user = new User("Frank", "123456", "kdasd", "dasdksamda", false,
				"male", "Paul", "Murray", "21", "street",
				"Las Vegas", "Nevada", "dnsak@gmail.com", dateOfBirth, 57);
		userMongo.addUser(user);
		users = userMongo.getUserMongo().findAll();
		users.forEach(System.out::println);
		assertEquals("Nevada", ((User)users.get(1)).getCountry());
		assertEquals(2, users.size());
	}

	@Test
	public void testAddPhone() {
		Date dateOfRelease = new GregorianCalendar(2005, Calendar.FEBRUARY, 11).getTime();
		Phone phone = new Phone("brand", "name", "url", "body", "os", "storage", "display",
				"resolution", "camera", "video", "ram", "chipset", "batterySize", "batteryType",
				"specs", dateOfRelease);
		phoneMongo.addPhone(phone);
		List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
		phones.forEach(System.out::println);
		assertEquals("resolution", phones.get(0).getDisplayResolution());
		assertEquals(1, phones.size());
	}

	@Test
	public void testAddReview() {
		Date dateOfReview = new GregorianCalendar(2007, Calendar.FEBRUARY, 11).getTime();
		Review review = new Review("1", "2", 5, dateOfReview, "Nice phone", "fda");
		reviewMongo.addReview(review);
		List<Review> reviews = reviewMongo.getReviewMongo().findAll();
		reviews.forEach(System.out::println);
		assertEquals("Nice phone", reviews.get(0).getTitle());
		assertEquals(1, reviews.size());
	}

}
