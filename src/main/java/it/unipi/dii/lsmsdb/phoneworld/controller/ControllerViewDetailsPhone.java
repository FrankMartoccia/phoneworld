package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServicePhone;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ControllerViewDetailsPhone implements Initializable {

    @FXML private Button buttonAddPhone;
    @FXML private Button buttonRemovePhone;
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
    @FXML private Button buttonCancel;
    @FXML private Button buttonAddReview;

    private final ObservableList<String> listReviews = FXCollections.observableArrayList();
    private final static Logger logger = LoggerFactory.getLogger(ControllerViewDetailsPhone.class);

    private final StageManager stageManager;
    private int counterPages = 0;
    private Phone phone;
    private GenericUser user;
    private List<Review> reviews;

    @Autowired
    private ReviewMongo reviewMongo;
    @Autowired
    private ServicePhone servicePhone;

    @Autowired @Lazy
    public ControllerViewDetailsPhone(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.closeStage(this.buttonCancel);
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
        if (this.tableReviews.getItems().size() != 10) {
            this.buttonNext.setDisable(true);
        }
        user = (GenericUser) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        if (user != null) {
            if (user.get_class().equals("admin")) {
                this.buttonAddPhone.setVisible(false);
                this.buttonAddReview.setText("DELETE REVIEW");
            }
        }
    }

    public void onClickAddReview(ActionEvent actionEvent) {
        if (App.getInstance().getModelBean().getBean(Constants.CURRENT_USER) == null) {
            stageManager.showWindow(FxmlView.LOGIN);
            return;
        }
        user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        phone = (Phone) App.getInstance().getModelBean().getBean(Constants.SELECTED_PHONE);
        if (reviewMongo.findByUsernameAndPhoneName(user.getUsername(), phone.getName()).isPresent()) {
            stageManager.showInfoMessage("INFO", "You already reviewed this phone");
            return;
        }
        App.getInstance().getModelBean().putBean(Constants.IS_UPDATE_REVIEW, false);
        stageManager.closeStage(this.buttonAddReview);
        stageManager.showWindow(FxmlView.REVIEW);
    }

    public void onClickAddPhone(ActionEvent actionEvent) {
        if (App.getInstance().getModelBean().getBean(Constants.CURRENT_USER) == null) {
            stageManager.showWindow(FxmlView.LOGIN);
            return;
        }
        if (user.get_class().equals("user")) {
            user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
            String userId = user.getId();
            String phoneId = phone.getId();
            try {
                if (App.getInstance().getUserNeo4j().getWatchlist(userId).size() == 10) {
                    stageManager.showInfoMessage("INFO", "You have already 10 phones in your watchlist!");
                    return;
                }
                if (!App.getInstance().getUserNeo4j().getAddRelationship(userId, phoneId).isEmpty()) {
                    stageManager.showInfoMessage("ERROR", "You have already added this phone to your " +
                            "watchlist!");
                    return;
                }
                App.getInstance().getUserNeo4j().addRelationship(userId, phoneId);
                stageManager.showInfoMessage("INFO", "You have added this phone to your watchlist");
            } catch (Exception e) {
                logger.error("Error in adding the phone to the watchlist: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        } else {
            // TODO update phone
            System.out.println("Update phone");
        }
    }

    public void onClickRemovePhone(ActionEvent actionEvent) {
        if (App.getInstance().getModelBean().getBean(Constants.CURRENT_USER) == null) {
            stageManager.showWindow(FxmlView.LOGIN);
            return;
        }
        if (user.get_class().equals("user")) {
            user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
            String userId = user.getId();
            String phoneId = phone.getId();
            try {
                if (App.getInstance().getUserNeo4j().getAddRelationship(userId, phoneId).isEmpty()) {
                    stageManager.showInfoMessage("ERROR", "This phone is not in your watchlist!");
                    return;
                }
                App.getInstance().getUserNeo4j().removeRelationship(userId, phoneId);
                stageManager.showInfoMessage("INFO", "You have removed this phone from your watchlist");
            } catch (Exception e) {
                logger.error("Error in adding the phone to the watchlist: " + e.getLocalizedMessage());
            }
        } else {
            if (!servicePhone.deletePhone(phone)) {
                stageManager.showInfoMessage("ERROR", "Error in deleting the phone, try again.");
            }
        }
    }

    @FXML
    void onClickNext(ActionEvent event) {
        phone = (Phone) App.getInstance().getModelBean().getBean(Constants.SELECTED_PHONE);
        if (counterPages==0) this.buttonPrevious.setDisable(false);
        this.counterPages++;
        if (counterPages <= 4 ) {
            this.setListReviews(phone.getReviews());
        }
        if (counterPages == 4) {
            String phoneName = phone.getName();
            reviews = reviewMongo.findOldReviews(phoneName, true);
            if (reviews.isEmpty()) {
                this.buttonNext.setDisable(true);
            }
        }
        if (counterPages > 4) {
            this.setListReviews(reviews);
        }
        if (this.tableReviews.getItems().size() != 10) {
            this.buttonNext.setDisable(true);
        }
    }

    @FXML
    public void onClickPrevious(ActionEvent event) {
        this.buttonNext.setDisable(false);
        this.counterPages--;
        if (counterPages == 0) this.buttonPrevious.setDisable(true);
        if (counterPages > 4) {
            this.setListReviews(reviews);
        } else {
            this.setListReviews(phone.getReviews());
        }
    }

    private void setListReviews(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return;
        }
        this.listReviews.clear();
        if (counterPages > 4) {
            for (int i = 0;i < 10;i++) {
                this.listReviews.add(reviews.get(i + (counterPages*10)-50).toStringTable(true));
                if (i+1 == reviews.size()-(counterPages*10-50)){
                    break;
                }
            }
        } else {
            for (int i = 0;i < 10;i++) {
                this.listReviews.add(reviews.get(i + (counterPages*10)).toStringTable(true));
                if (i+1 == reviews.size()-counterPages*10){
                    break;
                }
            }
        }
        tableReviews.setItems(listReviews);
    }

}
