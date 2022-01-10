package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserNeo4j;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.neo4j.driver.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ControllerViewRegisteredUser implements Initializable {

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
    public Label labelPhones1;
    public Label labelPhones2;
    public Label labelPhones3;
    public Label labelPhones4;
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
    public Separator separator;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();

    private final StageManager stageManager;

    @Autowired
    private PhoneMongo phoneMongo;


    @Autowired @Lazy
    public ControllerViewRegisteredUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initView();
    }

    private void initView() {
        this.labelPhones1.setText("PHONES LIKED BY");
        this.labelPhones2.setText("THOSE YOU FOLLOW:");
        this.labelPhones3.setText("PHONES OF YOUR");
        this.labelPhones4.setText("FAVOURITE BRAND:");
        this.imageViews = this.createImageViewList();
        this.labels = this.createLabelList();
        this.clearList(imageViews, labels);
        this.buttonPhones.setDisable(true);
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        this.buttonLogin.setText("Hi, " + user.getUsername());
        List<Record> phonesByFriends = App.getInstance().getPhoneNeo4j().findSuggestedPhonesByFriends(user.getId());
        List<Record> phonesByBrand = App.getInstance().getPhoneNeo4j().findSuggestedPhonesByBrand(user.getId());
        if (phonesByFriends.isEmpty() && phonesByBrand.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "You don't have recommendations based on your " +
                    "following and your favourite brand");
            return;
        } else if (phonesByBrand.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "You don't have recommendations based on your favourite brand");
            this.setPhonesByFriends(labels, imageViews, phonesByFriends);
            return;
        } else if (phonesByFriends.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "You don't have recommendations based on your following");
            this.setPhonesByBrand(labels, imageViews, phonesByBrand);
            return;
        }
        this.setPhonesByFriends(labels, imageViews, phonesByFriends);
        this.setPhonesByBrand(labels, imageViews, phonesByBrand);
    }

    private void setPhonesByFriends(List<Label> labels, List<ImageView> imageViews, List<Record> phonesByFriends) {
        int i;
        for (i = 0; i < imageViews.size();i++) {
            Image image = new Image(phonesByFriends.get(i).get("p").get("picture").asString());
//            System.out.println(phonesByFriends.get(i).get("p").get("picture").asString());
            imageViews.get(i).setImage(image);
            if (i+1 == phonesByFriends.size()) {
                break;
            }
        }
        for (i = 0;i < labels.size();i++) {
            labels.get(i).setText(phonesByFriends.get(i).get("p").get("name").asString());
            if(i+1 == phonesByFriends.size()) {
                break;
            }
        }
    }

    private void setPhonesByBrand(List<Label> labels, List<ImageView> imageViews, List<Record> phonesByBrand) {
        int i;
        int j = 0;
        for (i = 9; i < phonesByBrand.size() + 9;i++) {
            Image image = new Image(phonesByBrand.get(j).get("newPhone").get("picture").asString());
            imageViews.get(i).setImage(image);
            j++;
        }
        j = 0;
        for (i = 9;i < phonesByBrand.size() + 9;i++) {
            labels.get(i).setText(phonesByBrand.get(j).get("newPhone").get("name").asString());
            j++;
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

    public void actionClickOnUsers(ActionEvent actionEvent) {
    }

    public void actionLogin(ActionEvent actionEvent) {
    }

    public void actionSearch(ActionEvent actionEvent) {
        this.separator.setVisible(false);
        this.labelPhones1.setText("");
        this.labelPhones2.setText("");
        this.labelPhones3.setText("");
        this.labelPhones4.setText("");
            String text = this.textFieldSearch.getText();
            List<Phone> phones = new ArrayList<>();
            if (text.isEmpty()) {
                phones = phoneMongo.findRecentPhones();
                labelPhones2.setText("LATEST PHONES...");
                this.setListPhones(this.imageViews, this.labels, phones);
                return;
            }
            phones = phoneMongo.findPhones(text);
            if (phones.isEmpty()) {
                App.getInstance().showInfoMessage("INFO", "There aren't phones with the name searched!");
                this.textFieldSearch.clear();
                return;
            }
            labelPhones2.setText("'" + text + "'...");
            this.setListPhones(this.imageViews, this.labels, phones);
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
}
