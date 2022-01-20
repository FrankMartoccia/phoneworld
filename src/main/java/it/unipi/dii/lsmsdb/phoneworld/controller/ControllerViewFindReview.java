package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ControllerViewFindReview {

    @FXML private Button buttonFind;
    @FXML private Button buttonCancel;
    @FXML private Button buttonDeleteReview;
    @FXML private TextField textFieldword;
    @FXML private TableColumn<String, String> tableColumnReviews;
    @FXML private TableView<String> tableViewReviews;

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

    public void onClickFind() {

    }

    @FXML
    void onClickEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) this.onClickFind();
    }
}

