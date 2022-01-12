package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.UserNeo4j;
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
    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;

    private static final App singleton = new App();

    public static App getInstance() {
        return singleton;
    }

    public ModelBean getModelBean() {
        return modelBean;
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

    public void showInfoMessage(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(150);
        window.setOnCloseRequest(e -> window.close());
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(label, closeButton);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }


    public String getHashedPassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public String getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public void setProfileImage(ImageView imageView, String gender) {
        if (gender.equalsIgnoreCase("male")) imageView.setImage(new Image("man.png"));
        if (gender.equalsIgnoreCase("female")) imageView.setImage(new Image("woman.png"));
        if (gender.equalsIgnoreCase("not specified")) imageView.setImage(new Image("user.png"));
    }
}
