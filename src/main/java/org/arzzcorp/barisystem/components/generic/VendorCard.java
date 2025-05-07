package org.arzzcorp.barisystem.components.generic;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class VendorCard extends HBox {

    public VendorCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/vendor-card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for VendorCard", e);
        }
    }

    private void initialize() {
    }
}
