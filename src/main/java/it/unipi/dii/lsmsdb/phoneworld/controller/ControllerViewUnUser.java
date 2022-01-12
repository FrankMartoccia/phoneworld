package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.application.Platform;
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

    @Autowired @Lazy
    public ControllerViewUnUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void actionSearch() {
        String text = this.textFieldSearch.getText();
        List<Phone> phones = new ArrayList<>();
        if (text.isEmpty()) {
            phones = phoneMongo.findRecentPhones();
            labelPhones.setText("LATEST PHONES...");
            this.setListPhones(this.imageViews, this.labels, phones);
            return;
        }
        phones = phoneMongo.findPhones(text);
        if (phones.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "There aren't phones with the name searched!");
            this.textFieldSearch.clear();
            return;
        }
        labelPhones.setText("'" + text + "'...");
        this.setListPhones(this.imageViews, this.labels, phones);
    }

    public void actionClickOnUsers() {
        stageManager.switchScene(FxmlView.AUTORIZATION);
    }


    public void actionLogin() {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        App.getInstance().getUserNeo4j().addUser("61dc0d13e321a81c2770b6f3", "Paolo", "male");
//        App.getInstance().getUserNeo4j().addUser("61dc0d40f2c39e75656068bd", "Paolino", "male");
//        App.getInstance().getUserNeo4j().addUser("61dc0d5e6776357ac980be03", "Paoletto", "not specified");
//        App.getInstance().getPhoneNeo4j().addPhone("phoneid1", "Xiaomi", "Mi 11", "https://fdn2.gsmarena.com/vv/bigpic/philips-s616.jpg");
//        App.getInstance().getPhoneNeo4j().addPhone("phoneid2", "Xiaomi", "Mi 12", "https://fdn2.gsmarena.com/vv/bigpic/philips-s616.jpg");
//        App.getInstance().getPhoneNeo4j().addPhone("phoneid3", "Apple", "iPhone XS", "https://fdn2.gsmarena.com/vv/bigpic/philips-s616.jpg");
//        App.getInstance().getUserNeo4j().addRelationship("61dc0d40f2c39e75656068bd", "phoneid2");
//        App.getInstance().getUserNeo4j().addRelationship("61dc0d5e6776357ac980be03", "phoneid3");
//        App.getInstance().getUserNeo4j().followRelationship("61dc0d13e321a81c2770b6f3", "61dc0d40f2c39e75656068bd");
//        App.getInstance().getUserNeo4j().followRelationship("61dc0d40f2c39e75656068bd", "61dc0d5e6776357ac980be03");
//        App.getInstance().getUserNeo4j().followRelationship("61dc0d13e321a81c2770b6f3", "3");
//        App.getInstance().getUserNeo4j().addRelationship("3", "phoneid2");
//        App.getInstance().getUserNeo4j().addRelationship("3", "phoneid3");

        this.buttonPhones.setDisable(true);
        List<Phone>phones = phoneMongo.findRecentPhones();
        if (phones.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "Database is empty!");
            try {
                Platform.exit();
                System.exit(0);
            } catch (Exception e) {
                logger.error("Exception occurred: " + e.getLocalizedMessage());
            }
        }
        this.imageViews = this.createImageViewList();
        this.labels = this.createLabelList();
        this.setListPhones(imageViews,labels, phones);
    }

    private void setListPhones(List<ImageView> imageViews, List<Label> labels, List<Phone> phones) {
        this.clearList(this.imageViews, this.labels);
        this.textFieldSearch.clear();
        int i = 0;
        for (ImageView imageView: imageViews) {
            Image image = new Image(phones.get(i).getPicture());
            imageView.setImage(image);
            if (i+1 == phones.size()) {
                break;
            }
            i++;
        }
        i = 0;
        for (Label label: labels) {
            label.setText(phones.get(i).getName());
            if(i+1 == phones.size()) {
                break;
            }
            i++;
        }
    }

    private List<Label> createLabelList() {
        List<Label> labels = new ArrayList<>();
        labels.add(labelPhone1);
        labels.add(labelPhone2);
        labels.add(labelPhone3);
        labels.add(labelPhone4);
        labels.add(labelPhone5);
        labels.add(labelPhone6);
        labels.add(labelPhone7);
        labels.add(labelPhone8);
        labels.add(labelPhone9);
        labels.add(labelPhone10);
        labels.add(labelPhone11);
        labels.add(labelPhone12);
        labels.add(labelPhone13);
        labels.add(labelPhone14);
        labels.add(labelPhone15);
        labels.add(labelPhone16);
        labels.add(labelPhone17);
        labels.add(labelPhone18);
        return labels;
    }

    private List<ImageView> createImageViewList() {
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(imagePhone1);
        imageViews.add(imagePhone2);
        imageViews.add(imagePhone3);
        imageViews.add(imagePhone4);
        imageViews.add(imagePhone5);
        imageViews.add(imagePhone6);
        imageViews.add(imagePhone7);
        imageViews.add(imagePhone8);
        imageViews.add(imagePhone9);
        imageViews.add(imagePhone10);
        imageViews.add(imagePhone11);
        imageViews.add(imagePhone12);
        imageViews.add(imagePhone13);
        imageViews.add(imagePhone14);
        imageViews.add(imagePhone15);
        imageViews.add(imagePhone16);
        imageViews.add(imagePhone17);
        imageViews.add(imagePhone18);
        return imageViews;
    }

    private void clearList(List<ImageView> imageViews, List<Label> labels) {
        for (ImageView imageView: imageViews) {
            imageView.setImage(null);
        }
        for (Label label: labels) {
            label.setText("");
        }
    }
}
