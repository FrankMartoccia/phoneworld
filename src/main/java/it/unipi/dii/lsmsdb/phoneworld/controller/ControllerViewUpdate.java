package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public TextField textFieldPassword;
    public TextField textFieldRepeatPassword;
    public ComboBox<String> comboBoxGender;
    public Spinner<Integer> spinnerStreetNumber;
    public Spinner<Integer> spinnerYear;
    public Spinner<Integer> spinnerMonth;
    public Spinner<Integer> spinnerDay;

    private final StageManager stageManager;
    private User user;

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
        user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        textFieldFirstName.setText(user.getFirstName());
        textFieldLastName.setText(user.getLastName());
        textFieldCountry.setText(user.getCountry());
        textFieldCity.setText(user.getCity());
        textFieldStreetName.setText(user.getStreetName());
        textFieldEmail.setText(user.getEmail());
        initSpinnerValues();
        initComboBox();
    }

    private void initSpinnerValues() {
        int yearValue = Calendar.getInstance().get(Calendar.YEAR);
        Date date = user.getDateOfBirth();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        SpinnerValueFactory<Integer> valueFactoryYear = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, yearValue);
        SpinnerValueFactory<Integer> valueFactoryMonth = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        SpinnerValueFactory<Integer> valueFactoryDay = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31);
        SpinnerValueFactory<Integer> valueFactoryStreetNumber = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000);
        spinnerDay.setEditable(true);
        this.spinnerYear.setValueFactory(valueFactoryYear);
        this.spinnerMonth.setValueFactory(valueFactoryMonth);
        this.spinnerDay.setValueFactory(valueFactoryDay);
        this.spinnerStreetNumber.setValueFactory(valueFactoryStreetNumber);
    }

    private void initComboBox() {
        this.comboBoxGender.getItems().addAll("male","female", "not specified");
    }

}
