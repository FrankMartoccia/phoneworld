package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.swing.*;

//@ComponentScan("it.unipi.dii.lsmsdb.*")
//@EntityScan("it.unipi.dii.lsmsdb.*")
//@EnableMongoRepositories("it.unipi.dii.lsmsdb.*")
@SpringBootApplication
public class Application {

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

// To test on the real database

	@EventListener(ApplicationReadyEvent.class)
	public void afterTheStart() {
//		Date dateOfRelease = new GregorianCalendar(2005, Calendar.FEBRUARY, 11).getTime();
//		Phone phone = new Phone("brand", "Nokia Lumia 800", "url", "body", "os",
//				"storage", "display", "resolution", "camera",
//				"video", "ram", "chipset", "batterySize",
//				"batteryType", "specs", dateOfRelease);
//
//		Date dateOfRelease2 = new GregorianCalendar(2000, Calendar.MAY, 11).getTime();
//		Phone phone2 = new Phone("brand2", "Apple iPhone 11", "url", "body", "os",
//				"storage", "display", "resolution", "camera",
//				"video", "ram", "chipset", "batterySize",
//				"batteryType", "specs", dateOfRelease2);
//
//		Date dateOfRelease3 = new GregorianCalendar(2021, Calendar.MAY, 11).getTime();
//		Phone phone3 = new Phone("brand3", "Apple iPhone 13 Pro", "url", "body", "os",
//				"storage", "display", "resolution", "camera",
//				"video", "ram", "chipset", "batterySize",
//				"batteryType", "specs", dateOfRelease3);
//
//		phoneMongo.addPhone(phone);
//		phoneMongo.addPhone(phone2);
//		String id = phoneMongo.getPhoneMongo().findAll().get(0).getId();
//		List<Phone> phones = phoneMongo.getPhoneMongo().findAll();
//		phones.forEach(System.out::println);
//		phoneMongo.updatePhone(id, phone3);
//		phones = phoneMongo.getPhoneMongo().findAll();
//		System.out.println("\n");
//		phones.forEach(System.out::println);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SpringApplication.run(Application.class, args);
			Application.getInstance().init();
		});
	}

}
