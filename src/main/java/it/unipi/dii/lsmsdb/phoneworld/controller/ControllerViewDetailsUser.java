package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceReview;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
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

    @FXML private Button buttonDetailsFollowed;
    @FXML private Button buttonRemoveUser;
    @FXML private Button buttonDetailsPhone;
    @FXML private Button buttonCancel;
    @FXML private Label labelUsername;
    @FXML private Label labelFirstName;
    @FXML private Label labelLastName;
    @FXML private TableView<String> tableFollowed;
    @FXML private TableView<String> tableWatchList;
    @FXML private TableView<String> tableReviews;
    @FXML private TableColumn<String, String> columnFollowed;
    @FXML private TableColumn<String, String> columnWatchList;
    @FXML private TableColumn<String, String> columnReviews;
    @FXML private ImageView imageViewPhoto;
    @FXML private Button buttonNext;
    @FXML private Button buttonPrevious;
    @FXML private Button buttonRemovePhone;
    @FXML private Button buttonDeleteReview;
    @FXML private Button buttonUpdateReview;
    @FXML private Button buttonFollow;
    @FXML private Button buttonUnfollowDelete;

    private int counterPages = 0;

    private final ObservableList<String> listPhones = FXCollections.observableArrayList();
    private final ObservableList<String> listFollowed = FXCollections.observableArrayList();
    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

    private final StageManager stageManager;
    private List<Review> reviews;
    private List<Record> watchlist;
    private List<Record> followed;

    @Autowired
    private PhoneMongo phoneMongo;

    @Autowired
    private UserMongo userMongo;


    @Autowired
    private ReviewMongo reviewMongo;

    @Autowired
    private ServiceReview serviceReview;

    @Autowired
    private ServiceUser serviceUser;

    @Autowired @Lazy
    public ControllerViewDetailsUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.columnWatchList.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        this.columnFollowed.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        this.columnReviews.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        IntStream.range(0, 12).mapToObj(Integer::toString).forEach(tableWatchList.getItems()::add);
        IntStream.range(0, 12).mapToObj(Integer::toString).forEach(tableFollowed.getItems()::add);
        tableFollowed.setFixedCellSize(25);
        tableFollowed.prefHeightProperty().bind(tableFollowed.fixedCellSizeProperty().multiply(Bindings.size(tableFollowed.getItems()).add(1.10)));
        tableFollowed.minHeightProperty().bind(tableFollowed.prefHeightProperty());
        tableFollowed.maxHeightProperty().bind(tableFollowed.prefHeightProperty());
        tableWatchList.setFixedCellSize(25);
        tableWatchList.prefHeightProperty().bind(tableWatchList.fixedCellSizeProperty().multiply(Bindings.size(tableWatchList.getItems()).add(1.10)));
        tableWatchList.minHeightProperty().bind(tableWatchList.prefHeightProperty());
        tableWatchList.maxHeightProperty().bind(tableWatchList.prefHeightProperty());
        this.buttonRemoveUser.setVisible(false);
        this.buttonRemovePhone.setVisible(false);
        this.buttonUpdateReview.setVisible(false);
        this.buttonDeleteReview.setVisible(false);

        User selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        GenericUser user = (GenericUser) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        if (user.get_class().equals("admin")) {
            this.buttonDeleteReview.setVisible(true);
            this.buttonUnfollowDelete.setVisible(true);
            this.buttonUnfollowDelete.setText("DELETE");
            this.buttonFollow.setVisible(false);
        }
        if(selectedUser == null) {
            selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
            this.buttonRemovePhone.setVisible(true);
            this.buttonRemoveUser.setVisible(true);
            this.buttonUpdateReview.setVisible(true);
            this.buttonDeleteReview.setVisible(true);
            this.buttonUnfollowDelete.setVisible(false);
            this.buttonFollow.setVisible(false);
        }
        imageViewPhoto.setImage(new Image("user.png"));
        this.labelFirstName.setText("First Name: " + (selectedUser).getFirstName());
        this.labelLastName.setText("Last Name: " + (selectedUser).getLastName());
        this.labelUsername.setText("Username: " + selectedUser.getUsername());
        watchlist = App.getInstance().getUserNeo4j().getWatchlist(selectedUser.getId());
        followed = App.getInstance().getUserNeo4j().getFollowed(selectedUser.getId());
        this.setListPhones(watchlist);
        this.setListFollowed(followed);
        this.counterPages = 0;
        this.buttonPrevious.setDisable(true);
        this.setListReviews(selectedUser.getReviews());
        if (this.tableReviews.getItems().size() != 10) {
            this.buttonNext.setDisable(true);
        }
    }

    private void setListFollowed(List<Record> followed) {
        this.listFollowed.clear();
        for (Record record : followed) {
            this.listFollowed.add(record.get("u2").get("username").asString());
        }
        tableFollowed.setItems(listFollowed);
    }

    private void setListPhones(List<Record> watchlist) {
        this.listPhones.clear();
        for (Record record : watchlist) {
            this.listPhones.add(record.get("p").get("name").asString());
        }
        tableWatchList.setItems(listPhones);
    }

    public void onClickCancel(ActionEvent actionEvent) {
        List<User> users = (List<User>) App.getInstance().getModelBean().getBean(Constants.USERS_PATH);
        if (users.isEmpty()) {
            stageManager.closeStage(this.buttonCancel);
            return;
        }
        App.getInstance().getModelBean().putBean(Constants.SELECTED_USER, users.get(users.size()-1));
        users.remove(users.size()-1);
        App.getInstance().getModelBean().putBean(Constants.USERS_PATH, users);
        stageManager.closeStage(this.buttonCancel);
        stageManager.showWindow(FxmlView.DETAILS_USER);
    }

    @FXML
    void onClickDeleteReview(ActionEvent event) {
        User user = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        if(user == null) {
            user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        }
        int tableIndex = this.tableReviews.getSelectionModel().getSelectedIndex();
        if (tableIndex == -1) {
            stageManager.showInfoMessage("ERROR", "You have to select a review.");
            return;
        }
        Review selectedReview = serviceReview.getSelectedReview(counterPages, tableIndex, user,
                null, reviews);
        if (!serviceReview.deleteReview(selectedReview, null, user)) {
            stageManager.showInfoMessage("ERROR", "Error in deleting the review for this phone!");
            return;
        }
        stageManager.closeStage(this.buttonDeleteReview);
        stageManager.showWindow(FxmlView.DETAILS_USER);
        stageManager.showInfoMessage("INFO", "Review deleted correctly.");
    }

    @FXML
    void onClickUpdateReview(ActionEvent event) {
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        int tableIndex = this.tableReviews.getSelectionModel().getSelectedIndex();
        if (tableIndex == -1) {
            stageManager.showInfoMessage("ERROR", "You have to select a review.");
            return;
        }
        Review selectedReview = serviceReview.getSelectedReview(counterPages, tableIndex, user,
                null, reviews);
        App.getInstance().getModelBean().putBean(Constants.IS_UPDATE, true);
        App.getInstance().getModelBean().putBean(Constants.SELECTED_USER, null);
        App.getInstance().getModelBean().putBean(Constants.SELECTED_REVIEW, selectedReview);
        stageManager.showWindow(FxmlView.REVIEW);
        stageManager.closeStage(this.buttonUpdateReview);
    }

    @FXML
    void onClickNext(ActionEvent event) {
        User user = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        if (counterPages==0) this.buttonPrevious.setDisable(false);
        this.counterPages++;
        if (counterPages <= 4 ) {
            this.setListReviews(user.getReviews());
        }
        if (counterPages == 4) {
            String username = user.getUsername();
            reviews = reviewMongo.findOldReviews(username, false);
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
        User user = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        this.buttonNext.setDisable(false);
        this.counterPages--;
        if (counterPages == 0) this.buttonPrevious.setDisable(true);
        if (counterPages > 4) {
            this.setListReviews(reviews);
        } else {
            this.setListReviews(user.getReviews());
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

    public void onClickDetailsPhone(ActionEvent actionEvent) {
        String phoneNameComplete = String.valueOf(this.tableWatchList.getSelectionModel().getSelectedItems());
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
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        App.getInstance().getUserNeo4j().removeRelationship(user.getId(), phoneId);
        stageManager.showInfoMessage("INFO", "You have removed the phone from your watchlist");
        this.watchlist = App.getInstance().getUserNeo4j().getWatchlist(user.getId());
        setListPhones(watchlist);
    }

    public void onClickFollow(ActionEvent actionEvent) {
        User currentUser = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        User selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        if (currentUser == null || selectedUser == null) {
            stageManager.showInfoMessage("ERROR", "Error in adding the follow relationship!");
            return;
        }
        String idCurrentUser = currentUser.getId();
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

    public void onClickUnfollowDelete(ActionEvent actionEvent) {
        GenericUser genericUser = (GenericUser) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        User selectedUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        if (genericUser.get_class().equals("admin")) {
           if (!serviceUser.deleteUser(selectedUser)){
               stageManager.showInfoMessage("ERROR", "Error in deleting the user!");
               return;
           }
           stageManager.closeStage(this.buttonUnfollowDelete);
           stageManager.showInfoMessage("INFO", "User deleted correctly");
           return;
        }
        User currentUser = (User) genericUser;
        if (currentUser == null || selectedUser == null) {
            stageManager.showInfoMessage("ERROR", "Error in unfollowing relationship!");
            return;
        }
        String idCurrentUser = currentUser.getId();
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

    public void onClickDetailsFollowed(ActionEvent actionEvent) {
        String usernameComplete = String.valueOf(this.tableFollowed.getSelectionModel().getSelectedItems());
        String username = usernameComplete.substring(1, usernameComplete.length()-1);
        if (username.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You must select a user");
            return;
        }
        Optional<GenericUser> genericUser = userMongo.findByUsername(username);
        if (genericUser.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "User not found!");
            return;
        }
        User selectedUser = (User) genericUser.get();
        stageManager.closeStage(this.buttonDetailsFollowed);
        User currentUser = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        List<User> users = (List<User>) App.getInstance().getModelBean().getBean(Constants.USERS_PATH);
        users.add(currentUser);
        App.getInstance().getModelBean().putBean(Constants.USERS_PATH, users);
        App.getInstance().getModelBean().putBean(Constants.SELECTED_USER, selectedUser);
        stageManager.showWindow(FxmlView.DETAILS_USER);
    }

    public void onClickRemoveUser(ActionEvent actionEvent) {
        GenericUser currentUser = (GenericUser) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        int index = this.tableFollowed.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            stageManager.showInfoMessage("ERROR", "You have to select an user!");
            return;
        }
        String id = this.followed.get(index).get("u2").get("id").asString();
        if (!App.getInstance().getUserNeo4j().unfollowRelationship(currentUser.getId(), id)) {
            stageManager.showInfoMessage("ERROR", "Error in deleting the user in the followed list!");
            return;
        }
        stageManager.showInfoMessage("INFO", "User deleted from your followed list");
        this.followed = App.getInstance().getUserNeo4j().getFollowed(currentUser.getId());
        this.setListFollowed(followed);
    }
}
