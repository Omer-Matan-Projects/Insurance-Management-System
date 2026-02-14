package com.insurance.ui_controller;

import com.insurance.model.InsuranceType;
import com.insurance.navigation.NavigationManager;
import com.insurance.service.ServiceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class SellInsuranceController {

    @FXML private Label typeLabel;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea remarksArea;

    private InsuranceType insuranceType;

    @FXML
    public void initialize() {
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void onSaveClicked() {

        String firstName = firstNameField.getText() == null ? "" : firstNameField.getText().trim();
        String lastName  = lastNameField.getText() == null ? "" : lastNameField.getText().trim();
        LocalDate selectedDate = datePicker.getValue();
        String remarks = remarksArea.getText() == null ? "" : remarksArea.getText().trim();

        if (!validateInput(firstName, lastName, selectedDate)) {
            return;
        }

        ServiceLocator.getSalesService().createPolicy(
                firstName,
                lastName,
                selectedDate,
                remarks,
                insuranceType
        );

        showInformationAlert("Policy created successfully!");
        clearForm();
    }


    @FXML
    private void onBackClicked() {
        NavigationManager.navigateTo("home.fxml");
    }

    public void setInsuranceType(InsuranceType type) {
        this.insuranceType = type;
        typeLabel.setText("Create New " + type.getDisplayName() + " Policy");
    }

    private boolean validateInput(String firstName,
                                  String lastName,
                                  LocalDate selectedDate) {

        StringBuilder errors = new StringBuilder();

        if (firstName.isEmpty()) {
            errors.append("First name cannot be empty.\n");
        }

        if (lastName.isEmpty()) {
            errors.append("Last name cannot be empty.\n");
        }

        if (selectedDate == null) {
            errors.append("Please select a date.\n");
        } else if (selectedDate.isBefore(LocalDate.now())) {
            errors.append("Date cannot be in the past.\n");
        }

        if (insuranceType == null) {
            errors.append("Insurance type is not selected.\n");
        }

        if (errors.length() > 0) {
            showErrorAlert(errors.toString());
            return false;
        }

        return true;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Please fix the following:");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        remarksArea.clear();
        datePicker.setValue(LocalDate.now());
    }
}