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

    @FXML private Button buttonCancel;
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

    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

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
        Phone phone = (Phone) App.getInstance().getModelBean().getBean(Constants.SELECTED_PHONE);
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
        IntStream.range(0, 20).mapToObj(Integer::toString).forEach(tableReviews.getItems()::add);
        this.setListReviews(phone.getReviews());
    }

    private void setListReviews(List<Review> reviews) {
        this.listReviews.clear();
        for (Review review: reviews) {
            this.listReviews.add(review.toStringTable());
        }
        tableReviews.setItems(listReviews);
    }

    public void onClickAddReview(ActionEvent actionEvent) {
    }

    public void onClickAddPhone(ActionEvent actionEvent) {
    }

    public void onClickRemovePhone(ActionEvent actionEvent) {
    }
}
