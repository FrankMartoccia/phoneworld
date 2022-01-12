package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.model.User;
import it.unipi.dii.lsmsdb.phoneworld.repository.UserMongo;
import it.unipi.dii.lsmsdb.phoneworld.view.FxmlView;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControllerViewLogin {

    @Autowired
    private UserMongo userMongo;

    @FXML
    private TextField textFieldUsEm;
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

    public void onClickLogin(ActionEvent actionEvent) {
        String password = textFieldPassword.getText();
        String username = textFieldUsEm.getText();
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
            List<GenericUser> users = userMongo.findByUsername(username);
            if (users.isEmpty()) {
                stageManager.showInfoMessage("ERROR", "There aren't users with this username");
                this.clearFields();
                return;
            }
            String salt = users.get(0).getSalt();
            String hashedPassword = App.getInstance().getServiceUser().getHashedPassword(password, salt);
            if (!users.get(0).getHashedPassword().equals(hashedPassword)) {
                stageManager.showInfoMessage("ERROR", "Wrong username or password");
                this.clearFields();
                return;
            }
            GenericUser user = new User();
            user = users.get(0);
            App.getInstance().getModelBean().putBean(Constants.CURRENT_USER, user);
            stageManager.switchScene(FxmlView.USER);
        } catch (Exception e) {
            logger.error("Exception occurred: ");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        this.textFieldUsEm.clear();
        this.textFieldPassword.clear();
    }

    public void onClickSignUp(ActionEvent actionEvent) {
        stageManager.switchScene(FxmlView.SIGNUP);
    }

}
