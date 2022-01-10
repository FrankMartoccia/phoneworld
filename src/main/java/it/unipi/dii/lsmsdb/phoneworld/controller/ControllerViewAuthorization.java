package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


@Component
public class ControllerViewAuthorization {

    @FXML
    public Button buttonAccess;
    public Button buttonRegistration;
    public Button buttonBack;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewAuthorization(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void actionOnClickLog(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    public void actionOnClickSignUp(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.SIGNUP);
    }

    public void actionOnClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.UNUSER);
    }
}