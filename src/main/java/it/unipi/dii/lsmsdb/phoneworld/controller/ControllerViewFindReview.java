package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Review;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceReview;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ControllerViewFindReview implements Initializable {

    @FXML private Button buttonFind;
    @FXML private Button buttonCancel;
    @FXML private Button buttonDeleteReview;
    @FXML private TextField textFieldWord;
    @FXML private TableColumn<String, String> tableColumnReviews;
    @FXML private TableView<String> tableViewReviews;

    private List<Review> reviews = new ArrayList<>();

    private final ObservableList<String> listReviews = FXCollections.observableArrayList();

    private final StageManager stageManager;

    @Autowired
    private ReviewMongo reviewMongo;

    @Autowired
    private ServiceReview serviceReview;

    @Autowired @Lazy
    public ControllerViewFindReview(StageManager stageManager) {
        this.stageManager = stageManager;
    }


    @FXML void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickDeleteReview(ActionEvent event) {
        int tableIndex = this.tableViewReviews.getSelectionModel().getSelectedIndex();
        if (tableIndex == -1) {
            stageManager.showInfoMessage("ERROR", "You have to select a review.");
            return;
        }
        App.getInstance().getModelBean().putBean(Constants.IS_EMBEDDED, false);
        Review review = reviews.get(tableIndex);
        if (!serviceReview.deleteReview(review, null, null)) {
            stageManager.showInfoMessage("ERROR", "Error in deleting the review for this phone!");
            return;
        }
        onClickFind();
        stageManager.showInfoMessage("INFO", "Review deleted correctly.");
    }

    public void onClickFind() {
        String word = this.textFieldWord.getText().trim();
        reviews = reviewMongo.findByWord(word);
        if (reviews.isEmpty()) {
            stageManager.showInfoMessage("INFO", "There aren't reviews with the typed word");
            return;
        }
        this.setListReviews(reviews);
    }

    private void setListReviews(List<Review> reviews) {
        this.buttonDeleteReview.setDisable(false);
        this.listReviews.clear();
        for (Review review:reviews) {
            this.listReviews.add(review.toStringFind());
        }
        this.tableViewReviews.setItems(listReviews);
    }

    @FXML
    void onClickEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) this.onClickFind();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonDeleteReview.setDisable(true);
        this.tableColumnReviews.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
    }
}

