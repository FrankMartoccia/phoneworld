package it.unipi.dii.lsmsdb.phoneworld.controller;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.awt.event.ItemEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

@Component
public class ControllerViewDetails implements Initializable {

    @FXML
    private Label labelUsername;
    @FXML
    private Label labelFirstName;
    @FXML
    private Label labelLastName;
    @FXML
    private TableView<String> tableWatchList;
    @FXML
    private TableView<String> tableReviews;
    @FXML
    private TableColumn<String, String> columnWatchList;
    @FXML
    private TableColumn<String, String> columnReviews;
    @FXML
    private ImageView imageViewPhoto;

    private final ObservableList<String> listPhones = FXCollections.observableArrayList();
    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewDetails(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        App.getInstance().setProfileImage(imageViewPhoto, user.getGender());
        this.labelUsername.setText(user.getUsername());
        this.labelFirstName.setText(user.getFirstName());
        this.labelLastName.setText(user.getLastName());
        this.columnWatchList.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        this.columnReviews.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        IntStream.range(0, 10).mapToObj(Integer::toString).forEach(tableWatchList.getItems()::add);
        IntStream.range(0, 20).mapToObj(Integer::toString).forEach(tableReviews.getItems()::add);

        tableWatchList.setFixedCellSize(25);
        tableWatchList.prefHeightProperty().bind(tableWatchList.fixedCellSizeProperty().multiply(Bindings.size(tableWatchList.getItems()).add(1.01)));
        tableWatchList.minHeightProperty().bind(tableWatchList.prefHeightProperty());
        tableWatchList.maxHeightProperty().bind(tableWatchList.prefHeightProperty());
        this.setListPhones(App.getInstance().getUserNeo4j().getWatchlist(user.getId()));
        this.setListReviews(user.getReviews());
    }

    private void setListReviews(List<Review> reviews) {
        for (Review review: reviews) {
            this.listReviews.add(review.toString());
        }
        tableReviews.setItems(listReviews);
    }

    private void setListPhones(List<Record> watchlist) {
        for (Record record : watchlist) {
            this.listPhones.add(record.get("p").get("name").asString());
        }
        tableWatchList.setItems(listPhones);
    }

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.USER);
    }
}
