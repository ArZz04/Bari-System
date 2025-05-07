package org.arzzcorp.barisystem.components.views;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.arzzcorp.barisystem.components.ProductsList;
import org.arzzcorp.barisystem.components.generic.Branches;
import org.arzzcorp.barisystem.components.generic.OrderDropdown;
import org.arzzcorp.barisystem.components.generic.SearchBar;
import org.arzzcorp.barisystem.hooks.BranchState;
import org.arzzcorp.barisystem.services.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Prices extends VBox {

    @FXML
    private OrderDropdown orderDropdown;
    @FXML private ProductsList productsList;
    private SearchBar searchBar;
    @FXML private Branches branches;
    @FXML private Button sendButton;
    @FXML private Button clearButton;
    @FXML private Label labelAlert;
    @FXML private Label selectedProductsCounter;

    // Lista observable para productos seleccionados
    private final ObservableList<JSONObject> selectedProducts = FXCollections.observableArrayList();
    private final ObservableList<JSONObject> addedParquesProducts = FXCollections.observableArrayList();
    private final ObservableList<JSONObject> addedBothProducts = FXCollections.observableArrayList();

    public Prices() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/arzzcorp/barisystem/views/prices-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            initialize();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML for MenuSide", e);
        }
    }

    public ProductsList getProductsList() {
        return productsList;
    }

    // Initialize method to set up the view
    @FXML
    private void initialize() {
        productsList.setBranches(branches);
        orderDropdown.setOnOrderSelected(productsList::sortBy);

        // Listener para cambios de sucursal
        BranchState.getInstance().currentBranchProperty().addListener((obs, oldBranch, newBranch) -> {
            resetSelections(); // Limpiar selecciones al cambiar de branch
            productsList.loadProducts(newBranch.toString()); // Recargar productos
        });

        productsList.setOnProductAdded(product -> {
            String currentBranch = String.valueOf(BranchState.getInstance().getCurrentBranch());
            String productId = product.optString("NUMERO", ""); // ID único del producto

            // Verificar duplicado
            if (!isDuplicated(productId)) {
                addProduct(currentBranch, product);
                updateCounter();
            }
        });

        productsList.setSelectedProductsList(selectedProducts);
    }

    @FXML
    private void onSendButtonClicked() {
        // Obtener el nombre del branch actual
        String currentBranchName = BranchState.getInstance().getCurrentBranch().toString(); // asumiendo getName() retorna "PARQUES" o "VALLE_CIMA"

        // Determinar la ruta de la API y los productos según la sucursal
        String branchKey;
        ObservableList<JSONObject> productsToSend;

        if ("VALLE_CIMA".equalsIgnoreCase(currentBranchName)) {
            branchKey = "both";
            productsToSend = addedBothProducts;
        } else {
            branchKey = "parques";
            productsToSend = addedParquesProducts;
        }

        if (productsToSend.isEmpty()) {
            System.out.println("No hay productos para enviar.");
            return;
        }

        System.out.println("Productos a enviar: " + productsToSend);
        JSONArray jsonArray = new JSONArray(productsToSend);

        sendButton.setDisable(true); // Desactiva el botón durante el envío

        ProductService.sendProductsToAPI(jsonArray, branchKey).thenAccept(success -> {
            Platform.runLater(() -> {
                if (success) {
                    addedBothProducts.clear();
                    addedParquesProducts.clear();
                } else {
                    System.err.println("Error al enviar productos.");
                }
                sendButton.setDisable(false); // Reactiva el botón
            });
        });
    }

    @FXML
    private void onClearButtonClicked() {
        // Limpiar listas
        getAddedParquesProducts().clear();
        getAddedBothProducts().clear();

        // Actualizar contador
        selectedProductsCounter.setText("0");

        // Mostrar mensaje temporal
        labelAlert.setVisible(true);
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> labelAlert.setVisible(false));
        delay.play();

        // Recargar productos
        String currentBranch = BranchState.getInstance().getCurrentBranch().toString();
        productsList.clearProducts();  // Limpiar lista visual
        productsList.loadProducts(currentBranch);  // Recargar desde API
    }

    private boolean isDuplicated(String productId) {
        return addedBothProducts.stream().anyMatch(p -> p.optString("NUMERO", "").equals(productId)) ||
                addedParquesProducts.stream().anyMatch(p -> p.optString("NUMERO", "").equals(productId));
    }

    private void addProduct(String branch, JSONObject product) {
        if ("VALLE_CIMA".equalsIgnoreCase(branch)) {
            addedBothProducts.add(product);
        } else {
            addedParquesProducts.add(product);
        }
    }

    private void updateCounter() {
        int total = addedBothProducts.size() + addedParquesProducts.size();
        selectedProductsCounter.setText(String.valueOf(total));
    }

    private void resetSelections() {
        addedBothProducts.clear();
        addedParquesProducts.clear();
        updateCounter();
    }


    public ObservableList<JSONObject> getSelectedProducts() {
        return selectedProducts;
    }

    public ObservableList<JSONObject> getAddedParquesProducts() {
        return addedParquesProducts;
    }

    public ObservableList<JSONObject> getAddedBothProducts() {
        return addedBothProducts;
    }

    public Branches getBranchesPrices() {
        return branches;
    }

}