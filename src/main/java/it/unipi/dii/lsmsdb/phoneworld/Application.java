package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.AdminMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.swing.*;

@SpringBootApplication
@EntityScan("it.unipi.dii.lsmsdb.*")
@EnableMongoRepositories("it.unipi.dii.lsmsdb.*")
public class Application {

	@Autowired
	private AdminMongo adminMongo;

	@Autowired
	private UserMongo userMongo;

	@Autowired
	private PhoneMongo phoneMongo;

	@Autowired
	private ReviewMongo reviewMongo;

	private static Application singleton = new Application();
	private ModelBean modelBean = new ModelBean();

	public static Application getInstance() {
		return singleton;
	}

	public ModelBean getModelBean() {
		return modelBean;
	}

	public AdminMongo getAdminMongo() {
		return adminMongo;
	}

	public UserMongo getUserMongo() {
		return userMongo;
	}

	public PhoneMongo getPhoneMongo() {
		return phoneMongo;
	}

	public ReviewMongo getReviewMongo() {
		return reviewMongo;
	}

	private void init() {

	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void afterTheStart() {
//		Admin admin = new Admin("admin123", "dkasflafm", "ndas732neaj",
//				"dsaodd", true);
//		adminMongo.addAdmin(admin);
//		List<Admin> admins = adminMongo.getAdminMongo().findAll();
//		admins.forEach(System.out::println);
//
//		Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
//		User user = new User("Frank", "123456", "kdasd", "dasdksamda", false,
//				"male", "Paul", "Murray", "21", "street",
//				"Las Vegas", "Nevada", "dnsak@gmail.com", dateOfBirth, 57);
//		userMongo.addUser(user);
//		List<User> users = userMongo.getUserMongo().findAll();
//		users.forEach(System.out::println);
//	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SpringApplication.run(Application.class, args);
			Application.getInstance().init();
		});

	}

}
