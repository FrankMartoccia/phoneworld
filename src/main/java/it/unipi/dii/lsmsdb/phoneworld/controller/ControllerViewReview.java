package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GraphPhone;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.scanner.Constant;

import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

@Component
public class ControllerViewReview implements Initializable {

    @FXML private Spinner<Integer> spinnerRating;
    @FXML private Button buttonCancel;
    @FXML private Button buttonServiceReview;
    @FXML private TextArea textAreabody;
    @FXML private TextField textFieldTitle;

    private final StageManager stageManager;

    @Autowired
    private ReviewMongo reviewMongo;

    @Autowired @Lazy
    public ControllerViewReview(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @FXML
    void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickService(ActionEvent event) {
        boolean isUpdate = (boolean) App.getInstance().getModelBean().getBean(Constants.IS_UPDATE_REVIEW);
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        Phone phone = (Phone) App.getInstance().getModelBean().getBean(Constants.SELECTED_PHONE);
        if (user == null || phone == null) {
            stageManager.showInfoMessage("ERROR", "Error in adding review!");
            return;
        }
        String title = this.textFieldTitle.getText();
        int rating = this.spinnerRating.getValue();
        String body = this.textAreabody.getText();
        Date dateOfReview = new Date();
        Review reviewGeneric = new Review.ReviewBuilder(rating, dateOfReview, title, body).username(user.getUsername()).
                phoneName(phone.getName()).build();
        if (!isUpdate) {
            if (reviewMongo.findByUsernameAndPhoneName(user.getUsername(), phone.getName()).isPresent()) {
                stageManager.showInfoMessage("INFO", "You already reviewed this phone");
                return;
            }
        } else {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean isUpdate = (boolean) App.getInstance().getModelBean().getBean(Constants.IS_UPDATE_REVIEW);
        SpinnerValueFactory<Integer> valueFactoryRating = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5);
        this.spinnerRating.setValueFactory(valueFactoryRating);
        if (!isUpdate) {
            this.buttonServiceReview.setText("ADD");
            this.spinnerRating.getValueFactory().setValue(1);
        } else {
            Review review = (Review) App.getInstance().getModelBean().getBean(Constants.SELECTED_REVIEW);
            if (review == null) {
                stageManager.showInfoMessage("ERROR", "Error in updating review!");
                return;
            }
            this.buttonServiceReview.setText("UPDATE");
            this.spinnerRating.getValueFactory().setValue(review.getRating());
            this.textFieldTitle.setText(review.getTitle());
            this.textAreabody.setText(review.getBody());
        }
    }
}

