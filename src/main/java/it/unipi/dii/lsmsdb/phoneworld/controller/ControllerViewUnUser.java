package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
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

    @FXML
    public Button buttonPhones;
    public Button buttonLogin;
    public Button buttonUsers;
    public Button buttonSearch;
    public Label labelPhone1;
    public Label labelPhone2;
    public Label labelPhone3;
    public Label labelPhone4;
    public Label labelPhone5;
    public Label labelPhone6;
    public Label labelPhone7;
    public Label labelPhone8;
    public Label labelPhone9;
    public Label labelPhone10;
    public Label labelPhone11;
    public Label labelPhone12;
    public Label labelPhone13;
    public Label labelPhone14;
    public Label labelPhone15;
    public Label labelPhone16;
    public Label labelPhone17;
    public Label labelPhone18;
    public Label labelPhones;
    public ImageView imagePhone1;
    public ImageView imagePhone2;
    public ImageView imagePhone3;
    public ImageView imagePhone4;
    public ImageView imagePhone5;
    public ImageView imagePhone6;
    public ImageView imagePhone7;
    public ImageView imagePhone8;
    public ImageView imagePhone9;
    public ImageView imagePhone10;
    public ImageView imagePhone11;
    public ImageView imagePhone12;
    public ImageView imagePhone13;
    public ImageView imagePhone14;
    public ImageView imagePhone15;
    public ImageView imagePhone16;
    public ImageView imagePhone17;
    public ImageView imagePhone18;
    public TextField textFieldSearch;

    private final StageManager stageManager;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();

    @Autowired @Lazy
    public ControllerViewUnUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }


    public void actionClickOnPhones() {
//        labelPhone1.setText("Ciao");
    }

    public void actionSearch() {
        String text = this.textFieldSearch.getText();
        if (text.isEmpty()) {
            stageManager.switchScene(FxmlView.UNUSER);
            return;
        }
        List<Phone> phones = phoneMongo.findPhones(text);
        if (phones.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "There aren't phones with the name searched!");
            this.textFieldSearch.clear();
            return;
        }
        this.clearList(this.imageViews, this.labels);
        labelPhones.setText("'" + text + "'...");
        this.setListPhones(this.imageViews, this.labels, phones);
        this.textFieldSearch.clear();
    }

    public void actionClickOnUsers() {

        stageManager.switchScene(FxmlView.AUTORIZATION);
    }


    public void actionLogin() {
        stageManager.switchScene(FxmlView.AUTORIZATION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.buttonPhones.setDisable(true);
        List<Phone>phones = phoneMongo.findRecentPhones();
//        System.out.println(phones.size());
        if (phones.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "Database is empty!");
            try {
                App.getInstance().stop();
            } catch (Exception e) {
                logger.error("Exception occurred: " + e.getLocalizedMessage());
            }
        }
        this.imageViews = this.createImageViewList();
        this.labels = this.createLabelList();
        this.setListPhones(imageViews,labels, phones);
    }

    private void setListPhones(List<ImageView> imageViews, List<Label> labels, List<Phone> phones) {
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
