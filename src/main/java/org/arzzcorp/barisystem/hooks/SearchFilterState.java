package org.arzzcorp.barisystem.hooks;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Clase que mantiene el estado del filtro de búsqueda utilizando propiedades de JavaFX.
 * Implementa el patrón Singleton para acceso global.
 */
public class SearchFilterState {

    // Singleton instance
    private static SearchFilterState instance;

    // Enum para los modos de búsqueda disponibles
    public enum SearchMode {
        PLU("PLU"),
        NOMBRE("Nombre"),
        FAMILIA("Familia");

        private final String displayName;

        SearchMode(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Propiedad observable para el modo de búsqueda actual
    private final ObjectProperty<SearchMode> currentSearchMode = new SimpleObjectProperty<>(SearchMode.PLU);

    // Constructor privado (patrón Singleton)
    private SearchFilterState() {}

    /**
     * Obtiene la instancia única de SearchFilterState
     * @return La instancia de SearchFilterState
     */
    public static synchronized SearchFilterState getInstance() {
        if (instance == null) {
            instance = new SearchFilterState();
        }
        return instance;
    }

    /**
     * Obtiene la propiedad observable del modo de búsqueda actual
     * @return La propiedad del modo de búsqueda actual
     */
    public ObjectProperty<SearchMode> currentSearchModeProperty() {
        return currentSearchMode;
    }

    /**
     * Obtiene el modo de búsqueda actualmente seleccionado
     * @return El modo de búsqueda actual
     */
    public SearchMode getCurrentSearchMode() {
        return currentSearchMode.get();
    }

    /**
     * Cambia el modo de búsqueda seleccionado
     * @param mode El nuevo modo de búsqueda seleccionado
     */
    public void setCurrentSearchMode(SearchMode mode) {
        currentSearchMode.set(mode);
    }

}
