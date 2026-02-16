package com.insurance.ui_controller;

import com.insurance.model.InsuranceType;
import com.insurance.navigation.NavigationManager;
import com.insurance.service.ServiceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

/**
 * Controller for the sell insurance view.
 * Handles policy creation input and navigation back to home.
 */
public class SellInsuranceController {

    @FXML private Label typeLabel;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea remarksArea;

    private InsuranceType insuranceType;

    /**
     * Initializes default values after FXML loading.
     */
    @FXML
    public void initialize() {
        datePicker.setValue(LocalDate.now());
    }

    /**
     * Handles the Save button click.
     * Validates input, creates the policy, and clears the form.
     */
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


    /**
     * Handles the Back button click.
     * Navigates to the home view.
     */
    @FXML
    private void onBackClicked() {
        NavigationManager.navigateTo("home.fxml");
    }

    /**
     * Sets the insurance type and updates the view title.
     *
     * @param type the insurance type to preselect
     */
    public void setInsuranceType(InsuranceType type) {
        this.insuranceType = type;
        typeLabel.setText("Create New " + type.getDisplayName() + " Policy");
    }

    /**
     * Validates user input before creating a policy.
     *
     * @param firstName the customer first name
     * @param lastName the customer last name
     * @param selectedDate the policy date
     * @return true if input is valid; false otherwise
     */
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

    /**
     * Shows an error alert with the provided message.
     *
     * @param message the error message to display
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Please fix the following:");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an information alert with the provided message.
     *
     * @param message the information message to display
     */
    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Clears all input fields and resets the date to today.
     */
    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        remarksArea.clear();
        datePicker.setValue(LocalDate.now());
    }
}