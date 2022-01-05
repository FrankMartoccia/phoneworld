package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserNeo4j;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App extends Application {

    private GraphNeo4j graphNeo4j;
    private UserNeo4j userNeo4j;
    private PhoneNeo4j phoneNeo4j;
    private ModelBean modelBean;
    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

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

    private void initApp() {
        this.modelBean = new ModelBean();
        this.graphNeo4j = new GraphNeo4j("bolt://localhost:7687",
                "neo4j", "PhoneWorld");
        this.userNeo4j = new UserNeo4j(graphNeo4j);
        this.phoneNeo4j = new PhoneNeo4j(graphNeo4j);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(App.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/viewUnregisteredUser.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        rootNode = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
            initApp();
            stage.setTitle("PhoneWorld");
            stage.setScene(new Scene(rootNode));
            stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }


//    public void showInfoMessage(String title, String message) {
//        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
//        window.setTitle(title);
//        window.setMinWidth(300);
//        window.setMinHeight(150);
//        window.setOnCloseRequest(e -> window.close());
//        Label label = new Label();
//        label.setText(message);
//        Button closeButton = new Button("Close");
//        closeButton.setOnAction(e -> window.close());
//        VBox vBox = new VBox(10);
//        vBox.getChildren().addAll(label, closeButton);
//        vBox.setAlignment(Pos.CENTER);
//        Scene scene = new Scene(vBox);
//        window.setScene(scene);
//        window.show();
//    }
}
