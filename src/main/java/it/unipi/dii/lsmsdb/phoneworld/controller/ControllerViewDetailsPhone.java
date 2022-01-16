package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

@Component
public class ControllerViewDetailsPhone implements Initializable {

    @FXML private TableColumn<String, String> columnReviews;
    @FXML private Label labelBatterySize;
    @FXML private Label labelBatteryType;
    @FXML private Label labelBody;
    @FXML private Label labelBrand;
    @FXML private Label labelCameraPixel;
    @FXML private Label labelChipset;
    @FXML private Label labelDisplayResolution;
    @FXML private Label labelDisplaySize;
    @FXML private Label labelName;
    @FXML private Label labelOs;
    @FXML private Label labelRam;
    @FXML private Label labelReleaseYear;
    @FXML private Label labelStorage;
    @FXML private Label labelVideoPixels;
    @FXML private TableView<String> tableReviews;
    @FXML private ImageView imagePhone;
    @FXML private Button buttonNext;
    @FXML private Button buttonPrevious;

    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

    private final StageManager stageManager;
    private int counterPages = 0;
    private int remainingElem;
    private Phone phone;

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
        phone = (Phone) App.getInstance().getModelBean().getBean(Constants.SELECTED_PHONE);
        System.out.println(phone.getReviews());
        this.imagePhone.setImage(new Image(phone.getPicture()));
        this.labelName.setText("Name: " + phone.getName());
        this.labelBrand.setText("Brand: " + phone.getBrand());
        this.labelBatterySize.setText("Battery Size: " + phone.getBatterySize());
        this.labelBody.setText("Body : " + phone.getBody());
        this.labelBatteryType.setText("Battery Type: " + phone.getBatteryType());
        this.labelChipset.setText("Chipset: " + phone.getChipset());
        this.labelOs.setText("OS: " + phone.getOs());
        this.labelCameraPixel.setText("Camera Pixels: " + phone.getCameraPixels());
        this.labelDisplaySize.setText("Display Size: " + phone.getDisplaySize());
        this.labelDisplayResolution.setText("Display Resolution: " + phone.getDisplayResolution());
        this.labelRam.setText("RAM: " + phone.getRam());
        this.labelReleaseYear.setText("Release Year: " + phone.getReleaseYear());
        this.labelStorage.setText("Storage: " + phone.getStorage());
        this.labelVideoPixels.setText("Video Pixels: " + phone.getVideoPixels());
        this.columnReviews.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        this.counterPages = 0;
        this.buttonPrevious.setDisable(true);
        this.setListReviews(phone.getReviews());
        if (remainingElem < 10) this.buttonNext.setDisable(true);
    }

    private void setListReviews(List<Review> reviews) {
        if (reviews.isEmpty()) {
            remainingElem = reviews.size()-(counterPages+1)*10;
            return;
        }
        this.listReviews.clear();
        for (int i = 0;i < 10;i++) {
            this.listReviews.add(reviews.get(i + (counterPages*10)).toStringTable());
            if (i+1 == reviews.size()){
                break;
            }
        }
        tableReviews.setItems(listReviews);
        remainingElem = reviews.size()-(counterPages+1)*10;
    }

    public void onClickAddReview(ActionEvent actionEvent) {
    }

    public void onClickAddPhone(ActionEvent actionEvent) {
    }

    public void onClickRemovePhone(ActionEvent actionEvent) {
    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (counterPages==0) this.buttonPrevious.setDisable(false);
        this.counterPages++;
        this.setListReviews(phone.getReviews());
        if (remainingElem < 10) this.buttonNext.setDisable(true);
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        this.buttonNext.setDisable(false);
        this.counterPages--;
        if (counterPages == 0) this.buttonPrevious.setDisable(true);
        this.setListReviews(phone.getReviews());
    }
}
