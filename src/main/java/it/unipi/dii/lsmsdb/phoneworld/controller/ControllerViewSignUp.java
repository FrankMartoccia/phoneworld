package it.unipi.dii.lsmsdb.phoneworld.controller;

import it.unipi.dii.lsmsdb.phoneworld.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ControllerViewSignUp {

    private final StageManager stageManager;

    @Autowired @Lazy
    public ControllerViewSignUp(StageManager stageManager) {
        this.stageManager = stageManager;
    }
}
