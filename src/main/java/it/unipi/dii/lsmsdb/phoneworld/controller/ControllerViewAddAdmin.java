package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.model.Admin;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ControllerViewAddAdmin {

    @FXML private Button buttonAddAdmin;
    @FXML private Button buttonCancel;
    @FXML private PasswordField textFieldPassword;
    @FXML private PasswordField textFieldRepeatedPassword;
    @FXML private TextField textFieldUsername;

    private final StageManager stageManager;

    private final static Logger logger = LoggerFactory.getLogger(ControllerViewAddAdmin.class);

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private UserMongo userMongo;


    @Autowired @Lazy
    public ControllerViewAddAdmin(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @FXML
    void onClickAddAdmin() {
        String username = this.textFieldUsername.getText();
        String password = this.textFieldPassword.getText();
        String repeatedPassword = this.textFieldRepeatedPassword.getText();
        if (!this.checkError(username, password, repeatedPassword)) {
            return;
        }
        try {
            Admin admin = serviceUser.createAdmin(username, password);
            if (!serviceUser.insertAdmin(admin)) {
                stageManager.showInfoMessage("ERROR", "Error in adding new admin, " +
                        "please try again");
                return;
            }
            stageManager.closeStage(this.buttonAddAdmin);
            stageManager.showInfoMessage("INFO", "New admin added!");
        } catch (Exception e) {
            logger.error("Error in adding new user: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private boolean checkError(String username, String password, String repeatedPassword) {
        if ((username.isEmpty() && password.isEmpty()) || (username.isEmpty() && repeatedPassword.isEmpty())) {
            stageManager.showInfoMessage("ERROR", "You must insert username and password!");
            return false;
        }
        if (username.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You must insert the username!");
            return false;
        }
        if (password.isEmpty() || repeatedPassword.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You must insert the password!");
            return false;
        }
        Optional<GenericUser> users = userMongo.findByUsername(username);
        if (users.isPresent()) {
            stageManager.showInfoMessage("INFO", "Username already taken!");
            return false;
        }
        if (!password.equals(repeatedPassword)) {
            stageManager.showInfoMessage("ERROR", "Password and repeated password must be the same!");
            return false;
        }
        return true;
    }

    @FXML
    void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) this.onClickAddAdmin();
    }

}
