package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ControllerViewUpdate implements Initializable {

    @FXML
    public TextField textFieldFirstName;
    public TextField textFieldLastName;
    public TextField textFieldCountry;
    public TextField textFieldCity;
    public TextField textFieldStreetName;
    public TextField textFieldEmail;
    public TextField textFieldUsername;
    public TextField textFieldPassword;
    public TextField textFieldRepeatPassword;
    public ComboBox<String> comboBoxGender;
    public Spinner<Integer> spinnerStreetNumber;
    public Spinner<Integer> spinnerYear;
    public Spinner<Integer> spinnerMonth;
    public Spinner<Integer> spinnerDay;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewUpdate(StageManager stageManager) {
        this.stageManager = stageManager;
    }


    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.PROFILE);
    }

    public void onClickUpdate(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
