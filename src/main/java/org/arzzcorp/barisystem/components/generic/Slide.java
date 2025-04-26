package org.arzzcorp.barisystem.components.generic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.arzzcorp.barisystem.services.AuthState;

import java.io.IOException;

public class Slide extends StackPane {

    private ImageView[] images;  // Array para contener las imágenes
    private int currentIndex = 0;  // Índice para controlar la imagen visible

    public Slide() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/slide-images.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for Main", e);
        }
    }

    private void initialize() {

        System.out.println("Slide initialized");

        // Inicializa el array de imágenes
        images = new ImageView[]{
                (ImageView) lookup("#image1"), // Asume que las imágenes tienen IDs en el FXML
                (ImageView) lookup("#image2"),
                (ImageView) lookup("#image3")
        };

        blurAllImages();

        // Establece la primera imagen visible
        images[currentIndex].setVisible(true);

        // Configura el Timeline para cambiar las imágenes cada 5 segundos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> changeImage())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Observa el estado de inicio de sesión del usuario
        AuthState.getInstance().loggedInProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Si está logeado, enfocar todas las imágenes
                unblurAllImages();
            } else {
                // Si no está logeado, desenfocar todas las imágenes
                blurAllImages();
            }
        });
    }

    private void changeImage() {
        // Oculta la imagen actual
        images[currentIndex].setVisible(false);

        // Cambia al siguiente índice, si llega al final, regresa al inicio
        currentIndex = (currentIndex + 1) % images.length;

        // Muestra la nueva imagen
        images[currentIndex].setVisible(true);
    }

    // Función para desenfocar todas las imágenes
    public void blurAllImages() {
        for (ImageView image : images) {
            GaussianBlur blurEffect = new GaussianBlur(10); // 10 es el radio del desenfoque
            image.setEffect(blurEffect);
        }
    }

    // Función para quitar el desenfoque de todas las imágenes
    public void unblurAllImages() {
        for (ImageView image : images) {
            image.setEffect(null); // Remueve el efecto de desenfoque
        }
    }

}
