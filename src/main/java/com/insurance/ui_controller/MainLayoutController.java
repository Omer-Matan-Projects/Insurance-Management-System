package com.insurance.ui_controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.insurance.service.ServiceLocator;

/**
 * Controller for the main layout view.
 * Initializes header labels from the configuration service.
 */
public class MainLayoutController {

    @FXML private Label companyLabel;
    @FXML private Label versionLabel;
    @FXML private Label developerLabel;

    /**
     * Initializes the main layout by setting header labels from the configuration service.
     * This method is called automatically by JavaFX after the FXML is loaded.
     */
    @FXML
    public void initialize() {
        companyLabel.setText(ServiceLocator.getConfigService().getCompanyName());
        versionLabel.setText("Version " + ServiceLocator.getConfigService().getAppVersion());
        developerLabel.setText(ServiceLocator.getConfigService().getDeveloperNames());
    }
}