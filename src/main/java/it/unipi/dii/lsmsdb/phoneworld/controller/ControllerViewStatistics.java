package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
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
import javafx.scene.control.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

@Component
public class ControllerViewStatistics implements Initializable {

    @FXML private Button buttonCancel;
    @FXML private Button buttonDetails;
    @FXML private TableColumn<String, String> columnStatistics;
    @FXML private Label labelStatistics;
    @FXML private Spinner<Integer> spinnerFilter;
    @FXML private TableView<String> tableViewStatistics;

    @Autowired
    private PhoneMongo phoneMongo;

    @Autowired
    private ReviewMongo reviewMongo;

    private final ObservableList<String> listStatistics = FXCollections.observableArrayList();

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
        this.spinnerFilter.getValueFactory().setValue(10);
        this.columnStatistics.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        IntStream.range(0, this.spinnerFilter.getValue()).mapToObj(Integer::toString).forEach(tableViewStatistics.getItems()::add);
        tableViewStatistics.setFixedCellSize(25);
        tableViewStatistics.prefHeightProperty().bind(tableViewStatistics.fixedCellSizeProperty().
                multiply(Bindings.size(tableViewStatistics.getItems()).add(1.10)));
        tableViewStatistics.minHeightProperty().bind(tableViewStatistics.prefHeightProperty());
        tableViewStatistics.maxHeightProperty().bind(tableViewStatistics.prefHeightProperty());
        this.showStatistics(statisticName);
    }

    private void showStatistics(String statisticName) {
        List<Document> statistics = new ArrayList<>();
        if (statisticName.equalsIgnoreCase("Top Rated Brands:")) {
            this.buttonDetails.setVisible(false);
            this.columnStatistics.setText("BRANDS");
            Document result = phoneMongo.findTopRatedBrands(20, this.spinnerFilter.getValue());
            System.out.println(spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTable(statistics, "brand");
        }
        if (statisticName.equalsIgnoreCase("Top Phones By Rating:")) {
            this.columnStatistics.setText("PHONES");
            Document result = reviewMongo.findTopPhonesByRating(20, this.spinnerFilter.getValue());
            if (result.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "Statistic not found!");
                return;
            }
            statistics = (List<Document>) result.get("results");
            this.setTable(statistics, "phoneName");
        }
    }

    private void setTable(List<Document> statistics, String parameter) {
        this.listStatistics.clear();
        for (int i = 0;i < statistics.size();i++) {
            System.out.println(statistics.get(i).get(parameter));
            this.listStatistics.add((String) statistics.get(i).get(parameter));
        }
        this.tableViewStatistics.setItems(this.listStatistics);
    }

    @FXML void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickDetails(ActionEvent event) {
        String phoneNameComplete = String.valueOf(this.tableViewStatistics.getSelectionModel().getSelectedItems());
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

    public void onClickFind(ActionEvent actionEvent) {
        this.showStatistics(this.statisticName);
    }
}

