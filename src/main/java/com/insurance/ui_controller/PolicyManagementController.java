package com.insurance.ui_controller;

import com.insurance.model.Claim;
import com.insurance.model.Policy;
import com.insurance.navigation.NavigationManager;
import com.insurance.service.PolicyObserver;
import com.insurance.service.ServiceLocator;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

/**
 * Controller for the Policy Management view.
 * Displays all policies in a table with actions for claim submission and deletion.
 *
 * Design Pattern: Observer - implements PolicyObserver to react to policy changes
 */
public class PolicyManagementController implements PolicyObserver {

    @FXML private TableView<Policy> policyTable;
    @FXML private TableColumn<Policy, String> idColumn;
    @FXML private TableColumn<Policy, String> firstNameColumn;
    @FXML private TableColumn<Policy, String> lastNameColumn;
    @FXML private TableColumn<Policy, String> typeColumn;
    @FXML private TableColumn<Policy, String> dateColumn;
    @FXML private TableColumn<Policy, Void> actionsColumn;

    private final ObservableList<Policy> policyData = FXCollections.observableArrayList();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Initializes the controller.
     * Sets up the table columns, action buttons, and loads policies from the data service.
     * Also registers as an observer for policy changes.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        setupActionsColumn();
        setupColumnWidths();
        loadPolicies();

        // Register as observer for policy changes
        ServiceLocator.getSalesService().addPolicyObserver(this);
    }

    /**
     * Configures the table columns to display policy properties.
     */
    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId()));

        firstNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName()));

        lastNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastName()));

        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getType().getDisplayName()));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDate().format(DATE_FORMAT)));
    }

    /**
     * Configures the Actions column to display buttons for each policy.
     */
    private void setupActionsColumn() {
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button remarksButton = new Button("View Remarks");
            private final Button viewClaimsButton = new Button("View Prev. Claims");
            private final Button claimButton = new Button("Submit New Claim");
            private final Button deleteButton = new Button("Delete");
            private final HBox buttonBox = new HBox(5, remarksButton, viewClaimsButton, claimButton, deleteButton);

            {
                buttonBox.setAlignment(Pos.CENTER);

                // Apply CSS classes to buttons
                remarksButton.getStyleClass().addAll("table-button", "table-button-remarks");
                viewClaimsButton.getStyleClass().addAll("table-button", "table-button-view-claims");
                claimButton.getStyleClass().addAll("table-button", "table-button-claim");
                deleteButton.getStyleClass().addAll("table-button", "table-button-delete");

                remarksButton.setOnAction(event -> {
                    Policy policy = getTableView().getItems().get(getIndex());
                    onRemarksClicked(policy);
                });

                viewClaimsButton.setOnAction(event -> {
                    Policy policy = getTableView().getItems().get(getIndex());
                    onViewClaimsClicked(policy);
                });

                claimButton.setOnAction(event -> {
                    Policy policy = getTableView().getItems().get(getIndex());
                    onSubmitClaimClicked(policy);
                });

                deleteButton.setOnAction(event -> {
                    Policy policy = getTableView().getItems().get(getIndex());
                    onDeleteClicked(policy);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(buttonBox);
                }
            }
        });
    }

    /**
     * Configures the column widths dynamically.
     * All columns except Actions have equal width, Actions column is wider.
     */
    private void setupColumnWidths() {
        DoubleBinding tableWidth = policyTable.widthProperty().subtract(4); // Account for borders
        DoubleBinding dataWidth = tableWidth.multiply(0.12); // 12% for each data column

        idColumn.prefWidthProperty().bind(dataWidth);
        firstNameColumn.prefWidthProperty().bind(dataWidth);
        lastNameColumn.prefWidthProperty().bind(dataWidth);
        typeColumn.prefWidthProperty().bind(dataWidth);
        dateColumn.prefWidthProperty().bind(dataWidth);
        actionsColumn.prefWidthProperty().bind(tableWidth.subtract(dataWidth.multiply(5))); // Remaining width for actions
    }

    /**
     * Loads all policies from the data service into the table.
     */
    private void loadPolicies() {
        policyData.clear();
        policyData.addAll(ServiceLocator.getDataService().getAllPolicies());
        policyTable.setItems(policyData);
    }

    /**
     * Handles the Remarks button click.
     * Shows a dialog displaying the policy remarks.
     *
     * @param policy the policy to show remarks for
     */
    private void onRemarksClicked(Policy policy) {
        String remarks = policy.getRemarks();
        if (remarks == null || remarks.trim().isEmpty()) {
            remarks = "No remarks available for this policy.";
        }

        Alert remarksDialog = new Alert(Alert.AlertType.INFORMATION);
        remarksDialog.setTitle("Policy Remarks");
        remarksDialog.setHeaderText("Remarks for policy: " + policy.getId());
        remarksDialog.setContentText(remarks);
        remarksDialog.showAndWait();
    }

    /**
     * Handles the View Claims button click.
     * Shows a dialog displaying all claims for the selected policy.
     *
     * @param policy the policy to view claims for
     */
    private void onViewClaimsClicked(Policy policy) {
        java.util.List<Claim> claims = ServiceLocator.getClaimService().getClaimsByPolicy(policy.getId());

        Alert claimsDialog = new Alert(Alert.AlertType.INFORMATION);
        claimsDialog.setTitle("Policy Claims");
        claimsDialog.setHeaderText("Claims for policy: " + policy.getId());

        if (claims == null || claims.isEmpty()) {
            claimsDialog.setContentText("No claims found for this policy.");
        } else {
            StringBuilder claimsText = new StringBuilder();
            for (Claim claim : claims) {
                claimsText.append("Claim ID: ").append(claim.getId()).append("\n");
                claimsText.append("Date: ").append(claim.getClaimDate().format(DATE_FORMAT)).append("\n");
                claimsText.append("Description: ").append(claim.getDescription()).append("\n");
                claimsText.append("--------------------------------------\n");
            }
            claimsDialog.setContentText(claimsText.toString());
        }

        claimsDialog.showAndWait();
    }

    /**
     * Handles the Submit Claim button click.
     * Opens a dialog to submit a claim for the selected policy.
     *
     * @param policy the policy to submit a claim for
     */
    private void onSubmitClaimClicked(Policy policy) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Submit Claim");
        dialog.setHeaderText("Submit claim for policy: " + policy.getId());
        dialog.setContentText("Claim description:");

        dialog.showAndWait().ifPresent(description -> {
            if (!description.trim().isEmpty()) {
                try {
                    ServiceLocator.getClaimService().submitClaim(
                            policy.getId(),
                            description.trim(),
                            java.time.LocalDate.now()
                    );
                    showInformationAlert("Claim submitted successfully!");
                } catch (Exception e) {
                    showErrorAlert("Failed to submit claim: " + e.getMessage());
                }
            } else {
                showErrorAlert("Claim description cannot be empty.");
            }
        });
    }

    /**
     * Handles the Delete button click.
     * Shows a confirmation dialog before deleting the policy.
     *
     * @param policy the policy to delete
     */
    private void onDeleteClicked(Policy policy) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Deletion");
        confirmDialog.setHeaderText("Delete Policy");
        confirmDialog.setContentText("Are you sure you want to delete policy " + policy.getId() + "?\n" +
                "Customer: " + policy.getFirstName() + " " + policy.getLastName());

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = ServiceLocator.getSalesService().deletePolicy(policy.getId());
                if (deleted) {
                    showInformationAlert("Policy deleted successfully!");
                } else {
                    showErrorAlert("Failed to delete policy.");
                }
            }
        });
    }

    /**
     * Navigates back to the home view.
     */
    @FXML
    private void onBackClicked() {
        // Unregister observer before leaving
        ServiceLocator.getSalesService().removePolicyObserver(this);
        NavigationManager.navigateTo("home.fxml");
    }

    /**
     * Observer callback when a new policy is created.
     * Adds the new policy to the table.
     *
     * @param policy the newly created policy
     */
    @Override
    public void onPolicyCreated(Policy policy) {
        // Update table on JavaFX Application Thread
        Platform.runLater(() -> policyData.add(policy));
    }

    /**
    * Observer callback when a policy is deleted.
    * Removes the deleted policy from the table.
    *
    * @param policyId the ID of the deleted policy
    */
    @Override
    public void onPolicyDeleted(String policyId) {
        // Update table on JavaFX Application Thread
        Platform.runLater(() ->
                policyData.removeIf(p -> p.getId().equals(policyId))
        );
    }

    /**
     * Utility method to show an error alert with the given message.
     *
     * @param message the error message to display
     */
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Utility method to show an information alert with the given message.
     *
     * @param message the information message to display
     */
    private void showInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }
}