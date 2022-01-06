package it.unipi.dii.lsmsdb.phoneworld.view;

import java.util.ResourceBundle;

public enum FxmlView {

    UNUSER {
        @Override
        public String getTitle() {
            return "PhoneWorld";
        }

        @Override
        public String getFxmlFile() {
            return "/viewUnregisteredUser.fxml";
        }
    }, AUTORIZATION {
        @Override
        public String getTitle() {
            return "LOGIN/SIGN IN";
        }

        @Override
        public String getFxmlFile() {
            return "/viewAutorization.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBoundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}


