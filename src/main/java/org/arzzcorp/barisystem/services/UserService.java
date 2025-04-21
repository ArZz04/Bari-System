package org.arzzcorp.barisystem.services;

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

    // Método para manejar la información del usuario
    public static void handleUserInfo(JSONObject userInfo) {

        // Almacenar la información del usuario en el servicio UserService
        setUserInfo(userInfo);

        // Solo creamos la instancia de UserBox una vez
        if (userBox == null) {
            userBox = new UserBox();  // Solo se crea una vez, al principio
        }

        // Acceder a la clave 'user' que contiene la información del usuario
        JSONObject user = userInfo.getJSONObject("user");

        System.out.println("Nombre: " + user.getString("name"));
        System.out.println("Rol: " + user.getString("role"));
        System.out.println("Imagen: " + user.getString("image"));

        // Usamos la instancia existente de UserBox para actualizar la información
        userBox.setUserInfo(user.getString("name"), user.getString("role"), user.getString("image"));
    }

}
