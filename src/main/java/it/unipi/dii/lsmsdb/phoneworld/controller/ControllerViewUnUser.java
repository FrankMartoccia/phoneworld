package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ControllerViewUnUser implements Initializable {

    @Autowired
    private PhoneMongo phoneMongo;

    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);


    @FXML private Button buttonPhones;
    @FXML private Button buttonLogin;
    @FXML private Button buttonUsers;
    @FXML private Button buttonSearch;
    @FXML private Button buttonPrevious;
    @FXML private Button buttonNext;
    @FXML private Label labelPhone1;
    @FXML private Label labelPhone2;
    @FXML private Label labelPhone3;
    @FXML private Label labelPhone4;
    @FXML private Label labelPhone5;
    @FXML private Label labelPhone6;
    @FXML private Label labelPhone7;
    @FXML private Label labelPhone8;
    @FXML private Label labelPhone9;
    @FXML private Label labelPhone10;
    @FXML private Label labelPhone11;
    @FXML private Label labelPhone12;
    @FXML private Label labelPhone13;
    @FXML private Label labelPhone14;
    @FXML private Label labelPhone15;
    @FXML private Label labelPhone16;
    @FXML private Label labelPhone17;
    @FXML private Label labelPhone18;
    @FXML private Label labelPhones;
    @FXML private ImageView imagePhone1;
    @FXML private ImageView imagePhone2;
    @FXML private ImageView imagePhone3;
    @FXML private ImageView imagePhone4;
    @FXML private ImageView imagePhone5;
    @FXML private ImageView imagePhone6;
    @FXML private ImageView imagePhone7;
    @FXML private ImageView imagePhone8;
    @FXML private ImageView imagePhone9;
    @FXML private ImageView imagePhone10;
    @FXML private ImageView imagePhone11;
    @FXML private ImageView imagePhone12;
    @FXML private ImageView imagePhone13;
    @FXML private ImageView imagePhone14;
    @FXML private ImageView imagePhone15;
    @FXML private ImageView imagePhone16;
    @FXML private ImageView imagePhone17;
    @FXML private ImageView imagePhone18;
    @FXML private TextField textFieldSearch;

    private final StageManager stageManager;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();
    private int counterPages = 0;
    private int remainingElem;

    @Autowired @Lazy
    public ControllerViewUnUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void actionSearch() {
        this.buttonPrevious.setDisable(true);
        counterPages = 0;
        String text = this.textFieldSearch.getText();
        if (text.isEmpty()) {
            phones = phoneMongo.findRecentPhones();
            labelPhones.setText("LATEST PHONES...");
            this.setListPhones(this.phones);
            return;
        }
        phones = phoneMongo.findPhones(text, "Name");
        if (phones.isEmpty()) {
            stageManager.showInfoMessage("INFO", "There aren't phones with the name searched!");
            this.textFieldSearch.clear();
            return;
        }
        labelPhones.setText("'" + text + "'...");
        this.setListPhones(this.phones);
        this.buttonNext.setDisable(false);
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }

    public void actionClickOnUsers() {
        stageManager.switchScene(FxmlView.AUTORIZATION);
    }


    public void actionLogin() {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        App.getInstance().getUserNeo4j().addUser("61de1530538b5a365a79ab5b", "PaoloYeah");
//        App.getInstance().getUserNeo4j().addUser("61df1a38bbbad21499ed0881", "Paolino");
//        App.getInstance().getUserNeo4j().addUser("61df1a56bbbad21499ed0882", "Paoletto");
//        App.getInstance().getPhoneNeo4j().addPhone("phoneid1", "Xiaomi", "Mi 11", "https://fdn2.gsmarena.com/vv/bigpic/philips-s616.jpg");
//        App.getInstance().getPhoneNeo4j().addPhone("phoneid2", "Xiaomi", "Mi 12", "https://fdn2.gsmarena.com/vv/bigpic/philips-s616.jpg");
//        App.getInstance().getPhoneNeo4j().addPhone("phoneid3", "Apple", "iPhone XS", "https://fdn2.gsmarena.com/vv/bigpic/philips-s616.jpg");
//        App.getInstance().getUserNeo4j().addRelationship("61de1530538b5a365a79ab5b", "phoneid2");
//        App.getInstance().getUserNeo4j().addRelationship("61df1a38bbbad21499ed0881", "phoneid3");
//        App.getInstance().getUserNeo4j().followRelationship("61de1530538b5a365a79ab5b", "61df1a38bbbad21499ed0881");
//        App.getInstance().getUserNeo4j().followRelationship("61df1a38bbbad21499ed0881", "61df1a56bbbad21499ed0882");
//        App.getInstance().getUserNeo4j().followRelationship("61de1530538b5a365a79ab5b", "3");
//        App.getInstance().getUserNeo4j().addRelationship("3", "phoneid2");
//        App.getInstance().getUserNeo4j().addRelationship("3", "phoneid3");
        this.buttonPrevious.setDisable(true);
        this.buttonPhones.setDisable(true);
        phones = phoneMongo.findRecentPhones();
        if (phones.isEmpty()) {
            stageManager.showInfoMessage("INFO", "Database is empty!");
            try {
                Platform.exit();
                System.exit(0);
            } catch (Exception e) {
                logger.error("Exception occurred: " + e.getLocalizedMessage());
            }
        }
        stageManager.createImageViewList(this.imageViews, imagePhone1, imagePhone2, imagePhone3, imagePhone4, imagePhone5,
                imagePhone6, imagePhone7, imagePhone8, imagePhone9, imagePhone10, imagePhone11, imagePhone12, imagePhone13,
                imagePhone14, imagePhone15, imagePhone16, imagePhone17, imagePhone18);
        stageManager.createLabelList(this.labels, labelPhone1, labelPhone2, labelPhone3, labelPhone4, labelPhone5, labelPhone6,
                labelPhone7, labelPhone8, labelPhone9, labelPhone10, labelPhone11, labelPhone12, labelPhone13, labelPhone14,
                labelPhone15, labelPhone16, labelPhone17, labelPhone18);
        this.setListPhones(this.phones);
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }

    private void setListPhones(List<Phone> phones) {
        this.textFieldSearch.clear();
        for (int i = 0; i< imageViews.size();i++) {
            this.labels.get(i).setText(phones.get(i + (counterPages*18)).getName());
            this.imageViews.get(i).setImage(new Image(phones.get(i + (counterPages*18)).getPicture()));
            if (i+1 == phones.size()) {
                break;
            }
        }
        remainingElem = phones.size() - (counterPages + 1)*18;
    }

    public void actionClickOnPrevious(ActionEvent actionEvent) {
        this.buttonNext.setDisable(false);
        this.counterPages--;
        if (counterPages == 0) this.buttonPrevious.setDisable(true);
        this.setListPhones(this.phones);
    }

    public void actionClickOnNext(ActionEvent actionEvent) {
        if (counterPages==0) this.buttonPrevious.setDisable(false);
        this.counterPages++;
        this.setListPhones(this.phones);
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }
}
