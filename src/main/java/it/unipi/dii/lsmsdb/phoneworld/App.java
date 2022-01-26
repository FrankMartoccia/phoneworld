package it.unipi.dii.lsmsdb.phoneworld;

import it.unipi.dii.lsmsdb.phoneworld.model.ModelBean;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.GraphNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.neo4j.UserNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App extends Application {

    private GraphNeo4j graphNeo4j = new GraphNeo4j("bolt://172.16.4.105:7687",
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

}
