package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.SimpleFormatter;

@Component
public class ControllerViewProfile implements Initializable {

    @FXML
    public Label labelUsername;
    public Label labelFirstName;
    public Label labelLastName;
    public Label labelGender;
    public Label labelCountry;
    public Label labelCity;
    public Label labelStreet;
    public Label labelBirthday;
    public Label labelEmail;
    public ImageView imageViewProfile;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewProfile(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        this.labelUsername.setText(user.getUsername());
        this.labelFirstName.setText(user.getFirstName());
        this.labelLastName.setText(user.getLastName());
        this.labelGender.setText(user.getGender());
        this.labelCountry.setText(user.getCountry());
        this.labelCity.setText(user.getCity());
        this.labelStreet.setText(user.getStreetName() + " " + user.getStreetNumber());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(user.getDateOfBirth());
        this.labelBirthday.setText(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH));
        this.labelEmail.setText(user.getEmail());
        if (user.getGender().equalsIgnoreCase("male")) this.imageViewProfile.setImage(new Image("man.png"));
        if (user.getGender().equalsIgnoreCase("female")) this.imageViewProfile.setImage(new Image("woman.png"));
        if (user.getGender().equalsIgnoreCase("user")) this.imageViewProfile.setImage(new Image("user.png"));

    }

    public void onClickUpdate() {

    }

    public void onClickDetails() {

    }
    public void onClickCancel() {
        stageManager.switchScene(FxmlView.USER);
    }
}
