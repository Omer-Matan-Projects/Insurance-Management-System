package com.insurance.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import com.insurance.model.InsuranceType;
import com.insurance.ui_controller.SellInsuranceController;

public class NavigationManager {

    private static BorderPane mainLayout;

    public static void init(BorderPane root) {
        mainLayout = root;
    }

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