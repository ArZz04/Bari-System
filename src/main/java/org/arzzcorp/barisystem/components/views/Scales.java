package org.arzzcorp.barisystem.components.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.components.generic.Branches;

import java.io.IOException;

public class Scales extends VBox {

    @FXML
    private Branches branches;

    public Scales() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/views/scales-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    // Initialize method to set up the view
    private void initialize() {
        // Aquí puedes inicializar cualquier componente o lógica específica para la vista de Scales
    }

    public Branches getBranchesPrices() {
        return branches;
    }

}
