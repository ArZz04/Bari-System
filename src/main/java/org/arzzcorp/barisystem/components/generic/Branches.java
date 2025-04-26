package org.arzzcorp.barisystem.components.generic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.hooks.BranchState;

import java.io.IOException;

public class Branches extends VBox {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab parquesTab;

    @FXML
    private Tab valleCimaTab;

    private final BranchState branchState = BranchState.getInstance();

    public Branches() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/branches.fxml"));
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
            updateSelectedBranchUI(newValue);
        });

        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() == 0) {
                branchState.setCurrentBranch(BranchState.Branch.PARQUES);
            } else if (newValue.intValue() == 1) {
                branchState.setCurrentBranch(BranchState.Branch.VALLE_CIMA);
            }
        });
    }

    private void updateSelectedBranchUI(BranchState.Branch branch) {
        if (tabPane == null) return; // Protección adicional por si acaso

        switch (branch) {
            case PARQUES -> tabPane.getSelectionModel().select(0);
            case VALLE_CIMA -> tabPane.getSelectionModel().select(1);
        }
    }
}

