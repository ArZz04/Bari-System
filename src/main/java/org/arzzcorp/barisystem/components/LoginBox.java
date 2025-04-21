package org.arzzcorp.barisystem.components;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.arzzcorp.barisystem.services.ApiService;
import org.arzzcorp.barisystem.services.AuthService;
import org.arzzcorp.barisystem.services.AuthState;

import java.io.IOException;

public class LoginBox extends HBox {

    @FXML private TextField usernameField;

    @FXML private PasswordField passwordField;

    @FXML private TextField passwordTextField;

    @FXML private Button showPasswordButton;

    @FXML private Button loginBtn;
    @FXML private Label errorLabel;


    public LoginBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/login-box.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initialize();
    }

    private void initialize() {
        this.setAlignment(Pos.CENTER);

        this.visibleProperty().bind(AuthState.getInstance().loggedInProperty().not());
        this.managedProperty().bind(AuthState.getInstance().loggedInProperty().not());

        // Configuración inicial de los campos de contraseña
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);
        passwordField.setVisible(true);
        passwordField.setManaged(true);
        // Inicializar el label de error
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        // Sincronizar campos de contraseña
        passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        // Mostrar u ocultar la contraseña
        showPasswordButton.setOnAction(event -> togglePasswordVisibility());

        // Acción de login
        loginBtn.setOnAction(event -> handleLogin());

    }

    public void setVisibleBasedOnLoginStatus(boolean isLoggedIn) {
        this.setVisible(!isLoggedIn);
        this.setManaged(!isLoggedIn);  // Esto evita que ocupe espacio cuando está oculto
    }

    private void togglePasswordVisibility() {
        boolean showing = passwordTextField.isVisible();

        // Alternar visibilidad y gestión de los campos
        passwordTextField.setVisible(!showing);
        passwordTextField.setManaged(!showing);
        passwordField.setVisible(showing);
        passwordField.setManaged(showing);

        // Asegurarse de que el campo visible tiene el foco
        if (showing) {
            passwordField.requestFocus();
            passwordField.end();
        } else {
            passwordTextField.requestFocus();
            passwordTextField.end();
        }
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.isVisible() ? passwordField.getText() : passwordTextField.getText();

        // Validaciones
        if (username.isEmpty() || password.isEmpty()) {
            showErrorMessage("Por favor, completa todos los campos");
            return;
        }

        if (password.length() < 6) {
            showErrorMessage("Mínimo 6 caracteres para la contraseña");
            return;
        }

        loginBtn.setDisable(true);

        AuthService.login(username, password)
                .thenAcceptAsync(isAuthenticated -> {
                    Platform.runLater(() -> {
                        if (isAuthenticated) {
                            AuthState.getInstance().login();
                            AuthService.getUserInfo();
                            clearFields();
                        } else {
                            showErrorMessage("Credenciales incorrectas");
                        }
                        loginBtn.setDisable(false);
                    });
                })
                .exceptionally(e -> {
                    Platform.runLater(() -> {
                        showErrorMessage("Error de conexión: " + e.getMessage());
                        loginBtn.setDisable(false);
                    });
                    return null;
                });
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        passwordTextField.clear();
    }

    private void showErrorMessage(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);

        // Ocultar el mensaje después de 5 segundos
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(5));
        visiblePause.setOnFinished(event -> {
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        });
        visiblePause.play();
    }
}
