package org.arzzcorp.barisystem.components.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.components.ProductsList;
import org.arzzcorp.barisystem.components.generic.OrderDropdown;
import org.json.JSONObject;

import java.io.IOException;

public class Prices extends VBox {

    @FXML
    private OrderDropdown orderDropdown;
    @FXML private ProductsList productsList;

    // Lista observable para productos seleccionados
    private ObservableList<JSONObject> selectedProducts = FXCollections.observableArrayList();
    private ObservableList<JSONObject> addedProducts = FXCollections.observableArrayList();


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

    // Initialize method to set up the view
    @FXML
    private void initialize() {
        // Configura el listener del dropdown en Prices
        orderDropdown.setOnOrderSelected(selectedOrder -> {
            productsList.sortBy(selectedOrder); // Delega el ordenamiento
        });

        productsList.setOnProductAdded(product -> {
            addedProducts.add(product);
            System.out.println("Producto agregado: " + product);
        });

        // Pasa la lista de seleccionados a ProductsList
        productsList.setSelectedProductsList(selectedProducts);

    }

    // Método para acceder a la lista desde fuera
    public ObservableList<JSONObject> getSelectedProducts() {
        return selectedProducts;
    }

    public ObservableList<JSONObject> getAddedProducts() {
        return addedProducts;
    }

}