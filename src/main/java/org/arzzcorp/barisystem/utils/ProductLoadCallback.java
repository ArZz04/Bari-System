package org.arzzcorp.barisystem.utils;

import org.json.JSONArray;

public interface ProductLoadCallback {
    void onProductsLoaded(JSONArray productsList);
}
