package org.arzzcorp.barisystem.services;

import javafx.application.Platform;
import org.arzzcorp.barisystem.utils.ProductLoadCallback;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class ProductService {

    private static JSONArray productsListJSON;

    // Establecer la lista de productos
    public static void setProductsList(JSONArray info) {
        productsListJSON = info;
    }

    // Obtener la lista de productos
    public static JSONArray getProductsList() {
        return productsListJSON;
    }

    public static void loadProductsFromAPI(String branch, ProductLoadCallback callback) {
        CompletableFuture<JSONArray> future;

        if ("parques".equalsIgnoreCase(branch)) {
            future = APIService.getProductsParquesList();
        } else {
            future = APIService.getProductsBothList(); // Asumo que este método existe
        }

        future.thenAccept(response -> {
            if (response != null) {
                setProductsList(response);

                Platform.runLater(() -> {
                    callback.onProductsLoaded(response);
                });
            } else {
                System.out.println("No se pudieron obtener los productos desde la API");
                Platform.runLater(() -> callback.onProductsLoaded(null));
            }
        }).exceptionally(e -> {
            System.out.println("Error al obtener la lista de productos: " + e.getMessage());
            Platform.runLater(() -> callback.onProductsLoaded(null));
            return null;
        });
    }

    public static CompletableFuture<Boolean> sendProductsToAPI(JSONArray products, String branch) {
        return APIService.updateProducts(products, branch);
    }

    public static void clearProductsList() {
        System.out.println("Limpiando los productos almacenados");
        productsListJSON = null;
    }
}
