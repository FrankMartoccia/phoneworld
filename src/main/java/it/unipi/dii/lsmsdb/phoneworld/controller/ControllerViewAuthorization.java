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

    @FXML private Button buttonRegistration;
    @FXML private Button buttonAccess;
    @FXML private Button buttonBack;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewAuthorization(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void actionOnClickLog(ActionEvent actionEvent) {
        stageManager.closeStage(this.buttonAccess);
        stageManager.showWindow(FxmlView.LOGIN);
    }

    public void actionOnClickSignUp(ActionEvent actionEvent) {
        stageManager.closeStage(this.buttonRegistration);
        stageManager.showWindow(FxmlView.SIGNUP);
    }

    public void actionOnClickCancel(ActionEvent actionEvent) {
        stageManager.closeStage(this.buttonBack);
    }
}
