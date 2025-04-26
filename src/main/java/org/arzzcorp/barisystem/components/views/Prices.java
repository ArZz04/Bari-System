package org.arzzcorp.barisystem.components.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Prices extends VBox {

    private boolean initialized = false;

    public Prices() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/views/prices-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    public void initializePrices() {
        if (!initialized) {
            System.out.println("Initializing Prices...");
            // Lógica de inicialización aquí
            initialized = true;
        }
    }

    public boolean isInitialized() {
        return initialized;
    }
}