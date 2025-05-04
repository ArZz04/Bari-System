package org.arzzcorp.barisystem.services;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.concurrent.CompletableFuture;

public class APIService {

    public static CompletableFuture<Boolean> login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", username);
                requestBody.put("password", password);

                String response = ApiFunctions.makePostRequestSync(
                        "http://bariparques.local:4000/api/users/login",
                        requestBody.toString()
                );

                JSONObject jsonResponse = new JSONObject(response);

                // Gestion del token usando la clase TokenService
                if (jsonResponse.has("token")) {
                    TokenService.setAuthToken(jsonResponse.getString("token"));
                    System.out.println("Token guardado");
                    return true;
                }
                return false;

            } catch (Exception e) {
                System.err.println("Error en autenticación: " + e.getMessage());
                return false;
            }
        });
    }

    // Funcion para hacer el get para obtener la informacion del usuario
    public static CompletableFuture<JSONObject> getUserInfo() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Obtener el token de autenticación
                String token = TokenService.getAuthToken();
                if (token == null) {
                    throw new Exception("Token no encontrado");
                }

                // Realizar la solicitud GET a la API para obtener la información del usuario
                String response = ApiFunctions.makeGetRequestSync(
                        "http://bariparques.local:4000/api/users/profile/info",
                        token
                );

                // Convertir la respuesta a JSONObject
                JSONObject userInfo = new JSONObject(response);

                // Pasar la información del usuario al UserService para manejarla
                UserService.handleUserInfo(userInfo);

                return userInfo;

            } catch (Exception e) {
                System.err.println("Error al obtener información del usuario: " + e.getMessage());
                return null;
            }

        });
    }

    // Funcion para obtener la lista de productos de la API
    public static CompletableFuture<JSONArray> getProductsParquesList() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Obtener el token de autenticación
                // String token = TokenService.getAuthToken();
                // if (token == null) {
                //     throw new Exception("Token no encontrado");
                // }

                // Realizar la solicitud GET a la API para obtener la lista de productos
                String response = ApiFunctions.makeGetRequestSync(
                        "http://bariparques.local:4000/api/parques/products/"
                );

                // Convertir la respuesta a JSONObject
                JSONArray productsList = new JSONArray(response);

                // Pasar la lista de productos al ProductService para manejarla
                ProductService.setProductsList(productsList);

                return productsList;

            } catch (Exception e) {
                System.err.println("Error al obtener la lista de productos: " + e.getMessage());
                return null;
            }
        });
    }

    // Funcion para obtener la lista de productos de la API
    public static CompletableFuture<JSONArray> getProductsBothList() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String response = ApiFunctions.makeGetRequestSync(
                        "http://bariparques.local:4000/api/both/products/"
                );

                JSONObject jsonResponse = new JSONObject(response);
                JSONObject productsObject = jsonResponse.getJSONObject("products");
                JSONArray productsList = productsObject.getJSONArray("both");

                ProductService.setProductsList(productsList);

                return productsList;

            } catch (Exception e) {
                System.err.println("Error al obtener la lista de productos: " + e.getMessage());
                return null;
            }
        });
    }


    public static void logout() {
        TokenService.clearAuthToken();
        System.out.println("Token eliminado");
    }
}