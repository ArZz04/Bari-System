package org.arzzcorp.barisystem.components;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.components.generic.ProductRow;
import org.arzzcorp.barisystem.hooks.BranchState;
import org.arzzcorp.barisystem.services.APIService;
import org.arzzcorp.barisystem.services.AuthState;
import org.arzzcorp.barisystem.services.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class ProductsList extends VBox {

    public ProductsList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/products-list.fxml"));
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
        // Mostrar la sucursal seleccionada (debug)
        System.out.println("Selected branch: " + BranchState.getInstance().getCurrentBranch());

        // Separación entre filas de productos
        this.setSpacing(5);

        // Obtener productos desde el servicio
        ProductService.loadProductsFromAPI(productsList -> {
            if (productsList != null && productsList.length() > 0) {
                // Dividir en parter la lista de productos
                Integer listQuantity = (int) Math.ceil(productsList.length() / 16.0);

                System.out.println("Cantidad de productos: " + productsList.length());
                System.out.println("Cantidad de productos por rebanada: " + listQuantity);

                // El resultado de listQuantity es un número entero que representa la cantidad de productos que se manejarán por rebanada
                // Usaremos de referenica el quicksort para el orden de renderizado que se usará en el for o fors

                for (int i = 0; i < productsList.length(); i++) {
                    JSONObject product = productsList.getJSONObject(i);

                    ProductRow newProductRow = new ProductRow();
                    newProductRow.setProductData(
                            product.optString("CODIGO", ""),
                            product.optString("DESCRIPCION", ""),
                            product.optString("TIPO", ""),
                            product.optString("FAMILIA", ""),
                            product.optDouble("COSTO", 0.0),
                            product.optDouble("P01", 0.0) // Ajusta este campo si es necesario
                    );

                    this.getChildren().add(newProductRow);
                }
            } else {
                System.out.println("No hay productos para mostrar");
            }
        });
    }

}
