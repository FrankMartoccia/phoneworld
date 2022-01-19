package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.PhoneMongo;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    @FXML private ImageView image1;
    @FXML private ImageView image10;
    @FXML private ImageView image11;
    @FXML private ImageView image12;
    @FXML private ImageView image13;
    @FXML private ImageView image14;
    @FXML private ImageView image15;
    @FXML private ImageView image16;
    @FXML private ImageView image17;
    @FXML private ImageView image18;
    @FXML private ImageView image2;
    @FXML private ImageView image3;
    @FXML private ImageView image4;
    @FXML private ImageView image5;
    @FXML private ImageView image6;
    @FXML private ImageView image7;
    @FXML private ImageView image8;
    @FXML private ImageView image9;
    @FXML private Label label1;
    @FXML private Label label10;
    @FXML private Label label11;
    @FXML private Label label12;
    @FXML private Label label13;
    @FXML private Label label14;
    @FXML private Label label15;
    @FXML private Label label16;
    @FXML private Label label17;
    @FXML private Label label18;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private Label label5;
    @FXML private Label label6;
    @FXML private Label label7;
    @FXML private Label label8;
    @FXML private Label label9;
    @FXML private Label labelPhones;

    @FXML private TextField textFieldSearch;

    private final StageManager stageManager;

    @Autowired
    ServiceUser serviceUser;

    @Autowired
    UserMongo userMongo;

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

    }

    public void actionClickOnUsers() {
        stageManager.showWindow(FxmlView.AUTORIZATION);
    }


    public void actionLogin() {
        stageManager.showWindow(FxmlView.LOGIN);
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
//        String salt = serviceUser.getSalt();
//        String hashedPassword = serviceUser.getHashedPassword("admin", salt);
//        GenericUser admin = new Admin("martocciaAdmin",salt,hashedPassword, "admin");
//        userMongo.addUser(admin);
        this.counterPages = 0;
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
        stageManager.createImageViewList(this.imageViews, image1, image2, image3, image4, image5,
                image6, image7, image8, image9, image10, image11, image12, image13,
                image14, image15, image16, image17, image18);
        stageManager.createLabelList(this.labels, label1, label2, label3, label4, label5, label6,
                label7, label8, label9, label10, label11, label12, label13, label14,
                label15, label16, label17, label18);
        this.setListPhones(this.phones);
        if (remainingElem < imageViews.size()) this.buttonNext.setDisable(true);
    }

    private void setListPhones(List<Phone> phones) {
        stageManager.setNullList(this.imageViews, this.labels);
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

    public void onClickEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) this.actionSearch();
    }

    public void onClickImage(MouseEvent event) {
        int imageIndex = stageManager.getElemIndex(event);
        imageIndex--;
        Phone phone = this.phones.get((18*counterPages)+imageIndex);
        App.getInstance().getModelBean().putBean(Constants.SELECTED_PHONE, phone);
        stageManager.showWindow(FxmlView.DETAILS_PHONES);
    }

    public void onClickClose(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
