package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
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
	private ReviewMongo reviewMongo;

	@Autowired
	private PhoneMongo phoneMongo;

	@Autowired
	private  UserMongo userMongo;

	private static Application singleton = new Application();
	private ModelBean modelBean = new ModelBean();

	public static Application getInstance() {
		return singleton;
	}

	private void init() {

	}

	public static Application getSingleton() {
		return singleton;
	}

	public ModelBean getModelBean() {
		return modelBean;
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void afterTheStart() {
//		Date dateOfReview = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
//		Review review = new Review("1234", "2", 5, dateOfReview, "Nice phone", "fda");
//		reviewMongo.addReview(review);
//		List<Review> reviews = reviewMongo.getReviewMongo().findAll();
//		reviews.forEach(System.out::println);
//	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SpringApplication.run(Application.class, args);
			Application.getInstance().init();
		});

	}

}
