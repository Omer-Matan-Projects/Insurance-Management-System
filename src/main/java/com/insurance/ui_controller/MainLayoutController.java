package com.insurance.ui_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.insurance.config.ConfigService;

public class MainLayoutController {

    @FXML private Label companyLabel;
    @FXML private Label versionLabel;
    @FXML private Label developerLabel;

    private final ConfigService config = ConfigService.getInstance();

    @FXML
    public void initialize() {
        companyLabel.setText(config.getCompanyName());
        versionLabel.setText("Version " + config.getAppVersion());
        developerLabel.setText(config.getDeveloperNames());
    }
}