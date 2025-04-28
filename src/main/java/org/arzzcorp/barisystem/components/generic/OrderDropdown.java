package org.arzzcorp.barisystem.components.generic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import org.json.JSONObject;

import java.io.IOException;
import java.util.function.Consumer;

public class OrderDropdown extends HBox {

    @FXML
    private ComboBox<String> orderComboBox;

    private Consumer<String> onOrderSelected;

    public OrderDropdown() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/order-dropdown.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    private void initialize() {


        // Escuchar cuando el usuario seleccione algo
        orderComboBox.setOnAction(event -> {
            String selected = orderComboBox.getSelectionModel().getSelectedItem();

            if (onOrderSelected != null && selected != null) {
                onOrderSelected.accept(selected);
            }
        });
    }

    // Exponer un método para setear el listener desde afuera
    public void setOnOrderSelected(Consumer<String> listener) {
        this.onOrderSelected = listener;
    }

}