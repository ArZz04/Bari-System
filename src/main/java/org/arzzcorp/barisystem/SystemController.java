package org.arzzcorp.barisystem;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.arzzcorp.barisystem.components.ProductsList;
import org.arzzcorp.barisystem.components.generic.Branches;
import org.arzzcorp.barisystem.components.generic.MenuSide;
import org.arzzcorp.barisystem.components.views.Main;
import org.arzzcorp.barisystem.components.views.Prices;
import org.arzzcorp.barisystem.components.views.Scales;
import org.arzzcorp.barisystem.services.UserService;

import java.util.function.Consumer;

public class SystemController {

    private Consumer<String> onMenuSelected;
    private ProductsList productsList;

    @FXML private Main mainView;
    @FXML private Prices pricesView;
    @FXML private Scales scalesView;

    @FXML private StackPane contentPane;
    @FXML private MenuSide menuSide;

    @FXML
    public void initialize() {
        showMain(); // Muestra por defecto

        UserService.setPostLogoutHandler(this::showMain); // Registrar handler

        // Suscripción a eventos desde MenuSide
        menuSide.setOnMenuSelection(this::handleMenuSelection);
    }

    private void handleMenuSelection(String selected) {
        switch (selected) {
            case "main":
                showMain();
                break;
            case "prices":
                showPrices();
                break;
            case "scales":
                showScales();
        }
    }

    private void showMain() {
        mainView.setVisible(true);
        pricesView.setVisible(false);
        scalesView.setVisible(false);

        // Limpiar la lista al salir de Prices
        ProductsList productsList = pricesView.getProductsList();
        if (productsList != null) {
            productsList.clearProducts();
        }
    }

    private void showPrices() {

        // Obtener ProductsList desde pricesView
        ProductsList productsList = pricesView.getProductsList();
        if (productsList != null) {
            ProductsList.setTokenGetted(productsList);
        }

        Branches branches = pricesView.getBranchesPrices();
        if (branches != null) {
            branches.disableTabs();
        }

        mainView.setVisible(false);
        pricesView.setVisible(true);
        scalesView.setVisible(false);
    }

    private void showScales() {

        // Limpiar la lista al salir de Prices
        ProductsList productsList = pricesView.getProductsList();
        if (productsList != null) {
            productsList.clearProducts();
        }

        Branches branches = scalesView.getBranchesPrices();
        if (branches != null) {
            branches.enableTabs();
        }

        mainView.setVisible(false);
        pricesView.setVisible(false);
        scalesView.setVisible(true);
    }

    public void setOnMenuSelected(Consumer<String> onMenuSelected) {
        this.onMenuSelected = onMenuSelected;
    }
}
