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
            return "/viewAuthorization.fxml";
        }
    }, LOGIN {
        @Override
        public String getTitle() {
            return "LOG IN";
        }

        @Override
        public String getFxmlFile() {
            return "/viewLogin.fxml";
        }
    }, SIGNUP {
        @Override
        public String getTitle() {
            return "SIGN IN";
        }

        @Override
        public String getFxmlFile() {
            return "/viewSignUp.fxml";
        }
    }, USER {
        @Override
        public String getTitle() {
            return "PhoneWorld";
        }

        @Override
        public String getFxmlFile() {
            return "/viewRegisteredUser.fxml";
        }
    }, PROFILE {
        @Override
        public String getTitle() {
            return "YOUR PROFILE";
        }

        @Override
        public String getFxmlFile() {
            return "/viewProfile.fxml";
        }
    }, UPDATE {
        @Override
        public String getTitle() {
            return "UPDATE PROFILE";
        }

        @Override
        public String getFxmlFile() {
            return "/viewUpdate.fxml";
        }
    }, DETAILS_USER {
        @Override
        public String getTitle() {
            return "USER DETAILS";
        }

        @Override
        public String getFxmlFile() {
            return "/viewDetailsUser.fxml";
        }
    }, DETAILS_PHONES {
        @Override
        public String getTitle() {
            return "PHONES DETAILS";
        }

        @Override
        public String getFxmlFile() {
            return "/viewDetailsPhone.fxml";
        }
    };

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBoundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}


