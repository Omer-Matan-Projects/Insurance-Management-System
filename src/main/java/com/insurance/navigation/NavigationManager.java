package com.insurance.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import com.insurance.model.InsuranceType;
import com.insurance.ui_controller.SellInsuranceController;

/**
 * Central navigation utility for switching views in the main layout.
 */
public class NavigationManager {

    private static BorderPane mainLayout;

    /**
     * Initializes the navigation manager with the main layout container.
     *
     * @param root the root BorderPane that hosts the center view
     */
    public static void init(BorderPane root) {
        mainLayout = root;
    }

    /**
     * Navigates to the given FXML view and sets it in the center of the layout.
     *
     * @param fxmlFile the FXML file name located under /fxml/
     */
    public static void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource("/fxml/" + fxmlFile)
            );

            Node view = loader.load();
            mainLayout.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the sell insurance view and preconfigures the insurance type.
     *
     * @param type the insurance type to preselect
     */
    public static void navigateToSell(InsuranceType type) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    NavigationManager.class.getResource("/fxml/sellInsurance.fxml")
            );

            Node view = loader.load();
            SellInsuranceController controller = loader.getController();
            controller.setInsuranceType(type);
            mainLayout.setCenter(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}