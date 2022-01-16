package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ControllerViewSignUp implements Initializable {

    @FXML private TextField textFieldFirstName;
    @FXML private TextField textFieldLastName;
    @FXML private TextField textFieldCountry;
    @FXML private TextField textFieldCity;
    @FXML private TextField textFieldStreetName;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldUsername;
    @FXML private TextField textFieldPassword;
    @FXML private TextField textFieldRepeatPassword;
    @FXML private ComboBox<String> comboBoxGender;
    @FXML private Spinner<Integer> spinnerStreetNumber;
    @FXML private Spinner<Integer> spinnerYear;
    @FXML private Spinner<Integer> spinnerMonth;
    @FXML private Spinner<Integer> spinnerDay;

    private final StageManager stageManager;

    private final static Logger logger = LoggerFactory.getLogger(ControllerViewSignUp.class);

    @Autowired @Lazy
    public ControllerViewSignUp(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Autowired
    private UserMongo userMongo;

    @Autowired
    private ServiceUser serviceUser;

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.AUTORIZATION);
    }

    public void onClickSignUp(ActionEvent actionEvent) {
        String firstName = this.textFieldFirstName.getText();
        String lastName = this.textFieldLastName.getText();
        String gender = this.comboBoxGender.getValue();
        String country = this.textFieldCountry.getText();
        String city = this.textFieldCity.getText();
        String streetName = this.textFieldStreetName.getText();
        int streetNumber = this.spinnerStreetNumber.getValue();
        int year = this.spinnerYear.getValue();
        int month = this.spinnerMonth.getValue();
        int day = this.spinnerDay.getValue();
        String email = this.textFieldEmail.getText();
        String username = this.textFieldUsername.getText();
        String password = this.textFieldPassword.getText();
        String repeatedPassword = this.textFieldRepeatPassword.getText();
        String sbError = stageManager.generateStringBuilderError(firstName,lastName,gender,country,city,
                streetName, streetNumber, month, day, email,username,password,repeatedPassword, false);
        if (!sbError.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You have to insert the following fields: "
                    + stageManager.getErrors(sbError));
            return;
        }
        Optional<GenericUser> users = userMongo.findByUsername(username);
        if (users.isPresent()) {
            stageManager.showInfoMessage("INFO", "Username already taken!");
            return;
        }
        if (!password.equals(repeatedPassword)){
            stageManager.showInfoMessage("ERROR", "Password and repeated password must be the same!");
            return;
        }
        try {
            User user = serviceUser.createUser(firstName,lastName,gender,country,city,streetName,
                    streetNumber, email,username,password, year, month, day);
            if (!serviceUser.insertUser(user)) {
                stageManager.showInfoMessage("ERROR", "Error in adding new user, " +
                        "please try again");
                return;
            }
            App.getInstance().getModelBean().putBean(Constants.CURRENT_USER, user);
            stageManager.switchScene(FxmlView.USER);
        } catch (Exception e) {
            logger.error("Error in adding new user: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initComboBox();
        this.initSpinnerValues();
    }

    private void initSpinnerValues() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerValueFactory<Integer> valueFactoryYear = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, year);
        SpinnerValueFactory<Integer> valueFactoryMonth = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12);
        SpinnerValueFactory<Integer> valueFactoryDay = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 31);
        SpinnerValueFactory<Integer> valueFactoryStreetNumber = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000);
        this.spinnerYear.setValueFactory(valueFactoryYear);
        this.spinnerMonth.setValueFactory(valueFactoryMonth);
        this.spinnerDay.setValueFactory(valueFactoryDay);
        this.spinnerStreetNumber.setValueFactory(valueFactoryStreetNumber);
        this.spinnerYear.getValueFactory().setValue(2000);
    }

    private void initComboBox() {
        this.comboBoxGender.getItems().addAll("male","female", "not specified");
    }
}
