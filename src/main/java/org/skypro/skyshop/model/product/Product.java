package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String nameProduct;

    public Product(UUID id, String nameProduct) {
        if (nameProduct == null) {
            throw new IllegalArgumentException("Название продукто не может быть null");
        }
        if (nameProduct.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым или состоять только из пробелов");
        }
        this.id = id;
        this.nameProduct = nameProduct;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public abstract int getCostProduct();

    public String getNameProduct() {
        return nameProduct;
    }

    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return nameProduct;
    }

    @JsonIgnore
    @Override
    public String getSearchTerm() {
        return nameProduct;
    }

    @JsonIgnore
    @Override
    public String getSearchContentType() {
        return "PRODUCT";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(nameProduct, product.nameProduct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameProduct);
    }
}