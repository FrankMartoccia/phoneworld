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
public class ControllerViewAutorization {

    @FXML
    public Button buttonAccess;
    public Button buttonReg;
    public Button buttonBack;



    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewAutorization(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void actionOnClickLog(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    public void actionOnClickSignIn(ActionEvent actionEvent) {
    }

    public void actionOnClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.UNUSER);
    }
}
