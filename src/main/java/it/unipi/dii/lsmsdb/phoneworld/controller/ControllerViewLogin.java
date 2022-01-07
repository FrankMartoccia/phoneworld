package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class ControllerViewLogin {

    @FXML
    public TextField textFieldUsEm;
    public PasswordField textFieldPassword;
    public Button buttonCancel;
    public Button buttonLogin;
    public Button buttonRegister;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewLogin(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.UNUSER);
    }

    public void onClickLogin(ActionEvent actionEvent) {
    }

    public void onClickSignIn(ActionEvent actionEvent) {
    }
}
