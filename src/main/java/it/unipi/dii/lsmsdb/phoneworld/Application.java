package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.*;
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
	private final GraphNeo4j graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                    "neo4j", "PhoneWorld");
	private final UserNeo4j userNeo4j = new UserNeo4j(graphNeo4j);
	private final PhoneNeo4j phoneNeo4j = new PhoneNeo4j(graphNeo4j);

	private static final Application singleton = new Application();
	private final ModelBean modelBean = new ModelBean();

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

	public GraphNeo4j getGraphNeo4j() {
		return graphNeo4j;
	}

	public UserNeo4j getUserNeo4j() {
		return userNeo4j;
	}

	public PhoneNeo4j getPhoneNeo4j() {
		return phoneNeo4j;
	}

	public static Application getSingleton() {
		return singleton;
	}

	private void init() {

	}

// To test on the real database

	@EventListener(ApplicationReadyEvent.class)
	public void afterTheStart() {

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SpringApplication.run(Application.class, args);
			Application.getInstance().init();
		});
	}
}
