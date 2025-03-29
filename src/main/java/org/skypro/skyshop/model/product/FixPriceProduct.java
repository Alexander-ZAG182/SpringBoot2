package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private final static int PRICE = 1000;

    public FixPriceProduct(UUID id, String nameProduct) {
        super(id, nameProduct);
    }

    @Override
    public int getCostProduct() {
        return PRICE;
    }

    @Override
    public String toString() {
        return getNameProduct() + " : фиксированная цена " + PRICE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}