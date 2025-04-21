package org.arzzcorp.barisystem.services;

import org.json.JSONObject;
import java.util.concurrent.CompletableFuture;

public class AuthService {

    public static CompletableFuture<Boolean> login(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                JSONObject requestBody = new JSONObject();
                requestBody.put("username", username);
                requestBody.put("password", password);

                String response = ApiService.makePostRequestSync(
                        "http://bariparques.local:3000/api/users/login",
                        requestBody.toString()
                );

                JSONObject jsonResponse = new JSONObject(response);

                System.out.println( "Respuesta de autenticación: " + jsonResponse.toString());

                return jsonResponse.has("token");

            } catch (Exception e) {
                if (e.getMessage() == null) {
                    System.out.println("La API no está disponible");
                }
                System.err.println("Error en autenticación: " + e.getMessage());
                return false;
            }
        });
    }
}