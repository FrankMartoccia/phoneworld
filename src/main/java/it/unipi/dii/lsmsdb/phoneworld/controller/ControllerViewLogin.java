package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.mongo.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.services.ServiceUser;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
public class ControllerViewLogin {

    @Autowired
    private UserMongo userMongo;

    @Autowired
    private ServiceUser serviceUser;

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField textFieldPassword;

    private final StageManager stageManager;
    private final static Logger logger = LoggerFactory.getLogger(ControllerViewLogin.class);

    @Autowired @Lazy
    public ControllerViewLogin(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    public void onClickCancel(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.UNUSER);
    }

    public void onClickLogin() {
        String password = textFieldPassword.getText();
        String username = textFieldUsername.getText().trim();
        if (password.isEmpty() && username.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You have to insert your " +
                    "username and your password");
            return;
        } else if (username.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You have to insert your " +
                    "username");
            return;
        } else if (password.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You have to insert your " +
                    "password");
            return;
        }
        try {
            Optional<GenericUser> genericUser = userMongo.findByUsername(username);
            if (genericUser.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "There aren't users with this username");
                this.clearFields();
                return;
            }
            String salt = genericUser.get().getSalt();
            String hashedPassword = serviceUser.getHashedPassword(password, salt);
            if (!genericUser.get().getHashedPassword().equals(hashedPassword)) {
                stageManager.showInfoMessage("ERROR", "Wrong username or password");
                this.clearFields();
                return;
            }
            GenericUser user = new User();
            user = genericUser.get();
            App.getInstance().getModelBean().putBean(Constants.CURRENT_USER, user);
            stageManager.switchScene(FxmlView.USER);
        } catch (Exception e) {
            logger.error("Exception occurred: ");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        this.textFieldUsername.clear();
        this.textFieldPassword.clear();
    }

    public void onClickSignUp(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.SIGNUP);
    }

    public void onClikEnterUsername(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) this.onClickLogin();
    }

    public void onClickEnterPassword(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) this.onClickLogin();
    }
}
