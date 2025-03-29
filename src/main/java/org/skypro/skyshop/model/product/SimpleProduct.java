package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int costProduct;

    public SimpleProduct(UUID id, String nameProduct, int costProduct) {
        super(id, nameProduct);
        if (costProduct <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть больше 0 ");
        }
        this.costProduct = costProduct;
    }

    @Override
    public int getCostProduct() {
        return costProduct;
    }

    @Override
    public String toString() {
        return getNameProduct() + ": " + getCostProduct();
    }
}