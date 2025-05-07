package org.arzzcorp.barisystem.utils;

public class StateManager {

    public enum SearchMode {
        PLU,
        NOMBRE,
        FAMILIA
    }

    private static SearchMode searchMode = SearchMode.PLU;

    // Getter para obtener el estado
    public static SearchMode getSearchMode() {
        return searchMode;
    }

    // Setter para modificar el estado
    public static void setSearchMode(SearchMode mode) {
        searchMode = mode;
    }
}
