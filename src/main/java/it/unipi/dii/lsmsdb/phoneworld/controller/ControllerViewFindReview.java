package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ControllerViewFindReview {

    @FXML private Button buttonCancel;
    @FXML private Button buttonDeleteReview;
    @FXML private TextField textFieldTitle;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewFindReview(StageManager stageManager) {
        this.stageManager = stageManager;
    }


    @FXML void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickDeleteReview(ActionEvent event) {

    }

}

