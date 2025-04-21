package org.arzzcorp.barisystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SystemApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carga el FXML desde resources
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/org/arzzcorp/barisystem/system-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("BARI SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}