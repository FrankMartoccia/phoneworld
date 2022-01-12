package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.services.ServicePhone;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceReview;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class App extends Application {

    private GraphNeo4j graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                                                               "neo4j", "PhoneWorld");
    private UserNeo4j userNeo4j = new UserNeo4j(graphNeo4j);
    private PhoneNeo4j phoneNeo4j = new PhoneNeo4j(graphNeo4j);
    private ModelBean modelBean = new ModelBean();
    private ServiceUser serviceUser = new ServiceUser();
    private ServicePhone servicePhone = new ServicePhone();
    private ServiceReview serviceReview = new ServiceReview();
    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    private static final App singleton = new App();

    public static App getInstance() {
        return singleton;
    }

    public ModelBean getModelBean() {
        return modelBean;
    }

    public UserNeo4j getUserNeo4j() {
        return userNeo4j;
    }

    public PhoneNeo4j getPhoneNeo4j() {
        return phoneNeo4j;
    }

    public ServiceUser getServiceUser() {
        return serviceUser;
    }

    public ServicePhone getServicePhone() {
        return servicePhone;
    }

    public ServiceReview getServiceReview() {
        return serviceReview;
    }

    @Override
    public void init() throws Exception {
        springContext = bootStrapSpringApp();
    }

    @Override
    public void start(Stage stage) throws Exception {
            stageManager = springContext.getBean(StageManager.class, stage);
            displayInitStage();

    }

    protected void displayInitStage() {
        stageManager.switchScene(FxmlView.UNUSER);
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    private ConfigurableApplicationContext bootStrapSpringApp() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(App.class);
        String[] args = getParameters().getRaw().toArray(String[]::new);
        return builder.run(args);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void setProfileImage(ImageView imageView, String gender) {
        if (gender.equalsIgnoreCase("male")) imageView.setImage(new Image("man.png"));
        if (gender.equalsIgnoreCase("female")) imageView.setImage(new Image("woman.png"));
        if (gender.equalsIgnoreCase("not specified")) imageView.setImage(new Image("user.png"));
    }
}
