package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
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
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ControllerViewRegisteredUser implements Initializable {

    @FXML private Button buttonPhones;
    @FXML private Button buttonLogin;
    @FXML private Button buttonUsers;
    @FXML private Button buttonPrevious;
    @FXML private Button buttonNext;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private Label label5;
    @FXML private Label label6;
    @FXML private Label label7;
    @FXML private Label label8;
    @FXML private Label label9;
    @FXML private Label label10;
    @FXML private Label label11;
    @FXML private Label label12;
    @FXML private Label label13;
    @FXML private Label label14;
    @FXML private Label label15;
    @FXML private Label label16;
    @FXML private Label label17;
    @FXML private Label label18;
    @FXML private Label labelDescription1;
    @FXML private Label labelDescription2;
    @FXML private Label labelDescription3;
    @FXML private Label labelDescription4;
    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;
    @FXML private ImageView image4;
    @FXML private ImageView image5;
    @FXML private ImageView image6;
    @FXML private ImageView image7;
    @FXML private ImageView image8;
    @FXML private ImageView image9;
    @FXML private ImageView image10;
    @FXML private ImageView image11;
    @FXML private ImageView image12;
    @FXML private ImageView image13;
    @FXML private ImageView image14;
    @FXML private ImageView image15;
    @FXML private ImageView image16;
    @FXML private ImageView image17;
    @FXML private ImageView image18;
    @FXML private TextField textFieldSearch;
    @FXML private Separator separator;
    @FXML private ComboBox<String> comboBoxFilter;

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
        this.initComboBox();
        this.initViewPhones();
    }

    private void initComboBox() {
        this.comboBoxFilter.getItems().addAll("Name", "Ram (GB)", "Storage (GB)", "Chipset", "Battery Size (mAh)", "Camera Pixels (MP)");
        this.comboBoxFilter.setValue("Name");
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
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your " +
                    "following and your favourite brand");
            return;
        } else if (phonesByBrand.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your favourite brand");
            this.setListByFriends(labels, imageViews, phonesByFriends, false);
            return;
        } else if (phonesByFriends.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your following");
            this.setListByBrand(labels, imageViews, phonesByBrand, false);
            return;
        }
        this.setListByFriends(labels, imageViews, phonesByFriends, false);
        this.setListByBrand(labels, imageViews, phonesByBrand, false);
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
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your " +
                    "following and your favourite brand");
            return;
        } else if (usersByBrand.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your favourite brand");
            this.setListByFriends(labels, imageViews, usersByFollows, true);
            return;
        } else if (usersByFollows.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your following");
            this.setListByBrand(labels, imageViews, usersByBrand, true);
            return;
        }
        this.setListByFriends(labels, imageViews, usersByFollows, true);
        this.setListByBrand(labels, imageViews, usersByBrand, true);
    }


    private void setListByBrand(List<Label> labels, List<ImageView> imageViews, List<Record> list, boolean isUser){
        int i;
        int j = 0;
        for (i = 9; i < list.size() + 9;i++) {
            if (isUser) {
                labels.get(i).setText(list.get(j).get("username").asString());
                imageViews.get(i).setImage(new Image("user.png"));
            } else {
                imageViews.get(i).setImage(new Image(list.get(j).get("newPhone").get("picture").asString()));
                labels.get(i).setText(list.get(j).get("newPhone").get("name").asString());
            }
            j++;
        }
    }

    private void setListByFriends(List<Label> labels, List<ImageView> imageViews, List<Record> list, boolean isUser) {
        for (int i = 0;i< imageViews.size();i++) {
            if (isUser) {
                Optional<GenericUser> result = userMongo.findUserById(list.get(i).get("id").asString());
                if (result.isPresent()) {
                    User user = (User) result.get();
                    labels.get(i).setText(list.get(i).get("username").asString());
                    imageViews.get(i).setImage(new Image("user.png"));
                }
            } else {
                labels.get(i).setText(list.get(i).get("newPhone").get("name").asString());
                imageViews.get(i).setImage(new Image(list.get(i).get("newPhone").get("picture").asString()));
            }
            if(i+1 == list.size()) {
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
        phones = phoneMongo.findPhones(text, this.comboBoxFilter.getValue());
        if (phones.isEmpty()) {
            stageManager.showInfoMessage("INFO", "There aren't phones with the name searched!");
            this.textFieldSearch.clear();
            return;
        }
        this.separator.setVisible(false);
        this.labelDescription1.setText("");
        this.labelDescription3.setText("");
        this.labelDescription4.setText("");
        this.labelDescription2.setText("'" + text + "'...");
        this.setListPhones(this.imageViews, this.labels, phones);
    }     //TODO add Search List Users

    private void setListPhones(List<ImageView> imageViews, List<Label> labels, List<Phone> phones) {
        this.clearList(this.imageViews, this.labels);
        this.textFieldSearch.clear();
        for (int i = 0;i < labels.size(); i++) {
            imageViews.get(i).setImage(new Image(phones.get(i).getPicture()));
            labels.get(i).setText(phones.get(i).getName());
            if (i+1 == phones.size()) {
                break;
            }
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
        stageManager.switchScene(FxmlView.PROFILE);
    }

    public void onClickPhones(ActionEvent actionEvent) {
        this.initViewPhones();
    }

    public void actionLogOut(ActionEvent actionEvent) {
        User user = null;
        App.getInstance().getModelBean().putBean(Constants.CURRENT_USER, user);
        stageManager.switchScene(FxmlView.UNUSER);
    }

    public void actionClickOnNext(ActionEvent actionEvent) {
    }

    public void actionClickOnPrevious(ActionEvent actionEvent) {
    }
}
