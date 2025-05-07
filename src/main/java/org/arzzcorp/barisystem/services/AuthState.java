package org.arzzcorp.barisystem.services;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class AuthState {
    private static final AuthState instance = new AuthState();

    private final BooleanProperty loggedIn = new SimpleBooleanProperty(false);
    private final BooleanProperty userDataLoaded = new SimpleBooleanProperty(false); // Nueva propiedad

    private AuthState() {}

    public static AuthState getInstance() {
        return instance;
    }

    public BooleanProperty loggedInProperty() {
        return loggedIn;
    }

    public BooleanProperty userDataLoadedProperty() {
        return userDataLoaded; // Getter para la propiedad de datos cargados
    }

    public boolean isLoggedIn() {
        return loggedIn.get();
    }

    public void login() {
        loggedIn.set(true);
    }

    public void loadUserData() {
        userDataLoaded.set(true);
    }

    public void logout() {
        loggedIn.set(false);
        userDataLoaded.set(false); // Reseteamos los datos cuando el usuario cierre sesión
        SessionManager.cancelSessionTimeout();
    }
}