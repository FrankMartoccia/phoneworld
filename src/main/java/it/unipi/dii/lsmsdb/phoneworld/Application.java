package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class Application {

	private static Application singleton = new Application();

	public static Application getInstance() {
		return singleton;
	}

	private PhoneMongo phoneMongo = new PhoneMongo();
	private UserMongo userMongo = new UserMongo();
	private ReviewMongo reviewMongo = new ReviewMongo();
	private ModelBean modelBean = new ModelBean();

	private void init() {

	}

	public static Application getSingleton() {
		return singleton;
	}

	public PhoneMongo getPhoneMongo() {
		return phoneMongo;
	}

	public UserMongo getUserMongo() {
		return userMongo;
	}

	public ReviewMongo getReviewMongo() {
		return reviewMongo;
	}

	public ModelBean getModelBean() {
		return modelBean;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SpringApplication.run(Application.class, args);
				Application.getInstance().init();
			}
		});
	}

}
