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

    // Logout: borra el token
    public static void logout() {
        TokenService.clearAuthToken();
        System.out.println("Token eliminado");
    }
}