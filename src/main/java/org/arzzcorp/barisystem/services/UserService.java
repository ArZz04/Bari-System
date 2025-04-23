package org.arzzcorp.barisystem.services;

import javafx.fxml.FXML;
import org.arzzcorp.barisystem.components.UserBox;
import org.json.JSONObject;

public class UserService {

    private static UserBox userBox;

    // Variable estática para almacenar la información del usuario
    private static JSONObject userInfo;

    // Método para establecer la información del usuario
    public static void setUserInfo(JSONObject info) {
        userInfo = info;
    }

    // Método para obtener la información del usuario
    public static JSONObject getUserInfo() {
        return userInfo;
    }

    // Método para borrar la información del usuario (cuando el usuario cierra sesión)
    public static void clearUserInfo() {
        System.out.println("Limpiando la información del usuario");
        userInfo = null;
    }

    public static void setUserBox(UserBox userBoxRef) {
        userBox = userBoxRef;
    }

    // Método para manejar la información del usuario
    public static void handleUserInfo(JSONObject userInfo) {;

        // Almacenar la información del usuario en el servicio UserService
        setUserInfo(userInfo);

        // Acceder a la clave 'user' que contiene la información del usuario
        JSONObject user = userInfo.getJSONObject("user");

        if (userBox != null) {
            javafx.application.Platform.runLater(() -> {
                userBox.setUserInfo(user.getString("name"), user.getString("role"), user.getString("image"));
            });
        }
        AuthState.getInstance().loadUserData(); // Cambia el estado a "datos cargados"
    }

}
