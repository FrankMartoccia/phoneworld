package it.unipi.dii.lsmsdb.phoneworld.view;

import it.unipi.dii.lsmsdb.phoneworld.config.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class StageManager {

    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private final static Logger logger = LoggerFactory.getLogger(StageManager.class);

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.springFXMLLoader = springFXMLLoader;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRoot = loadViewNode(view.getFxmlFile());
        show(viewRoot, view.getTitle());
    }

    private Parent loadViewNode(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception e){
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
        return rootNode;
    }

    private void show(final Parent rootNode, String title) {
        Scene scene = prepareScene(rootNode);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        try {
            primaryStage.show();
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    private Scene prepareScene(Parent rootNode) {
        Scene scene = primaryStage.getScene();
        if (scene == null) {
            scene = new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }
}
