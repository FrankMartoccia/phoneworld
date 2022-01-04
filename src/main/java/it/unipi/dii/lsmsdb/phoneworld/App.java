package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.*;
import it.unipi.dii.lsmsdb.phoneworld.view.ViewUnUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan("it.unipi.dii.lsmsdb.*")
//@EntityScan("it.unipi.dii.lsmsdb.*")
//@EnableMongoRepositories("it.unipi.dii.lsmsdb.*")
@SpringBootApplication
public class App {

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
    private ViewUnUser viewUnUser;

    private static final App singleton = new App();

    public static App getInstance() {
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

    public ViewUnUser getViewUnUser() {
        return viewUnUser;
    }

    private void initApp() {
        this.modelBean = new ModelBean();
        this.graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                "neo4j", "PhoneWorld");
        this.userNeo4j = new UserNeo4j(graphNeo4j);
        this.phoneNeo4j = new PhoneNeo4j(graphNeo4j);
        this.viewUnUser = new ViewUnUser();
    }

    // To test on the real database

   // @EventListener(ApplicationReadyEvent.class)
   // public void afterTheStart() {
//        Date dateOfBirth = new GregorianCalendar(1965, Calendar.FEBRUARY, 11).getTime();
//        User user1 = new User("Frank", "kdasd", "dasdksamda",
//                        false, "male", "Paul", "Murray", "21",
//                        "street", "Las Vegas", "Nevada", "dnsak@gmail.com",
//                        dateOfBirth, 57);
//        User user2 = new User("Mario", "kdasd", "dasdksamda",
//                        false, "male", "Paul", "Murray", "21",
//                        "street", "Las Vegas", "Italy", "dnsak@gmail.com",
//                        dateOfBirth, 23);
//        userMongo.addUser(user1);
//        userMongo.addUser(user2);
    //}

    public static void main(String[] args) {
        javafx.application.Application.launch(ViewUnUser.class, args);
        App.getInstance().initApp();
    }

}
