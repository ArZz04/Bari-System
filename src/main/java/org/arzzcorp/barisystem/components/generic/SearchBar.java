package org.arzzcorp.barisystem.components.generic;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.arzzcorp.barisystem.hooks.SearchFilterState;
import org.arzzcorp.barisystem.hooks.SearchFilterState.SearchMode;

import java.io.IOException;

public class SearchBar extends HBox {

    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button switchButton;

    // Método para filtrar productos según el texto y el modo
    private FilteredProductsListener filteredProductsListener;

    public SearchBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/generic/search-bar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for SearchBar", e);
        }
    }

    private void initialize() {
        // Inicializar el prompt del campo de búsqueda con el estado actual de SearchMode
        SearchMode currentMode = SearchFilterState.getInstance().getCurrentSearchMode();
        searchField.setPromptText("Buscar por " + getModeLabel(currentMode));

        // Agregar un listener al campo de búsqueda para actualizar el filtro en tiempo real
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (filteredProductsListener != null) {
                    filteredProductsListener.onFilterTextChanged(newValue);
                }
            }
        });
    }

    @FXML
    private void switchToSearchMode(ActionEvent event) {
        // Cambiar el modo de búsqueda utilizando SearchFilterState
        SearchMode currentMode = SearchFilterState.getInstance().getCurrentSearchMode();
        switch (currentMode) {
            case PLU -> SearchFilterState.getInstance().setCurrentSearchMode(SearchMode.NOMBRE);
            case NOMBRE -> SearchFilterState.getInstance().setCurrentSearchMode(SearchMode.FAMILIA);
            case FAMILIA -> SearchFilterState.getInstance().setCurrentSearchMode(SearchMode.PLU);
        }

        // Actualizar el prompt del campo de búsqueda
        searchField.setPromptText("Buscar por " + getModeLabel(SearchFilterState.getInstance().getCurrentSearchMode()));
    }

    private String getModeLabel(SearchMode mode) {
        return mode.getDisplayName();
    }

    // Método para registrar el listener de filtro (esto se debe hacer desde la clase que maneja la lista de productos)
    public void setFilteredProductsListener(FilteredProductsListener listener) {
        this.filteredProductsListener = listener;
    }

    // Interfaz para escuchar los cambios en el filtro
    public interface FilteredProductsListener {
        void onFilterTextChanged(String filterText);
    }
}
