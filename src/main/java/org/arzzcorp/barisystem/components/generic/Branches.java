package org.arzzcorp.barisystem.components.generic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.hooks.BranchState;

import java.io.IOException;

public class Branches extends VBox {

    @FXML private TabPane tabPane;

    @FXML private Tab parquesTab;

    @FXML private Tab valleTab;

    @FXML private Tab cimaTab;

    @FXML private Tab valleCimaTab;

    private final BranchState branchState = BranchState.getInstance();
    private boolean isPricesMode = true;

    public Branches() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/branches-prices.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for Branches", e);
        }
    }

    @FXML
    private void initialize() {
        updateSelectedBranchUI(branchState.getCurrentBranch());

        branchState.currentBranchProperty().addListener((observable, oldValue, newValue) -> {
            // Actualiza la UI cuando cambia la rama
            System.out.println("Branch changed from " + oldValue + " to " + newValue);
            updateSelectedBranchUI(newValue);
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            // Si salgo de valleCimaTab a otro tab, reseteo a PARQUES
            if (oldTab == valleCimaTab && newTab != valleCimaTab) {
                branchState.setCurrentBranch(BranchState.Branch.PARQUES);
            }

            // Lógica estándar de cambio de estado según el tab actual
            if (newTab == parquesTab) {
                branchState.setCurrentBranch(BranchState.Branch.PARQUES);
            } else if (newTab == valleTab) {
                branchState.setCurrentBranch(BranchState.Branch.VALLE);
            } else if (newTab == cimaTab) {
                branchState.setCurrentBranch(BranchState.Branch.CIMA);
            } else if (newTab == valleCimaTab) {
                branchState.setCurrentBranch(BranchState.Branch.VALLE_CIMA);
            }
        });
    }

    private void updateSelectedBranchUI(BranchState.Branch branch) {
        if (tabPane == null) return; // Protección adicional por si acaso

        switch (branch) {
            case PARQUES -> tabPane.getSelectionModel().select(parquesTab);
            case VALLE -> tabPane.getSelectionModel().select(valleTab);
            case CIMA -> tabPane.getSelectionModel().select(cimaTab);
            case VALLE_CIMA -> tabPane.getSelectionModel().select(valleCimaTab);
        }
    }

    // Se ejecutara esta funcion al cambiar a precios
    public void disableTabs() {
        isPricesMode = true;

        branchState.setCurrentBranch(BranchState.Branch.PARQUES);

        // Remover las pestañas que no deben verse en "Prices"
        tabPane.getTabs().removeAll(valleTab, cimaTab);
        // Asegurarse de que las pestañas requeridas estén presentes
        if (!tabPane.getTabs().contains(parquesTab)) {
            tabPane.getTabs().add(0, parquesTab);
        }
        if (!tabPane.getTabs().contains(valleCimaTab)) {
            tabPane.getTabs().add(valleCimaTab);
        }
    }


    // Se ejecutara esta funcion al cambiar a Scales
    public void enableTabs() {
        isPricesMode = false;
        // Remover la pestaña que no debe verse en "Scales"
        tabPane.getTabs().remove(valleCimaTab);
        // Asegurarse de que las pestañas requeridas estén presentes
        if (!tabPane.getTabs().contains(valleTab)) {
            tabPane.getTabs().add(1, valleTab);
        }
        if (!tabPane.getTabs().contains(cimaTab)) {
            tabPane.getTabs().add(2, cimaTab);
        }
    }

    public boolean isPricesMode() {
        return isPricesMode;
    }
}

