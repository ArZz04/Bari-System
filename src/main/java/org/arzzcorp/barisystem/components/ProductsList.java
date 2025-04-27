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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
                final int CHUNK_SIZE = 16;
                final int totalProducts = productsList.length();
                final int totalChunks = (int) Math.ceil(totalProducts / (double) CHUNK_SIZE);

                // Usar un executor para manejar los chunks con retraso
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                for (int chunkIndex = 0; chunkIndex < totalChunks; chunkIndex++) {
                    int start = chunkIndex * CHUNK_SIZE;
                    int end = Math.min(start + CHUNK_SIZE, totalProducts);

                    // Programar cada chunk con un pequeño retraso
                    executor.schedule(() -> {
                        Platform.runLater(() -> { // Actualizar UI en el hilo correcto
                            for (int i = start; i < end; i++) {
                                JSONObject product = productsList.getJSONObject(i);
                                ProductRow row = new ProductRow();
                                row.setProductData(
                                        product.optString("CODIGO", ""),
                                        product.optString("DESCRIPCION", ""),
                                        product.optString("TIPO", ""),
                                        product.optString("FAMILIA", ""),
                                        product.optDouble("COSTO", 0.0),
                                        product.optDouble("P01", 0.0) // Ajusta según tu método
                                );
                                this.getChildren().add(row);
                            }
                        });
                    }, chunkIndex * 5000, TimeUnit.MILLISECONDS); // 100ms entre chunks
                }
            } else {
                System.out.println("No hay productos para mostrar");
            }
        });
    }

}
