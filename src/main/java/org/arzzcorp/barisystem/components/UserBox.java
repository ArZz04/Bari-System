package org.arzzcorp.barisystem.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.arzzcorp.barisystem.services.AuthService;
import org.arzzcorp.barisystem.services.AuthState;

import java.io.IOException;
import java.util.Objects;

public class UserBox extends HBox {

    @FXML private ImageView userImage;
    @FXML private Label userName;
    @FXML private Label userRole;
    @FXML private Button logoutBtn;

    public UserBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/user-box.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize() {

        // setUserInfo("Juan Arvizu", "Sistemas", "/org/arzzcorp/barisystem/images/users/arzz.jpg");

        this.setAlignment(Pos.CENTER); // Centra los hijos del HBox
        this.setSpacing(10); // Espacio entre elementos (opcional)

        AuthState.getInstance().loggedInProperty().addListener((obs, oldVal, newVal) -> updateVisibility(newVal));
        updateVisibility(AuthState.getInstance().isLoggedIn());

        logoutBtn.setOnAction(event -> handleLogout());
    }

    private void updateVisibility(boolean loggedIn) {
        userName.setVisible(loggedIn);
        userName.setManaged(loggedIn);

        userRole.setVisible(loggedIn);
        userRole.setManaged(loggedIn);

        userImage.setVisible(loggedIn);
        userImage.setManaged(loggedIn);

        logoutBtn.setVisible(loggedIn);
        logoutBtn.setManaged(loggedIn);
    }

    public void setUserInfo(String name, String role, String imageUrl) {
        userName.setText(name);
        userRole.setText(role);
        userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageUrl))));
    }

    @FXML
    private void handleLogout() {
        AuthState.getInstance().logout(); // Desconecta desde el estado global
        AuthService.logout();
    }

}
