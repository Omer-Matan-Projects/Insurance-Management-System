package com.insurance;

import com.insurance.service.ServiceLocator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.insurance.navigation.NavigationManager;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Initialize all backend services
        ServiceLocator.initialize();

        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxml/mainLayout.fxml"));

        BorderPane root = loader.load();

        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(
                getClass().getResource("/css/styles.css").toExternalForm()
        );

        NavigationManager.init(root);

        stage.setTitle("Insurance Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        NavigationManager.navigateTo("home.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}