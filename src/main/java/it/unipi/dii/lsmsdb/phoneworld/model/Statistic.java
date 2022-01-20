package it.unipi.dii.lsmsdb.phoneworld.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Statistic {

    private SimpleStringProperty name;
    private SimpleIntegerProperty param2;
    private SimpleDoubleProperty param3;

    public Statistic(String name, int param2, Double param3) {
        this.name = new SimpleStringProperty(name);
        this.param2 = new SimpleIntegerProperty(param2);
        this.param3 = new SimpleDoubleProperty(param3);
    }

    public String getName() {
        return name.get();
    }

    public int getParam2() {
        return param2.get();
    }

    public double getParam3() {
        return param3.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setParam2(int param2) {
        this.param2 = new SimpleIntegerProperty(param2);
    }

    public void setParam3(double param3) {
        this.param3 = new SimpleDoubleProperty(param3);
    }
}
