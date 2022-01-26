package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.Statistic;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.ReviewMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ControllerViewStatistics implements Initializable {


    @FXML private Button buttonCancel;
    @FXML private Button buttonDetails;
    @FXML private Label labelStatistics;
    @FXML private Spinner<Integer> spinnerFilter;
    @FXML private TableView<Statistic> tableViewStatistics;
    @FXML private TableColumn <Statistic, String> columnName;
    @FXML private TableColumn<Statistic, Integer> columnParameter2;
    @FXML private TableColumn <Statistic, Double> columnParameter3;

    @Autowired
    private PhoneMongo phoneMongo;

    @Autowired
    private ReviewMongo reviewMongo;

    @Autowired
    private UserMongo userMongo;

    private final ObservableList<Statistic> listStatistics = FXCollections.observableArrayList();

    private final StageManager stageManager;

    private String statisticName;

    @Autowired @Lazy
    public ControllerViewStatistics(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statisticName = (String) App.getInstance().getModelBean().getBean(Constants.SELECTED_STATISTIC);
        this.labelStatistics.setText(statisticName);
        SpinnerValueFactory<Integer> valueFactoryFilter = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        this.spinnerFilter.setValueFactory(valueFactoryFilter);
        this.spinnerFilter.getValueFactory().setValue(5);
        this.columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        this.columnParameter2.setCellValueFactory(new PropertyValueFactory<>("Param2"));
        this.columnParameter3.setCellValueFactory(new PropertyValueFactory<>("Param3"));
        this.showStatistics(statisticName);
    }

    private void showStatistics(String statisticName) {
        if (statisticName.equalsIgnoreCase("Top Rated Brands:") ||
                statisticName.equalsIgnoreCase("Younger Countries By Users:")) {
            List<Document> statistics = new ArrayList<>();
            this.buttonDetails.setVisible(false);
            this.columnName.setText("BRAND");
            this.columnParameter2.setText("# REVIEWS");
            this.columnParameter3.setText("RATING");
            Document result = phoneMongo.findTopRatedBrands(20, this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTableRating(statistics, "brand", "reviews", "rating");
        }
        if (statisticName.equalsIgnoreCase("Top Phones By Rating:")) {
            List<Document> statistics = new ArrayList<>();
            this.columnName.setText("PHONE");
            this.columnParameter2.setText("# REVIEWS");
            this.columnParameter3.setText("RATING");
            Document result = reviewMongo.findTopPhonesByRating(20, this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTableRating(statistics, "phoneName", "reviews", "rating");
        }
        if (statisticName.equalsIgnoreCase("Most Followed Users:")) {
            this.columnName.setText("USERNAME");
            this.columnParameter2.setText("# FOLLOWERS");
            List<Record> result = App.getInstance().getUserNeo4j().findMostFollowedUsers(this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            setTableRecords(result, "username", "followers");
        }
        if (statisticName.equalsIgnoreCase("Most Active Users:")) {
            List<Document> statistics = new ArrayList<>();
            this.columnName.setText("USERNAME");
            this.columnParameter2.setText("# REVIEWS");
            this.columnParameter3.setText("RATING");
            Document result = reviewMongo.findMostActiveUsers(this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTableRating(statistics, "username", "reviews", "rating");
        }
        if (statisticName.equalsIgnoreCase("Younger Countries By Users:")) {
            this.buttonDetails.setVisible(false);
            List<Document> statistics = new ArrayList<>();
            this.columnName.setText("COUNTRY");
            this.columnParameter3.setText("AGE");
            this.tableViewStatistics.getColumns().get(1).setVisible(false);
            Document result = userMongo.findYoungerCountriesByUsers(this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTableRating(statistics, "country", "", "age");
        }
        if (statisticName.equalsIgnoreCase("Top Countries By Users:")) {
            this.buttonDetails.setVisible(false);
            List<Document> statistics = new ArrayList<>();
            this.columnName.setText("COUNTRY");
            this.columnParameter2.setText("# USERS");
            this.tableViewStatistics.getColumns().get(2).setVisible(false);
            Document result = userMongo.findTopCountriesByUsers(this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTableRating(statistics, "country", "users", "");
        }
        if (statisticName.equalsIgnoreCase("Most appreciated Brands:")) {
            this.buttonDetails.setVisible(false);
            this.columnName.setText("BRAND");
            this.columnParameter2.setText("# PHONES");
            List<Record> result = App.getInstance().getPhoneNeo4j().findBestBrands(this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            setTableRecords(result, "brand", "numPhones");
        }
    }

    private void setTableRecords(List<Record> statistics, String parameter1, String parameter2) {
        this.tableViewStatistics.getColumns().get(2).setVisible(false);
        this.listStatistics.clear();
        for (Record statistic : statistics) {
            String username = statistic.get(parameter1).asString();
            int followers = statistic.get(parameter2).asInt();
            System.out.println(username);
            System.out.println(followers);
            this.listStatistics.add(new Statistic(username, followers, (double) 0));
        }
        this.tableViewStatistics.setItems(this.listStatistics);
    }

    private void setTableRating(List<Document> statistics, String parameter1, String parameter2, String parameter3) {
        this.listStatistics.clear();
        for (Document statistic : statistics) {
            String name = (String) statistic.get(parameter1);
            int param2 = 0;
            if (!parameter2.equalsIgnoreCase("")) {
                param2 = (int) statistic.get(parameter2);
            }
            double param3 = 0;
            if (!parameter3.equalsIgnoreCase("")) {
                param3 = (double) statistic.get(parameter3);
            }
            double roundPar3 = Math.round(param3 * 10.0) / 10.0;
            this.listStatistics.add(new Statistic(name, param2, roundPar3));
        }
        this.tableViewStatistics.setItems(this.listStatistics);
    }

    @FXML void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickDetails(ActionEvent event) {
        TablePosition pos = tableViewStatistics.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Statistic item = tableViewStatistics.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        Object objectName = col.getCellObservableValue(item).getValue();
        System.out.println(objectName);
        if (!objectName.getClass().getSimpleName().equalsIgnoreCase("String")) {
            stageManager.showInfoMessage("INFO", "You must select an item from the first column");
            return;
        }
        String name = (String) objectName;
        String statistic = (String) App.getInstance().getModelBean().getBean(Constants.SELECTED_STATISTIC);
        if (statistic.equals("Most Followed Users:") || statistic.equals("Most Active Users:")) {
            Optional<GenericUser> user = userMongo.findByUsername(name);
            if (user.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "User not found!");
                return;
            }
            App.getInstance().getModelBean().putBean(Constants.SELECTED_USER, user.get());
            stageManager.showWindow(FxmlView.DETAILS_USER);
            return;
        }
        Optional<Phone> phone = phoneMongo.findPhoneByName(name);
        if (phone.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "Phone not found!");
            return;
        }
        App.getInstance().getModelBean().putBean(Constants.SELECTED_PHONE, phone.get());
        stageManager.showWindow(FxmlView.DETAILS_PHONES);
    }

    public void onClickFind(ActionEvent actionEvent) {
        this.showStatistics(this.statisticName);
    }
}