package it.unipi.dii.lsmsdb.phoneworld.view;

import it.unipi.dii.lsmsdb.phoneworld.repository.PhoneMongo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ControllerViewUnUser implements Initializable {

    @Autowired
    private PhoneMongo phoneMongo;

    @FXML
    public Button buttonPhones;
    public Button buttonLogin;
    public Button buttonUsers;
    public Button buttonSearch;
    public Label labelPhone1;
    public ImageView imagePhone1;
    public ImageView imagePhone2;
    public ImageView imagePhone3;
    public ImageView imagePhone4;
    public ImageView imagePhone5;
    public ImageView imagePhone6;
    public ImageView imagePhone7;
    public ImageView imagePhone8;
    public ImageView imagePhone9;
    public ImageView imagePhone10;
    public ImageView imagePhone11;
    public ImageView imagePhone12;
    public ImageView imagePhone13;
    public ImageView imagePhone14;
    public ImageView imagePhone15;
    public ImageView imagePhone16;
    public ImageView imagePhone17;
    public ImageView imagePhone18;

    public void actionClickOnPhones() {
        labelPhone1.setText("Ciao");
    }

    public void actionSearch() {

    }

    public void actionClickOnUsers() {

    }

    public void actionLogin() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      /*  List<Phone> phones = new ArrayList<>();
        phones = phoneMongo.findRecentPhones();
        Phone phone1 = phones.get(0);
        System.out.println(phone1);
        System.out.println(phones.size());
        Image image = new Image(phone1.getPicture());
        imagePhone2.setImage(image);*/
    }
}
