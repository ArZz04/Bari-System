package org.arzzcorp.barisystem.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.arzzcorp.barisystem.services.APIService;
import org.arzzcorp.barisystem.services.AuthState;
import org.arzzcorp.barisystem.services.UserService;

import java.io.IOException;

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

        this.setAlignment(Pos.CENTER); // Centra los hijos del HBox
        this.setSpacing(10); // Espacio entre elementos (opcional)

        AuthState.getInstance().loggedInProperty().addListener((obs, oldVal, newVal) -> updateVisibility(newVal));

        updateVisibility(AuthState.getInstance().isLoggedIn());
        UserService.setUserBox(this); // <- Asegúrate de poner esto aquí

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

        try {
            Image image;
            // Si la ruta tiene "/" al principio, la tratamos como un recurso dentro del classpath
            if (imageUrl.startsWith("/")) {
                image = new Image(getClass().getResource(imageUrl).toExternalForm());
            } else {
                // Si no es una ruta con "/", buscamos la ruta absoluta
                image = new Image("file:" + imageUrl); // Ruta de archivo directo
            }
            userImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            userImage.setImage(new Image(getClass().getResource("/org/arzzcorp/barisystem/images/users/default-avatar.png").toExternalForm()));
        }
    }

    @FXML
    private void handleLogout() {
        AuthState.getInstance().logout(); // Desconecta desde el estado global
        APIService.logout();

        userImage.setImage(new Image(getClass().getResource("/org/arzzcorp/barisystem/images/users/default-avatar.png").toExternalForm()));

        UserService.clearUserInfo();
    }

}
