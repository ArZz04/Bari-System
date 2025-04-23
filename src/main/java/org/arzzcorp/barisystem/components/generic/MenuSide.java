package org.arzzcorp.barisystem.components.generic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.services.AuthService;
import org.arzzcorp.barisystem.services.AuthState;
import org.arzzcorp.barisystem.services.UserService;
import org.json.JSONObject;

import java.io.IOException;

public class MenuSide extends VBox {

    @FXML private Button mainBtn;
    @FXML private Button pricesBtn;
    @FXML private Button productsBtn;
    @FXML private Button ordersBtn;

    public MenuSide() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/menu-side.fxml"));
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
        // Listener de login
        AuthState.getInstance().loggedInProperty().addListener((obs, wasLoggedIn, isNowLoggedIn) -> {
            if (isNowLoggedIn) {
                AuthService.getUserInfo().thenAccept(userInfo -> {
                    if (userInfo != null) {
                        String role = getRoleFromUserInfo();
                        showButtons(role);
                    } else {
                        hideButtons();
                    }
                });
            } else {
                hideButtons();
            }
        });

        // Si ya está logueado, lo mismo:
        if (AuthState.getInstance().isLoggedIn()) {
            AuthService.getUserInfo().thenAccept(userInfo -> {
                if (userInfo != null) {
                    String role = getRoleFromUserInfo();
                    showButtons(role);
                } else {
                    hideButtons();
                }
            });
        } else {
            hideButtons();
        }
    }

    // Obtener el rol desde la información del usuario
    private String getRoleFromUserInfo() {
        JSONObject userInfo = UserService.getUserInfo();

        if (userInfo != null && userInfo.has("user")) {
            JSONObject user = userInfo.getJSONObject("user");
            String role = user.optString("role", "GENERAL");
            return role;
        }
        return "GENERAL";
    }

    // Ocultar botones
    public void hideButtons() {
        setButtonVisibility(mainBtn, false);
        setButtonVisibility(pricesBtn, false);
        setButtonVisibility(productsBtn, false);
        setButtonVisibility(ordersBtn, false);
    }

    private void setButtonVisibility(Button button, boolean visible) {
        button.setVisible(visible);
        button.setManaged(visible);
    }

    // Mostrar botones acorde a su rol
    public void showButtons(String role) {
        switch (role) {
            case "SISTEMAS":
                setButtonVisibility(mainBtn, true);
                setButtonVisibility(pricesBtn, true);
                setButtonVisibility(productsBtn, true);
                setButtonVisibility(ordersBtn, true);
                break;
            case "COMPRAS":
                setButtonVisibility(mainBtn, true);
                setButtonVisibility(pricesBtn, false);
                setButtonVisibility(productsBtn, true);
                setButtonVisibility(ordersBtn, true);
                break;
            case "GENERAL":
                setButtonVisibility(mainBtn, true);
                setButtonVisibility(pricesBtn, false);
                setButtonVisibility(productsBtn, true);
                setButtonVisibility(ordersBtn, false);
                break;
            default:
                hideButtons();
        }
    }


}
