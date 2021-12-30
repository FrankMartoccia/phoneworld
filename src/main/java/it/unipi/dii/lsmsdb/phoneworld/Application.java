package it.unipi.dii.lsmsdb.phoneworld;

import javafx.stage.Stage;
import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan("it.unipi.dii.lsmsdb.*")
//@EntityScan("it.unipi.dii.lsmsdb.*")
//@EnableMongoRepositories("it.unipi.dii.lsmsdb.*")
@SpringBootApplication
public class Application extends javafx.application.Application {

    @Autowired
    private UserMongo userMongo;

    @Autowired
    private PhoneMongo phoneMongo;

    @Autowired
    private ReviewMongo reviewMongo;

    private GraphNeo4j graphNeo4j;
    private UserNeo4j userNeo4j;
    private PhoneNeo4j phoneNeo4j;
    private ModelBean modelBean;

    private static final Application singleton = new Application();

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

    private void initApp() {
        this.modelBean = new ModelBean();
        this.graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                "neo4j", "PhoneWorld");
        this.userNeo4j = new UserNeo4j(graphNeo4j);
        this.phoneNeo4j = new PhoneNeo4j(graphNeo4j);
    }

    /*To test on the real database

        @EventListener(ApplicationReadyEvent.class)
        public void afterTheStart() {

        }
    */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Application.getInstance().initApp();
    }

    @Override
    public void start(Stage primaryStage) {

    }

}
