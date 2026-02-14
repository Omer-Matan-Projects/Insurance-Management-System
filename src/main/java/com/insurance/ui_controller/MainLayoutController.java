package com.insurance.ui_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.insurance.service.ServiceLocator;

public class MainLayoutController {

    @FXML private Label companyLabel;
    @FXML private Label versionLabel;
    @FXML private Label developerLabel;

    @FXML
    public void initialize() {
        companyLabel.setText(ServiceLocator.getConfigService().getCompanyName());
        versionLabel.setText("Version " + ServiceLocator.getConfigService().getAppVersion());
        developerLabel.setText(ServiceLocator.getConfigService().getDeveloperNames());
    }
}