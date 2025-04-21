package org.arzzcorp.barisystem.services;

public class TokenService {

    private static String authToken;

    // Método para guardar el token
    public static void setAuthToken(String token) {
        authToken = token;
    }

    // Método para obtener el token
    public static String getAuthToken() {
        return authToken;
    }

    // Método para borrar el token (cuando el usuario cierra sesión)
    public static void clearAuthToken() {
        authToken = null;
    }
}
