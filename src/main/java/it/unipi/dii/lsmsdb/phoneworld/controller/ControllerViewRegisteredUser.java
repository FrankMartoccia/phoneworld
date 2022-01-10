package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public Label label5;
    public Label label6;
    public Label label7;
    public Label label8;
    public Label label9;
    public Label label10;
    public Label label11;
    public Label label12;
    public Label label13;
    public Label label14;
    public Label label15;
    public Label label16;
    public Label label17;
    public Label label18;
    public Label labelDescription1;
    public Label labelDescription2;
    public Label labelDescription3;
    public Label labelDescription4;
    public ImageView image1;
    public ImageView image2;
    public ImageView image3;
    public ImageView image4;
    public ImageView image5;
    public ImageView image6;
    public ImageView image7;
    public ImageView image8;
    public ImageView image9;
    public ImageView image10;
    public ImageView image11;
    public ImageView image12;
    public ImageView image13;
    public ImageView image14;
    public ImageView image15;
    public ImageView image16;
    public ImageView image17;
    public ImageView image18;
    public TextField textFieldSearch;
    public Separator separator;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<Label> labels = new ArrayList<>();
    private User user;

    private final StageManager stageManager;

    @Autowired
    private PhoneMongo phoneMongo;
    @Autowired
    private UserMongo userMongo;


    @Autowired @Lazy
    public ControllerViewRegisteredUser(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.imageViews = this.createImageViewList();
        this.labels = this.createLabelList();
        user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        this.buttonLogin.setText("Hi, " + user.getUsername());
        this.initViewPhones();
    }

    private void initViewPhones() {
        this.labelDescription1.setText("PHONES LIKED BY");
        this.labelDescription2.setText("THOSE YOU FOLLOW:");
        this.labelDescription3.setText("PHONES OF YOUR");
        this.labelDescription4.setText("FAVOURITE BRAND:");
        this.clearList(imageViews, labels);
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
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
        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        labels.add(label7);
        labels.add(label8);
        labels.add(label9);
        labels.add(label10);
        labels.add(label11);
        labels.add(label12);
        labels.add(label13);
        labels.add(label14);
        labels.add(label15);
        labels.add(label16);
        labels.add(label17);
        labels.add(label18);
        return labels;
    }

    private List<ImageView> createImageViewList() {
        List<ImageView> imageViews = new ArrayList<>();
        imageViews.add(image1);
        imageViews.add(image2);
        imageViews.add(image3);
        imageViews.add(image4);
        imageViews.add(image5);
        imageViews.add(image6);
        imageViews.add(image7);
        imageViews.add(image8);
        imageViews.add(image9);
        imageViews.add(image10);
        imageViews.add(image11);
        imageViews.add(image12);
        imageViews.add(image13);
        imageViews.add(image14);
        imageViews.add(image15);
        imageViews.add(image16);
        imageViews.add(image17);
        imageViews.add(image18);
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
        this.initViewUsers();
    }

    private void initViewUsers() {
        this.labelDescription1.setText("USERS FOLLOWED BY");
        this.labelDescription2.setText("THOSE YOU FOLLOW:");
        this.labelDescription3.setText("USERS THAT LIKE YOUR");
        this.labelDescription4.setText("FAVOURITE BRAND:");
        this.clearList(imageViews, labels);
        this.buttonPhones.setDisable(false);
        this.buttonUsers.setDisable(true);
        List<Record> usersByFollows = App.getInstance().getUserNeo4j().findSuggestedUsersByFriends(user.getId());
        List<Record> usersByBrand = App.getInstance().getUserNeo4j().findSuggestedUsersByBrand(user.getId());
        if (usersByFollows.isEmpty() && usersByBrand.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "You don't have recommendations based on your " +
                    "following and your favourite brand");
            return;
        } else if (usersByBrand.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "You don't have recommendations based on your favourite brand");
            this.setUsersByFollow(labels, imageViews, usersByFollows);
            return;
        } else if (usersByFollows.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "You don't have recommendations based on your following");
            this.setUsersByBrand(labels, imageViews, usersByBrand);
            return;
        }
        this.setUsersByFollow(labels, imageViews, usersByFollows);
        this.setUsersByBrand(labels, imageViews, usersByBrand);
    }

    private void setUsersByBrand(List<Label> labels, List<ImageView> imageViews, List<Record> usersByBrand) {
        int i;
        int j = 0;
        for (i = 9; i < usersByBrand.size() + 9;i++) {
            String id = usersByBrand.get(j).get("id").asString();
            String gender =  ((User)userMongo.findUserById(id, false)).getGender();
            if (gender.equalsIgnoreCase("male")) imageViews.get(i).setImage(new Image("man.png"));
            if (gender.equalsIgnoreCase("female")) imageViews.get(i).setImage(new Image("woman.png"));
            if (gender.equalsIgnoreCase("not specified")) imageViews.get(i).setImage(new Image("user.png"));
            j++;
        }
        j = 0;
        for (i = 9;i < usersByBrand.size() + 9;i++) {
            labels.get(i).setText(usersByBrand.get(j).get("username").asString());
            j++;
        }
    }

    private void setUsersByFollow(List<Label> labels, List<ImageView> imageViews, List<Record> usersByFollows) {
        int i;
        for (i = 0; i < imageViews.size();i++) {
            String id = usersByFollows.get(i).get("id").asString();
            User user = (User)userMongo.findUserById(id, false);
            String gender =  user.getGender();
            if (gender.equalsIgnoreCase("male")) imageViews.get(i).setImage(new Image("man.png"));
            if (gender.equalsIgnoreCase("female")) imageViews.get(i).setImage(new Image("woman.png"));
            if (gender.equalsIgnoreCase("not specified")) imageViews.get(i).setImage(new Image("user.png"));
            if (i+1 == usersByFollows.size()) {
                break;
            }
        }
        for (i = 0;i < labels.size();i++) {
            labels.get(i).setText(usersByFollows.get(i).get("username").asString());
            if(i+1 == usersByFollows.size()) {
                break;
            }
        }
    }

    public void actionSearch(ActionEvent actionEvent) {
        String text = this.textFieldSearch.getText();
        List<Phone> phones = new ArrayList<>();
        if (text.isEmpty()) {
            this.separator.setVisible(false);
            phones = phoneMongo.findRecentPhones();
            this.labelDescription1.setText("");
            this.labelDescription3.setText("");
            this.labelDescription4.setText("");
            this.labelDescription2.setText("LATEST PHONES...");
            this.setListPhones(this.imageViews, this.labels, phones);
            return;
            }
        phones = phoneMongo.findPhones(text);
        if (phones.isEmpty()) {
            App.getInstance().showInfoMessage("INFO", "There aren't phones with the name searched!");
            this.textFieldSearch.clear();
            return;
        }
        this.separator.setVisible(false);
        this.labelDescription1.setText("");
        this.labelDescription3.setText("");
        this.labelDescription4.setText("");
        this.labelDescription2.setText("'" + text + "'...");
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

    public void onCloseApp(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void onSuggestedPhones(ActionEvent actionEvent) {
        this.initViewPhones();
    }

    public void onSuggestedUsers(ActionEvent actionEvent) {
        this.initViewUsers();
    }

    public void actionProfile(ActionEvent actionEvent) {
    }
}
