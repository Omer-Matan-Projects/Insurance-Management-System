package com.insurance.ui_controller;

import javafx.fxml.FXML;
import com.insurance.navigation.NavigationManager;
import com.insurance.model.InsuranceType;

/**
 * Controller for the home view.
 * Handles navigation to policy creation and management screens.
 */
public class HomeController {

    @FXML
    private void onCarClicked() {
        NavigationManager.navigateToSell(InsuranceType.CAR);
    }

    @FXML
    private void onApartmentClicked() {
        NavigationManager.navigateToSell(InsuranceType.APARTMENT);
    }

    @FXML
    private void onLifeClicked() {
        NavigationManager.navigateToSell(InsuranceType.LIFE);
    }

    @FXML
    private void onHealthClicked() {
        NavigationManager.navigateToSell(InsuranceType.HEALTH);
    }

    @FXML
    private void onViewClicked() {
        NavigationManager.navigateTo("policyManagement.fxml");
    }
}