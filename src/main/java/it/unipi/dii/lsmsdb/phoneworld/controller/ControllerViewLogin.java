package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.model.GenericUser;
import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Component
public class ControllerViewLogin {

    @Autowired
    private UserMongo userMongo;

    @FXML
    public TextField textFieldUsEm;
    public PasswordField textFieldPassword;
    public Button buttonCancel;
    public Button buttonLogin;
    public Button buttonRegister;

    private final StageManager stageManager;
    private final static Logger logger = LoggerFactory.getLogger(PhoneMongo.class);

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
            App.getInstance().showInfoMessage("ERROR", "You have to insert your " +
                    "username or email and your password");
            return;
        } else if (username.isEmpty()) {
            App.getInstance().showInfoMessage("ERROR", "You have to insert your " +
                    "username or email");
            return;
        } else if (password.isEmpty()) {
            App.getInstance().showInfoMessage("ERROR", "You have to insert your " +
                    "password");
            return;
        }
        try {
            String salt = getSalt();
            String hashedPassword = getHashedPassword(password, salt);
            List<GenericUser> users = userMongo.findByUsername(username);
            if (users.isEmpty() || !users.get(0).getSha().equals(hashedPassword)) {
                App.getInstance().showInfoMessage("ERROR", "Wrong username or password");
                return;
            }
            System.out.println("You're in!!");
        } catch (Exception e) {
            logger.error("Exception occurred: " + e.getLocalizedMessage());
        }
    }

    public void onClickSignIn(ActionEvent actionEvent) {
    }


    private String getHashedPassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Arrays.toString(salt);
    }

}
