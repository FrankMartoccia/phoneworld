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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private List<GenericUser> users = new ArrayList<>();
    private int counterPages = 0;
    private int remainingElem;
    private boolean isSearch = false;

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
        User user = (User) App.getInstance().getModelBean().getBean(Constants.CURRENT_USER);
        this.buttonLogin.setText("Hi, " + user.getUsername());
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
        this.buttonPrevious.setDisable(true);
        this.buttonNext.setDisable(true);
        this.initComboBox();
        this.counterPages = 0;
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
        this.comboBoxFilter.getItems().addAll("Name", "Ram (GB)", "Storage (GB)", "Chipset",
                "Battery Size (mAh)", "Camera Pixels (MP)", "Release Year");
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

    public void actionSearch() {
        this.isSearch = true;
        this.buttonPrevious.setDisable(true);
        this.labelDescription1.setText("");
        this.labelDescription3.setText("");
        this.labelDescription4.setText("");
        this.counterPages = 0;
        String text = this.textFieldSearch.getText().trim();
        this.textFieldSearch.clear();
        if(this.buttonPhones.isDisabled()) {
            this.searchPhones(text);
        }
        else {
            this.searchUsers(text);
        }
    }

    private void searchPhones(String text) {
        if (text.isEmpty() && this.comboBoxFilter.getValue().equalsIgnoreCase("Name")) {
            this.separator.setVisible(false);
            this.phones = phoneMongo.findRecentPhones();
            this.labelDescription2.setText("LATEST PHONES...");
            this.buttonNext.setDisable(false);
            this.setListPhones(this.phones);
            return;
        } else if (text.isEmpty()){
            stageManager.showInfoMessage("ERROR", "You didn't type nothing!");
            return;
        }
        this.phones = phoneMongo.findPhones(text, this.comboBoxFilter.getValue());
        if (phones.isEmpty()) {
            stageManager.showInfoMessage("INFO", "There aren't phones with the parameter searched");
            return;
        }
        this.separator.setVisible(false);
        this.labelDescription2.setText("'" + text + "'...");
        this.buttonNext.setDisable(false);
        this.setListPhones(this.phones);
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }

    private void searchUsers(String text) {
        if (text.isEmpty()) {
            this.textFieldSearch.clear();
            stageManager.showInfoMessage("ERROR", "You didn't type a username!");
            return;
        }
        users = userMongo.findUsers(text, "user");
        if (users.isEmpty()) {
            this.textFieldSearch.clear();
            stageManager.showInfoMessage("INFO", "There aren't users with the username searched");
            return;
        }
        this.separator.setVisible(false);
        this.labelDescription2.setText("'" + text + "'...");
        this.buttonNext.setDisable(false);
        this.setListUsers(this.users);
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }

    private void setListUsers(List<GenericUser> users) {
        stageManager.setNullList(this.imageViews, this.labels);
        for (int i = 0;i < this.labels.size(); i++) {
                this.imageViews.get(i).setImage(new Image("user.png"));
                this.labels.get(i).setText(users.get(i + (counterPages*18)).getUsername());
                if (i+1 == users.size()) {
                    break;
                }
        }
        remainingElem = users.size() - (counterPages + 1)*18;
    }

    private void setListPhones(List<Phone> phones) {
        stageManager.setNullList(this.imageViews, this.labels);
        for (int i = 0;i < this.labels.size(); i++) {
            labels.get(i).setText(phones.get(i + (counterPages*18)).getName());
            imageViews.get(i).setImage(new Image(phones.get(i + (counterPages*18)).getPicture()));
            if (i+1 == phones.size()) {
                    break;
            }
        }
        remainingElem = phones.size() - (counterPages + 1)*18;
    }

    public void onCloseApp(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void onSuggestedPhones(ActionEvent actionEvent) {
        this.isSearch = false;
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
        this.buttonPrevious.setDisable(true);
        this.buttonNext.setDisable(true);
        this.comboBoxFilter.setDisable(false);
        this.initScene(phonesByFriends, phonesByBrand, false);
    }

    public void onSuggestedUsers(ActionEvent actionEvent) {
        this.isSearch = false;
        this.buttonUsers.setDisable(true);
        this.buttonPhones.setDisable(false);
        this.buttonPrevious.setDisable(true);
        this.buttonNext.setDisable(true);
        this.comboBoxFilter.setDisable(true);
        this.initScene(usersByFollows, usersByBrand, true);
    }

    public void actionProfile(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.PROFILE);
    }

    public void actionClickOnUsers(ActionEvent actionEvent) {
        this.isSearch = false;
        this.buttonUsers.setDisable(true);
        this.buttonPhones.setDisable(false);
        this.buttonPrevious.setDisable(true);
        this.buttonNext.setDisable(true);
        this.comboBoxFilter.setDisable(true);
        this.initScene(usersByFollows, usersByBrand, true);
    }

    public void onClickPhones(ActionEvent actionEvent) {
        this.isSearch = false;
        this.buttonPhones.setDisable(true);
        this.buttonUsers.setDisable(false);
        this.buttonPrevious.setDisable(true);
        this.buttonNext.setDisable(true);
        this.comboBoxFilter.setDisable(false);
        this.initScene(phonesByFriends, phonesByBrand, false);
    }

    public void actionLogOut(ActionEvent actionEvent) {
        User user = null;
        App.getInstance().getModelBean().putBean(Constants.CURRENT_USER, user);
        stageManager.switchScene(FxmlView.UNUSER);
    }

    public void actionClickOnNext(ActionEvent actionEvent) {
        if (counterPages==0) this.buttonPrevious.setDisable(false);
        this.counterPages++;
        if (this.buttonPhones.isDisabled()) {
            this.setListPhones(this.phones);
        } else{
            this.setListUsers(this.users);
        }
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }

    public void actionClickOnPrevious(ActionEvent actionEvent) {
        this.buttonNext.setDisable(false);
        this.counterPages--;
        if (counterPages == 0) this.buttonPrevious.setDisable(true);
        if (this.buttonPhones.isDisabled()) {
            this.setListPhones(this.phones);
        } else{
            this.setListUsers(this.users);
        }
    }

    public void onClickEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER) this.actionSearch();

    }

    public void onClickElem(MouseEvent event) {
        int imageIndex = stageManager.getElemIndex(event);
        if (this.imageViews.get(imageIndex-1).getImage() == null) {
            return;
        }
        if (this.buttonPhones.isDisabled()) {
            this.showPhoneDetail(imageIndex);
        } else {
            this.showUserDetail(imageIndex);
        }
    }

    private void showUserDetail(int imageIndex) {
        User user = null;
        String userId = null;
        if (isSearch == false) {
            if (imageIndex < 9) {
                userId = this.usersByFollows.get(imageIndex-1).get("id").asString();
            } else {
                userId = this.usersByBrand.get(imageIndex-9).get("id").asString();
            }
            if (userMongo.findUserById(userId).isPresent()) {
                user = (User) userMongo.findUserById(userId).get();
            } else {
                stageManager.showInfoMessage("ERROR", "User not found!");
                return;
            }
            App.getInstance().getModelBean().putBean(Constants.SELECTED_USER, user);
            stageManager.switchScene(FxmlView.DETAILS_USER);
        } else {
            user = (User) this.users.get((18*counterPages) + imageIndex-1);
            App.getInstance().getModelBean().putBean(Constants.SELECTED_USER, user);
            stageManager.switchScene(FxmlView.DETAILS_USER);
        }
    }

    private void showPhoneDetail(int imageIndex) {
        Phone phone = null;
        String phoneId = null;
        if (isSearch == false) {
            if (imageIndex < 9) {
                phoneId = this.phonesByFriends.get(imageIndex - 1).get("newPhone").get("id").asString();
            } else {
                phoneId = this.phonesByBrand.get((imageIndex-1)-9).get("newPhone").get("id").asString();
            }
            if (phoneMongo.findPhoneById(phoneId).isPresent()) {
                phone = phoneMongo.findPhoneById(phoneId).get();
            } else {
                stageManager.showInfoMessage("ERROR", "Phone not found!");
                return;
            }
            App.getInstance().getModelBean().putBean(Constants.SELECTED_PHONE, phone);
            stageManager.switchScene(FxmlView.DETAILS_PHONES);
        } else {
            phone = this.phones.get((18*counterPages)+imageIndex-1);
            App.getInstance().getModelBean().putBean(Constants.SELECTED_PHONE, phone);
            stageManager.switchScene(FxmlView.DETAILS_PHONES);
        }
    }
}
