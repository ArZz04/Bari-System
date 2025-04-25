package org.arzzcorp.barisystem.hooks;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Clase que mantiene el estado de la sucursal seleccionada utilizando propiedades de JavaFX.
 * Implementa el patrón Singleton para acceso global.
 */
public class BranchState {

    // Singleton instance
    private static BranchState instance;

    // Enum para las sucursales disponibles
    public enum Branch {
        PARQUES("PARQUES"),
        VALLE_CIMA("VALLE | CIMA");

        private final String displayName;

        Branch(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Propiedad observable para la sucursal actual
    private final ObjectProperty<Branch> currentBranch = new SimpleObjectProperty<>(Branch.PARQUES);

    // Constructor privado (patrón Singleton)
    private BranchState() {}

    /**
     * Obtiene la instancia única de BranchStatus
     * @return La instancia de BranchStatus
     */
    public static synchronized BranchState getInstance() {
        if (instance == null) {
            instance = new BranchState();
        }
        return instance;
    }

    /**
     * Obtiene la propiedad observable de la sucursal actual
     * @return La propiedad de la sucursal actual
     */
    public ObjectProperty<Branch> currentBranchProperty() {
        return currentBranch;
    }

    /**
     * Obtiene la sucursal actualmente seleccionada
     * @return La sucursal actual
     */
    public Branch getCurrentBranch() {
        return currentBranch.get();
    }

    /**
     * Cambia la sucursal seleccionada
     * @param branch La nueva sucursal seleccionada
     */
    public void setCurrentBranch(Branch branch) {
        currentBranch.set(branch);
    }
}