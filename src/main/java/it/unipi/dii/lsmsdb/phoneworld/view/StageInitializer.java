package it.unipi.dii.lsmsdb.phoneworld.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class StageInitializer implements ApplicationListener<ViewUnUser.StageReadyEvent> {

    @Value("classpath:/viewUnregisteredUser.fxml")
    private Resource resource;

    @Override
    public void onApplicationEvent(ViewUnUser.StageReadyEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(resource.getURL());
            Parent parent = loader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
