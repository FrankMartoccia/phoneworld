package it.unipi.dii.lsmsdb.phoneworld.controller;

import com.sun.javafx.menu.MenuItemBase;
import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
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
import java.util.ResourceBundle;
import java.util.stream.IntStream;

@Component
public class ControllerViewDetailsUser implements Initializable {

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

    private int counterPages = 0;
    private int remainingElem;

    private final ObservableList<String> listPhones = FXCollections.observableArrayList();
    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

    private final StageManager stageManager;
    private User user;


    @Autowired @Lazy
    public ControllerViewDetailsUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.user = (User) App.getInstance().getModelBean().getBean(Constants.SELECTED_USER);
        imageViewPhoto.setImage(new Image("user.png"));
        this.labelUsername.setText("Username: " + user.getUsername());
        this.labelFirstName.setText("First Name: " + user.getFirstName());
        this.labelLastName.setText("Last Name: " + user.getLastName());
        this.columnWatchList.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        this.columnReviews.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        IntStream.range(0, 10).mapToObj(Integer::toString).forEach(tableWatchList.getItems()::add);

        tableWatchList.setFixedCellSize(25);
        tableWatchList.prefHeightProperty().bind(tableWatchList.fixedCellSizeProperty().multiply(Bindings.size(tableWatchList.getItems()).add(1.10)));
        tableWatchList.minHeightProperty().bind(tableWatchList.prefHeightProperty());
        tableWatchList.maxHeightProperty().bind(tableWatchList.prefHeightProperty());
        this.setListPhones(App.getInstance().getUserNeo4j().getWatchlist(user.getId()));
        this.counterPages = 0;
        this.buttonPrevious.setDisable(true);
        this.setListReviews(user.getReviews());
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

    private void setListPhones(List<Record> watchlist) {
        this.listPhones.clear();
        for (Record record : watchlist) {
            this.listPhones.add(record.get("p").get("name").asString());
        }
        tableWatchList.setItems(listPhones);
    }

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.USER);
    }

    @FXML
    void onClickDeleteReview(ActionEvent event) {

    }

    @FXML
    void onClickRemovePhone(ActionEvent event) {

    }

    @FXML
    void onClikUpdateReview(ActionEvent event) {

    }

    @FXML
    void onClickNext(ActionEvent event) {
        if (counterPages==0) this.buttonPrevious.setDisable(false);
        this.counterPages++;
        this.setListReviews(user.getReviews());
        if (remainingElem < 10) this.buttonNext.setDisable(true);
    }

    @FXML
    void onClickPrevious(ActionEvent event) {
        this.buttonNext.setDisable(false);
        this.counterPages--;
        if (counterPages == 0) this.buttonPrevious.setDisable(true);
        this.setListReviews(user.getReviews());
    }

}
