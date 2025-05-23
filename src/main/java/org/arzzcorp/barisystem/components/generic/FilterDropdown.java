package org.arzzcorp.barisystem.components.generic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import org.arzzcorp.barisystem.components.ProductsList;

import java.io.IOException;

public class FilterDropdown extends HBox {


    public FilterDropdown() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/filter-dropdown.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    @FXML
    private void initialize() {
    }

}