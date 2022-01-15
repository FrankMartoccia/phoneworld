package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ControllerViewDetailsPhone implements Initializable {

    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonViewMore;
    @FXML
    private TableColumn<?, ?> columnReviews;
    @FXML
    private Label labelBatterySize;
    @FXML
    private Label labelBatteryType;
    @FXML
    private Label labelBody;
    @FXML
    private Label labelBrand;
    @FXML
    private Label labelCameraPixel1;
    @FXML
    private Label labelChipset;
    @FXML
    private Label labelDisplayResolution;
    @FXML
    private Label labelDisplaySize;
    @FXML
    private Label labelName;
    @FXML
    private Label labelName1;
    @FXML
    private Label labelOs;
    @FXML
    private Label labelRam;
    @FXML
    private Label labelReleaseYear;
    @FXML
    private Label labelStorage;
    @FXML
    private Label labelVideoPixels;
    @FXML
    private TableView<?> tableReviews;

    @FXML
    private ImageView imagePhone;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewDetailsPhone(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void onClickCancel(ActionEvent actionEvent) {
        if (App.getInstance().getModelBean().getBean(Constants.CURRENT_USER) != null) {
            stageManager.switchScene(FxmlView.USER);
        } else {
            stageManager.switchScene(FxmlView.UNUSER);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Phone phone = (Phone) App.getInstance().getModelBean().getBean(Constants.CURRENT_PHONE);
        this.imagePhone.setImage(new Image(phone.getPicture()));
    }
}
