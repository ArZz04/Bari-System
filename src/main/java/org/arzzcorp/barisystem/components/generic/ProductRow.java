package org.arzzcorp.barisystem.components.generic;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProductRow extends HBox {

    @FXML
    private Label pluLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label unitLabel;

    @FXML
    private Label familyLabel;

    @FXML
    private TextField purchasePriceField;

    @FXML
    private TextField salePriceField;

    @FXML
    private Button addButton;

    private Runnable onAddButtonClick;

    public ProductRow() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/product-row.fxml"));
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
    }

    public void setProductData(String plu, String name, String unit, String family, double purchasePrice, double salePrice) {
        if (plu.length() > 5) {
            // Si el PLU es más largo que 5 caracteres, lo recortamos y le agregamos "..."
            plu = plu.substring(0, 5) + "...";
        }
        if (name.length() > 20) {
            // Si el nombre es más largo que 20 caracteres, lo recortamos y le agregamos "..."
            name = name.substring(0, 20) + "...";
        }
        if (unit.length() > 10) {
            // Si la unidad es más larga que 5 caracteres, lo recortamos y le agregamos "..."
            unit = unit.substring(0, 5) + "...";
        }
        if (family.length() > 10) {
            // Si la familia es más larga que 5 caracteres, lo recortamos y le agregamos "..."
            family = family.substring(0, 5) + "...";
        }
        pluLabel.setText(plu);
        nameLabel.setText(name);
        unitLabel.setText(unit);
        familyLabel.setText(family);
        purchasePriceField.setText(String.format("%.2f", purchasePrice));
        salePriceField.setText(String.format("%.2f", salePrice));

        // Configura el botón de agregar
        addButton.setOnAction(e -> {
            if (onAddButtonClick != null) {
                onAddButtonClick.run();
            }
        });

    }

    // Método para asignar un listener desde fuera
    public void setOnAddButtonClick(Runnable listener) {
        this.onAddButtonClick = listener;
    }

    public double getEditedPurchasePrice() {
        try {
            return Double.parseDouble(purchasePriceField.getText());
        } catch (NumberFormatException e) {
            return 0.0; // o cualquier valor predeterminado
        }
    }

    public double getEditedPrice() {
        try {
            return Double.parseDouble(salePriceField.getText());
        } catch (NumberFormatException e) {
            return 0.0; // o cualquier valor predeterminado
        }
    }

}
