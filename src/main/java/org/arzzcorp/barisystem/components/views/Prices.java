package org.arzzcorp.barisystem.components.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.components.ProductsList;
import org.arzzcorp.barisystem.components.generic.OrderDropdown;
import org.arzzcorp.barisystem.hooks.BranchState;
import org.json.JSONObject;

import java.io.IOException;

public class Prices extends VBox {

    @FXML
    private OrderDropdown orderDropdown;
    @FXML private ProductsList productsList;

    // Lista observable para productos seleccionados
    private final ObservableList<JSONObject> selectedProducts = FXCollections.observableArrayList();
    private final ObservableList<JSONObject> addedParquesProducts = FXCollections.observableArrayList();
    private final ObservableList<JSONObject> addedBothProducts = FXCollections.observableArrayList();

    public Prices() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/views/prices-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    public ProductsList getProductsList() {
        return productsList;
    }

    // Initialize method to set up the view
    @FXML
    private void initialize() {
        orderDropdown.setOnOrderSelected(productsList::sortBy);

        productsList.setOnProductAdded(product -> {
            String currentBranch = String.valueOf(BranchState.getInstance().getCurrentBranch());

            if ("VALLE_CIMA".equalsIgnoreCase(currentBranch)) {
                addedBothProducts.add(product);
                System.out.println("Producto agregado a VALLE_CIMA: " + product);
            } else {
                addedParquesProducts.add(product);
                System.out.println("Producto agregado a PARQUES: " + product);
            }
        });

        productsList.setSelectedProductsList(selectedProducts);
    }

    public ObservableList<JSONObject> getSelectedProducts() {
        return selectedProducts;
    }

    public ObservableList<JSONObject> getAddedParquesProducts() {
        return addedParquesProducts;
    }

    public ObservableList<JSONObject> getAddedBothProducts() {
        return addedBothProducts;
    }


}