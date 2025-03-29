package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserBasket {
    private final List<BasketItem> basketItems;
    private final int total;

    public UserBasket(Map<UUID, Integer> basketItems, StorageService storageService) {
        this.basketItems = basketItems.entrySet().stream()
                .map(entry -> {
                    UUID productId = entry.getKey();
                    int quantity = entry.getValue();
                    Product product = storageService.getProductById(productId)
                            .orElseThrow(() -> new IllegalArgumentException("Товар с ID " + productId + " не найден."));
                    return new BasketItem(product, quantity);
                })
                .collect(Collectors.toList());
        this.total = this.basketItems.stream()
                .mapToInt(item -> item.getProduct().getCostProduct() * item.getQuantity())
                .sum();
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public int getTotal() {
        return total;
    }
}
