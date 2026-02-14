package com.insurance.ui_controller;

import javafx.fxml.FXML;
import com.insurance.navigation.NavigationManager;
import com.insurance.model.InsuranceType;

public class HomeController {

    @FXML
    public void initialize() {
        // Initialization logic if needed
    }

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
    private void onSubmitClaimClicked() {
        // TODO: implement navigation to submit claim view
    }

    @FXML
    private void onViewClicked() {
        // TODO: implement navigation to view policies/claims view
    }
}