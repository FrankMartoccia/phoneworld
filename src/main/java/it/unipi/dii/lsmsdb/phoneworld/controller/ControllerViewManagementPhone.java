package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.App;
import it.unipi.dii.lsmsdb.phoneworld.Constants;
import it.unipi.dii.lsmsdb.phoneworld.model.Phone;
import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

@Component
public class ControllerViewManagementPhone implements Initializable {

    @FXML private Button buttonCancel;
    @FXML private Button buttonServicePhone;
    @FXML private Spinner<Integer> spinnerReleaseYear;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldBatterySize;
    @FXML private TextField textFieldBatteryType;
    @FXML private TextField textFieldBody;
    @FXML private TextField textFieldBrand;
    @FXML private TextField textFieldCameraPixels;
    @FXML private TextField textFieldChipset;
    @FXML private TextField textFieldDisplayResolution;
    @FXML private TextField textFieldDisplaySize;
    @FXML private TextField textFieldOS;
    @FXML private TextField textFieldPicture;
    @FXML private TextField textFieldRam;
    @FXML private TextField textFieldStorage;
    @FXML private TextField textFieldVideoPixels;

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewManagementPhone(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @FXML
    void onClickCancel(ActionEvent event) {
        stageManager.closeStage(this.buttonCancel);
    }

    @FXML
    void onClickService(ActionEvent event) {
        String name = this.textFieldName.getText();
        String batterySize = this.textFieldBatterySize.getText();
        String batteryType = this.textFieldBatteryType.getText();
        String body = this.textFieldBody.getText();
        String brand = this.textFieldBrand.getText();
        String cameraPixels = this.textFieldCameraPixels.getText();
        String videoPixels = this.textFieldVideoPixels.getText();
        String chipset = this.textFieldChipset.getText();
        String displayResolution = this.textFieldDisplayResolution.getText();
        String displaySize = this.textFieldDisplaySize.getText();
        String os = this.textFieldOS.getText();
        String picture = this.textFieldPicture.getText();
        String ram = this.textFieldRam.getText();
        String storage = this.textFieldStorage.getText();
        int year = this.spinnerReleaseYear.getValue();
        boolean isUpdate = (boolean) App.getInstance().getModelBean().getBean(Constants.IS_UPDATE);
        String sbError = stageManager.generateStringBuilderErrorPhone(name,brand,picture,body,os,storage,displaySize,
                displayResolution,cameraPixels,videoPixels,ram,chipset,batterySize,batteryType,isUpdate);
        if (!sbError.isEmpty()) {
            stageManager.showInfoMessage("ERROR", "You have to insert the following fields: "
                    + stageManager.getErrors(sbError));
            return;
        }
        if (isUpdate == true) {
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerValueFactory<Integer> valueFactoryReleaseYear =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1973, year);
        this.spinnerReleaseYear.setValueFactory(valueFactoryReleaseYear);
        boolean isUpdate = (boolean) App.getInstance().getModelBean().getBean(Constants.IS_UPDATE);
        if (isUpdate == true) {
            Phone phone = (Phone) App.getInstance().getModelBean().getBean(Constants.SELECTED_PHONE);
            this.textFieldName.setText(phone.getName());
            this.textFieldBatterySize.setText(phone.getBatterySize());
            this.textFieldBatteryType.setText(phone.getBatteryType());
            this.textFieldBody.setText(phone.getBody());
            this.textFieldBrand.setText(phone.getBrand());
            this.textFieldCameraPixels.setText(phone.getCameraPixels());
            this.textFieldVideoPixels.setText(phone.getVideoPixels());
            this.textFieldChipset.setText(phone.getChipset());
            this.textFieldDisplayResolution.setText(phone.getDisplayResolution());
            this.textFieldDisplaySize.setText(phone.getDisplaySize());
            this.textFieldOS.setText(phone.getOs());
            this.textFieldPicture.setText(phone.getPicture());
            this.textFieldRam.setText(phone.getRam());
            this.textFieldStorage.setText(phone.getStorage());
            this.spinnerReleaseYear.getValueFactory().setValue(phone.getReleaseYear());
            this.buttonServicePhone.setText("UPDATE PHONE");
        }
    }
}
