package org.arzzcorp.barisystem.components;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.arzzcorp.barisystem.components.generic.Branches;
import org.arzzcorp.barisystem.components.generic.MenuSide;
import org.arzzcorp.barisystem.components.generic.ProductRow;
import org.arzzcorp.barisystem.hooks.BranchState;
import org.arzzcorp.barisystem.services.ProductService;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.Consumer;

public class ProductsList extends VBox {

    private Branches branches;

    @FXML
    private ListView<JSONObject> listView;
    private SortedList<JSONObject> sortedList;

    private ObservableList<JSONObject> observableList = FXCollections.observableArrayList();
    private ObservableList<JSONObject> selectedProductsList; // Referencia a la lista de Prices
    private Consumer<JSONObject> onProductAddedListener;

    public ProductsList() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/components/products-list.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            //initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    public static void setTokenGetted(ProductsList instance) {
        instance.initialize(); // Llamar a initialize() en la instancia pasada
    }

    // ProductsList.java
    public void clearProducts() {
        observableList.clear();
        listView.setPlaceholder(new Label("No hay productos disponibles"));
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }

    private void initialize() {
        // this.getChildren().add(listView);
        // Si

        listView.setPlaceholder(new Label("No hay productos disponibles"));

        // Inicializar SortedList
        sortedList = new SortedList<>(observableList);
        listView.setItems(sortedList);

        BranchState.getInstance().currentBranchProperty().addListener((observable, oldBranch, newBranch) -> {
            if (newBranch != null) {
                loadProducts(newBranch.toString());
            }
        });

        loadProducts(BranchState.getInstance().getCurrentBranch().toString());

        listView.setCellFactory(param -> new ListCell<JSONObject>() {
            @Override
            protected void updateItem(JSONObject item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    ProductRow row = new ProductRow();
                    row.setProductData(
                            item.optString("CODIGO", ""),
                            item.optString("DESCRIPCION", ""),
                            item.optString("TIPO", ""),
                            item.optString("FAMILIA", ""),
                            item.optDouble("COSTO", 0.0),
                            item.optDouble("P01", 0.0)
                    );
                    // Escucha el clic en el botón de agregar
                    row.setOnAddButtonClick(() -> {
                        if (onProductAddedListener != null) {
                            onProductAddedListener.accept(item); // Notifica a Prices
                        }
                    });

                    setGraphic(row);
                }
            }
        });
    }

    // Método para cargar productos, con o sin filtro
    public void loadProducts(String  branch) {

        if (branches != null && !branches.isPricesMode()) {
            System.out.println("No se necesita hacer la petición porque no estamos en Prices.");
            return;
        }

        Platform.runLater(() -> {
            observableList.clear();
            listView.setPlaceholder(new Label("Cargando productos..."));
        });

        ProductService.loadProductsFromAPI(branch, productsList -> {
            if (productsList != null && productsList.length() > 0) {
                observableList.clear();

                for (int i = 0; i < productsList.length(); i++) {
                    JSONObject product = productsList.getJSONObject(i);

                    observableList.add(product);
                }

            } else {
                observableList.clear(); // Asegura que esté vacío
                listView.setPlaceholder(new Label("No hay productos disponibles o hubo un error en la api"));
            }
        });
    }

    // Método para crear comparadores según el criterio seleccionado
    private Comparator<JSONObject> createComparator(String selectedOrder) {
        switch (selectedOrder) {
            case "Nombre": // ← Coincide con el ComboBox
                return Comparator.comparing(json -> json.optString("DESCRIPCION", "")); // Campo correcto
            case "Familia":
                return Comparator.comparing(json -> json.optString("FAMILIA", ""));
            case "PLU": // ← Nueva opción
                return Comparator.comparing(json -> json.optString("CODIGO", ""));
            case "Precio":
                return Comparator.comparingDouble(json -> json.optDouble("P01", 0.0));
            case "Modificados": // ← Define cómo ordenar esto
                return Comparator.comparing(json -> json.optString("ULTIMA_MODIFICACION", ""));
            default:
                return (a, b) -> 0;
        }
    }

    public void sortBy(String criteria) {
        Comparator<JSONObject> comparator = createComparator(criteria);
        sortedList.setComparator(comparator);
    }

    // Método para recibir la lista de seleccionados desde Prices
    public void setSelectedProductsList(ObservableList<JSONObject> selectedProductsList) {
        this.selectedProductsList = selectedProductsList;
    }

    // Método para recibir el listener desde Prices
    public void setOnProductAdded(Consumer<JSONObject> listener) {
        this.onProductAddedListener = listener;
    }

}
