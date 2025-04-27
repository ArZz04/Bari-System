package org.arzzcorp.barisystem.services;

import javafx.application.Platform;
import org.arzzcorp.barisystem.utils.ProductLoadCallback;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static void loadProductsFromAPI(ProductLoadCallback callback) {
        APIService.getProductsList().thenAccept(response -> {
            if (response != null) {
                setProductsList(response);

                // Asegurarse de que el callback se ejecute en el hilo de la aplicación JavaFX
                Platform.runLater(() -> {
                    callback.onProductsLoaded(response);  // Llamar al callback cuando los productos se carguen
                });
            } else {
                System.out.println("No se pudieron obtener los productos desde la API");

                // Llamar al callback en el hilo de la aplicación JavaFX
                Platform.runLater(() -> {
                    callback.onProductsLoaded(null);  // Llamar al callback con null si no se pudieron obtener productos
                });
            }
        }).exceptionally(e -> {
            System.out.println("Error al obtener la lista de productos: " + e.getMessage());

            // Llamar al callback en el hilo de la aplicación JavaFX
            Platform.runLater(() -> {
                callback.onProductsLoaded(null);  // Llamar al callback con null si ocurre un error
            });

            return null;
        });
    }


    public static void clearProductsList() {
        System.out.println("Limpiando los productos almacenados");
        productsListJSON = null;
    }
}
