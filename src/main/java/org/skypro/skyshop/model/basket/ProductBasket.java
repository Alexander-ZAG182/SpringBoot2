package org.skypro.skyshop.model.basket;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@SessionScope
public class ProductBasket {
    private final Map<UUID, Integer> basketItems = new HashMap<>();

    public void addProduct(UUID productId) {
        if (basketItems.containsKey(productId)) {
            int currentQuantity = basketItems.get(productId);
            basketItems.put(productId, currentQuantity + 1);
        } else {
            basketItems.put(productId, 1);
        }
    }

    public Map<UUID, Integer> getBasketItems() {
        return Collections.unmodifiableMap(basketItems);
    }

}
