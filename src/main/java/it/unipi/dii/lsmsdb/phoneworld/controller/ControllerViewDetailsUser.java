package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.*;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
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
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

@Component
public class ControllerViewDetailsUser implements Initializable {

    @FXML private Button buttonDetails;
    @FXML private Button buttonCancel;
    @FXML private Label labelUsername;
    @FXML private Label labelFirstName;
    @FXML private Label labelLastName;
    @FXML private TableView<String> tableWatchList;
    @FXML private TableView<String> tableReviews;
    @FXML private TableColumn<String, String> columnWatchList;
    @FXML private TableColumn<String, String> columnReviews;
    @FXML private ImageView imageViewPhoto;
    @FXML private Button buttonNext;
    @FXML private Button buttonPrevious;
    @FXML private Button buttonRemovePhone;
    @FXML private Button buttonDeleteReview;
    @FXML private Button buttonUpdateReview;
    @FXML private Button buttonFollow;
    @FXML private Button buttonUnfollow;

    private int counterPages = 0;
    private int remainingElem;

    private final ObservableList<String> listPhones = FXCollections.observableArrayList();
    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

    private final StageManager stageManager;
    private GenericUser user;
    private Phone phone;
    private List<Review> reviews;
    private List<Record> watchlist;

    @Autowired
    private PhoneMongo phoneMongo;

    @Autowired
    private ReviewMongo reviewMongo;

    @Autowired @Lazy
    public ControllerViewDetailsUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonRemovePhone.setVisible(false);
        this.buttonUpdateReview.setVisible(false);
        this.buttonDeleteReview.setVisible(false);
        User selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        user = (GenericUser) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        if(selectedUser == null) {
            user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        } else if (selectedUser.getId().equals(user.getId())) {
            user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);;
            this.buttonRemovePhone.setVisible(true);
            this.buttonUpdateReview.setVisible(true);
            this.buttonDeleteReview.setVisible(true);
            this.buttonUnfollow.setVisible(false);
            this.buttonFollow.setVisible(false);
        } else {
            user = selectedUser;
        }
        imageViewPhoto.setImage(new Image("user.png"));
        this.labelUsername.setText("Username: " + user.getUsername());
        this.labelFirstName.setText("First Name: " + selectedUser.getFirstName());
        this.labelLastName.setText("Last Name: " + selectedUser.getLastName());

        this.columnWatchList.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        this.columnReviews.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        IntStream.range(0, 10).mapToObj(Integer::toString).forEach(tableWatchList.getItems()::add);

        tableWatchList.setFixedCellSize(25);
        tableWatchList.prefHeightProperty().bind(tableWatchList.fixedCellSizeProperty().multiply(Bindings.size(tableWatchList.getItems()).add(1.10)));
        tableWatchList.minHeightProperty().bind(tableWatchList.prefHeightProperty());
        tableWatchList.maxHeightProperty().bind(tableWatchList.prefHeightProperty());
        watchlist = App.getInstance().getUserNeo4j().getWatchlist(user.getId());
        this.setListPhones(watchlist);
        this.counterPages = 0;
        this.buttonPrevious.setDisable(true);
        this.setListReviews(selectedUser.getReviews());
        if (this.tableReviews.getItems().size() != 10) {
            this.buttonNext.setDisable(true);
        }
        GenericUser currentUser = (GenericUser) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        if (currentUser.get_class().equals("admin")) {
            this.buttonRemovePhone.setVisible(false);
            this.buttonDeleteReview.setVisible(true);
            this.buttonUnfollow.setVisible(false);
            this.buttonFollow.setVisible(false);
        }
    }

    private void setListPhones(List<Record> watchlist) {
        this.listPhones.clear();
        for (Record record : watchlist) {
            this.listPhones.add(record.get("p").get("name").asString());
        }
        tableWatchList.setItems(listPhones);
    }

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickDeleteReview(ActionEvent event) {

    }

    @FXML
    void onClikUpdateReview(ActionEvent event) {

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
    void onClickPrevious(ActionEvent event) {
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
                this.listReviews.add(reviews.get(i + (counterPages*10)-50).toStringTable(false));
                if (i+1 == reviews.size()-(counterPages*10-50)){
                    break;
                }
            }
        } else {
            for (int i = 0;i < 10;i++) {
                this.listReviews.add(reviews.get(i + (counterPages*10)).toStringTable(false));
                if (i+1 == reviews.size()-counterPages*10){
                    break;
                }
            }
        }
        tableReviews.setItems(listReviews);
    }

    public void onClickDetails(ActionEvent actionEvent) {
        String phoneNameComplete = String.valueOf(this.tableWatchList.getSelectionModel().getSelectedItems());
        System.out.println(phoneNameComplete);
        String phoneName = phoneNameComplete.substring(1, phoneNameComplete.length()-1);
        if (phoneName.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You must select a phone");
            return;
        }
        Optional<Phone> phone = phoneMongo.findPhoneByName(phoneName);
        if (phone.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "Phone not found!");
            return;
        }
        App.getInstance().getModelBean().putBean(Constants.SELECTED_PHONE, phone.get());
        stageManager.showWindow(FxmlView.DETAILS_PHONES);
    }

    @FXML
    void onClickRemovePhone(ActionEvent event) {
        int index = this.tableWatchList.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            stageManager.showInfoMessage("ERROR", "You have to select a phone!");
            return;
        }
        String phoneId = watchlist.get(index).get("p").get("id").asString();
        App.getInstance().getUserNeo4j().removeRelationship(user.getId(), phoneId);
        stageManager.showInfoMessage("INFO", "You have removed the phone from your watchlist");
        this.watchlist = App.getInstance().getUserNeo4j().getWatchlist(user.getId());
        setListPhones(watchlist);
    }

    public void onClickFollow(ActionEvent actionEvent) {
        User curentUser = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        User selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        if (curentUser == null || selectedUser == null) {
            stageManager.showInfoMessage("ERROR", "Error in adding the follow relationship!");
            return;
        }
        String idCurrentUser = curentUser.getId();
        String idSelectedUser = selectedUser.getId();
        if (!App.getInstance().getUserNeo4j().getFollowRelationship(idCurrentUser, idSelectedUser).isEmpty()) {
            stageManager.showInfoMessage("INFO", "You already follow this user");
            return;
        }
        if (App.getInstance().getUserNeo4j().followRelationship(idCurrentUser, idSelectedUser)) {
            stageManager.showInfoMessage("INFO", "Now you follow this user");
            return;
        }
        stageManager.showInfoMessage("ERROR", "Error in adding the follow relationship!");
    }

    public void onClickUnfollow(ActionEvent actionEvent) {
        User curentUser = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        User selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        System.out.println(curentUser.get_class());
        if (curentUser == null || selectedUser == null) {
            stageManager.showInfoMessage("ERROR", "Error in unfollowing relationship!");
            return;
        }
        String idCurrentUser = curentUser.getId();
        String idSelectedUser = selectedUser.getId();
        if (App.getInstance().getUserNeo4j().getFollowRelationship(idCurrentUser, idSelectedUser).isEmpty()) {
            stageManager.showInfoMessage("INFO", "You are not following this user");
            return;
        }
        if (App.getInstance().getUserNeo4j().unfollowRelationship(idCurrentUser, idSelectedUser)) {
            stageManager.showInfoMessage("INFO", "Now you are not following this user");
            return;
        }
        stageManager.showInfoMessage("ERROR","Error in unfollowing relationship!");
    }
}
