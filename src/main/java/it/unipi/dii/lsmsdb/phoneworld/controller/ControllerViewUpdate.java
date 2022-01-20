package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @FXML private Button buttonCancel;
    @FXML private Button buttonUpdate;
    @FXML private TextField textFieldFirstName;
    @FXML private TextField textFieldLastName;
    @FXML private TextField textFieldCountry;
    @FXML private TextField textFieldCity;
    @FXML private TextField textFieldStreetName;
    @FXML private TextField textFieldEmail;
    @FXML private TextField textFieldPassword;
    @FXML private TextField textFieldRepeatPassword;
    @FXML private ComboBox<String> comboBoxGender;
    @FXML private Spinner<Integer> spinnerStreetNumber;
    @FXML private Spinner<Integer> spinnerYear;
    @FXML private Spinner<Integer> spinnerMonth;
    @FXML private Spinner<Integer> spinnerDay;

    private final StageManager stageManager;
    private User user;

    @Autowired
    private ServiceUser serviceUser;
    @Autowired
    private UserMongo userMongo;

    private final static Logger logger = LoggerFactory.getLogger(ControllerViewUpdate.class);

    @Autowired @Lazy
    public ControllerViewUpdate(StageManager stageManager) {
        this.stageManager = stageManager;
    }


    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.closeStage(this.buttonCancel);
        stageManager.showWindow(FxmlView.PROFILE);
    }

    public void onClickUpdate(ActionEvent actionEvent) {
        String firstName = this.textFieldFirstName.getText();
        String lastName = this.textFieldLastName.getText();
        String gender = this.comboBoxGender.getValue();
        String country = this.textFieldCountry.getText();
        String city = this.textFieldCity.getText();
        String streetName = this.textFieldStreetName.getText();
        int streetNumber = this.spinnerStreetNumber.getValue();
        int month = this.spinnerMonth.getValue();
        int day = this.spinnerDay.getValue();
        int year = this.spinnerYear.getValue();
        String email = this.textFieldEmail.getText();
        String password = this.textFieldPassword.getText();
        String repeatedPassword = this.textFieldRepeatPassword.getText();
        String errors = stageManager.generateStringBuilderErrorUser(firstName, lastName, gender, country, city,
                streetName, streetNumber, month, day, email, "", password, repeatedPassword, true);
        if (!errors.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You have to insert the following fields: "
            + stageManager.getErrors(errors));
            return;
        }
        if (!password.equals(repeatedPassword)){
            stageManager.showInfoMessage("ERROR", "Password and repeated password must be the same!");
            return;
        }
        try {
            User newUser = serviceUser.createUser(firstName,lastName,gender,country,city,streetName,
                    streetNumber, email, user.getUsername(),password, year, month, day);
            newUser.setId(user.getId());
            if (!userMongo.updateUser(user.getId(),newUser, "user")) {
                logger.error("Error in adding the user to MongoDB");
                stageManager.showInfoMessage("ERROR", "Error in updating the user, " +
                        "please try again");
                return;
            }
            App.getInstance().getModelBean().putBean(Constants.CURRENT_USER, newUser);
            stageManager.closeStage(this.buttonUpdate);
            stageManager.showWindow(FxmlView.PROFILE);
        } catch (Exception e) {
            logger.error("Error in adding new user: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
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
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        SpinnerValueFactory<Integer> valueFactoryYear = new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, yearValue);
        SpinnerValueFactory<Integer> valueFactoryMonth = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12);
        SpinnerValueFactory<Integer> valueFactoryDay = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 31);
        SpinnerValueFactory<Integer> valueFactoryStreetNumber = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000000);
        this.spinnerYear.setValueFactory(valueFactoryYear);
        this.spinnerMonth.setValueFactory(valueFactoryMonth);
        this.spinnerDay.setValueFactory(valueFactoryDay);
        this.spinnerStreetNumber.setValueFactory(valueFactoryStreetNumber);
        this.spinnerYear.getValueFactory().setValue(year);
        this.spinnerMonth.getValueFactory().setValue(month);
        this.spinnerDay.getValueFactory().setValue(day-1);
        this.spinnerStreetNumber.getValueFactory().setValue(user.getStreetNumber());
        this.comboBoxGender.setValue(user.getGender());
    }

    private void initComboBox() {
        this.comboBoxGender.getItems().addAll("male","female", "not specified");
    }

}
