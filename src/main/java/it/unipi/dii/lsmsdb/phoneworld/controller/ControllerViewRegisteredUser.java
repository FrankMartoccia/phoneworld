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
    private List<Record> phonesByFriends = new ArrayList<>();
    private List<Record> phonesByBrand = new ArrayList<>();
    private List<Record> usersByFollows = new ArrayList<>();
    private List<Record> usersByBrand = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();
    private User user;
    private List<GenericUser> users = new ArrayList<>();

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
        stageManager.createImageViewList(this.imageViews, image1, image2, image3, image4, image5, image6, image7, image8,
                image9, image10, image11, image12, image13, image14, image15, image16, image17, image18);
        stageManager.createLabelList(this.labels, label1, label2, label3, label4, label5, label6, label7, label8, label9,
                label10, label11, label12, label13, label14, label15, label16, label17, label18);
        user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        this.buttonLogin.setText("Hi, " + user.getUsername());
        this.initComboBox();
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
        phonesByFriends = App.getInstance().getPhoneNeo4j().findSuggestedPhonesByFriends(user.getId());
        phonesByBrand = App.getInstance().getPhoneNeo4j().findSuggestedPhonesByBrand(user.getId());
        usersByFollows = App.getInstance().getUserNeo4j().findSuggestedUsersByFriends(user.getId());
        usersByBrand = App.getInstance().getUserNeo4j().findSuggestedUsersByBrand(user.getId());
        this.initScene(phonesByFriends, phonesByBrand, false);
    }

    private void initScene(List<Record> listFriends, List<Record> listBrand, boolean isUser) {
        this.stageManager.setNullList(this.imageViews, this.labels);
        this.separator.setVisible(true);
        if (isUser) {
            this.labelDescription1.setText("USERS FOLLOWED BY");
            this.labelDescription2.setText("THOSE YOU FOLLOW");
            this.labelDescription3.setText("USERS THAT LIKE YOUR");
            this.labelDescription4.setText("FAVOURITE BRAND");
        } else {
            this.labelDescription1.setText("PHONES LIKED BY");
            this.labelDescription2.setText("THOSE YOU FOLLOW");
            this.labelDescription3.setText("PHONES OF YOUR");
            this.labelDescription4.setText("FAVOURITE BRAND");
        }
        if (listFriends.isEmpty() && listBrand.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your " +
                    "following and your favourite brand");
            return;
        } else if (listBrand.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your favourite brand");
            this.setElements(listFriends, listBrand, isUser);
            return;
        } else if (listFriends.isEmpty()) {
            stageManager.showInfoMessage("INFO", "You don't have recommendations based on your following");
            this.setElements(listFriends, listBrand, isUser);
            return;
        }
        this.setElements(listFriends, listBrand, isUser);
    }

    private void initComboBox() {
        this.comboBoxFilter.getItems().addAll("Name", "Ram (GB)", "Storage (GB)", "Chipset", "Battery Size (mAh)", "Camera Pixels (MP)");
        this.comboBoxFilter.setValue("Name");
    }

    private void setElements(List<Record> listFriends,
                             List<Record> listBrand, boolean isUser) {
        List<Record> genericList = listFriends;
        int j = 0;
        for (int i = 0;i< this.imageViews.size();i++) {
            if (i == 9) {
                genericList = listBrand;
                j = 0;
            }
            if (isUser) {
                this.labels.get(i).setText(genericList.get(j).get("username").asString());
                this.imageViews.get(i).setImage(new Image("user.png"));
            } else {
                this.labels.get(i).setText(genericList.get(j).get("newPhone").get("name").asString());
                this.imageViews.get(i).setImage(new Image(genericList.get(j).get("newPhone").get("picture").asString()));
            }
            if(j+1 == genericList.size() && genericList==listBrand) {
                break;
            }
            if (j+1 == genericList.size()) {
                i = 8;
            }
            j++;
        }
    }

    public void actionSearch(ActionEvent actionEvent) {
        this.labelDescription1.setText("");
        this.labelDescription3.setText("");
        this.labelDescription4.setText("");
        String text = this.textFieldSearch.getText().trim();
        this.textFieldSearch.clear();
        if (text.isEmpty()) {
            if (this.buttonPhones.isDisabled()) {
                this.separator.setVisible(false);
                this.phones = phoneMongo.findRecentPhones();
                this.labelDescription2.setText("LATEST PHONES...");
                this.setListPhones(this.phones);
                return;
            } else {
                this.textFieldSearch.clear();
                stageManager.showInfoMessage("ERROR", "You didn't type a username!");
                return;
            }
            }
        if (this.buttonPhones.isDisabled()) {
            this.phones = phoneMongo.findPhones(text, this.comboBoxFilter.getValue());
            if (phones.isEmpty()) {
                this.textFieldSearch.clear();
                stageManager.showInfoMessage("INFO", "There aren't phones with the parameter searched");
                return;
            }
            this.separator.setVisible(false);
            this.labelDescription2.setText("'" + text + "'...");
            this.setListPhones(this.phones);
        } else {
             users = userMongo.findUsers(text, "user");
             if (users.isEmpty()) {
                 this.textFieldSearch.clear();
                 stageManager.showInfoMessage("INFO", "There aren't users with the username searched");
                 return;
             }
             this.separator.setVisible(false);
             this.labelDescription2.setText("'" + text + "'...");
             this.setListUsers(this.users);
        }
    }

    private void setListUsers(List<GenericUser> users) {
        stageManager.setNullList(this.imageViews, this.labels);
        for (int i = 0;i < this.labels.size(); i++) {
                this.imageViews.get(i).setImage(new Image("user.png"));
                this.labels.get(i).setText(users.get(i).getUsername());
                if (i+1 == phones.size()) {
                    break;
                }
        }
    }

    private void setListPhones(List<Phone> phones) {
        stageManager.setNullList(this.imageViews, this.labels);
        for (int i = 0;i < this.labels.size(); i++) {
                this.imageViews.get(i).setImage(new Image(phones.get(i).getPicture()));
                this.labels.get(i).setText(phones.get(i).getName());
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
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
        this.comboBoxFilter.setDisable(false);
        this.initScene(phonesByFriends, phonesByBrand, false);
    }

    public void onSuggestedUsers(ActionEvent actionEvent) {
        this.buttonUsers.setDisable(true);
        this.buttonPhones.setDisable(false);
        this.comboBoxFilter.setDisable(true);
        this.initScene(usersByFollows, usersByBrand, true);
    }

    public void actionProfile(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.PROFILE);
    }

    public void actionClickOnUsers(ActionEvent actionEvent) {
        this.buttonUsers.setDisable(true);
        this.buttonPhones.setDisable(false);
        this.comboBoxFilter.setDisable(true);
        this.initScene(usersByFollows, usersByBrand, true);
    }

    public void onClickPhones(ActionEvent actionEvent) {
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
        this.comboBoxFilter.setDisable(false);
        this.initScene(phonesByFriends, phonesByBrand, false);
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
