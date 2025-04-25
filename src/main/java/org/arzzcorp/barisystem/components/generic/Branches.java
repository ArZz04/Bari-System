package org.arzzcorp.barisystem.components.generic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.hooks.BranchState;

import java.io.IOException;

public class Branches extends VBox {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab parquesTab;

    @FXML
    private HBox valleCimaTab;

    // Referencia al singleton BranchStatus
    private final BranchState branchStatus = BranchState.getInstance();

    public Branches() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/branches.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for Branches", e);
        }
    }

    private void initialize() {
        System.out.println("Initializing Branches...");

        // Configurar el estado inicial
        updateSelectedBranchUI(BranchState.getCurrentBranch());

        // Escuchar cambios en la sucursal seleccionada
        branchStatus.currentBranchProperty().addListener((observable, oldValue, newValue) -> {
            updateSelectedBranchUI(newValue);
        });

        // Configurar listeners para las pestañas
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                // Primera pestaña seleccionada (PARQUES)
                branchStatus.setCurrentBranch(BranchState.Branch.PARQUES);
            }
        });

        // Configurar listeners para las opciones dentro de la segunda pestaña
        valleCimaTab.setOnMouseClicked(event -> {
            branchStatus.setCurrentBranch(BranchState.Branch.VALLE_CIMA);
        });
    }

    /**
     * Actualiza la interfaz de usuario para reflejar la sucursal seleccionada
     * @param branch La sucursal seleccionada
     */
    private void updateSelectedBranchUI(BranchState.Branch branch) {
        // Resetear estilos
        valleCimaTab.getStyleClass().remove("branch-option-selected");

        // Aplicar estilo según la sucursal seleccionada
        switch (branch) {
            case PARQUES:
                tabPane.getSelectionModel().select(0);
                break;
            case VALLE_CIMA:
                tabPane.getSelectionModel().select(1);
                valleCimaTab.getStyleClass().add("branch-option-selected");
                break;
        }
    }
}