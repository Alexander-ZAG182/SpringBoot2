package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage = new HashMap<>();
    private final Map<UUID, Article> articleStorage = new HashMap<>();

    public StorageService() {
        initializeStorage();
    }

    private void initializeStorage() {
        Product bike = new SimpleProduct(UUID.randomUUID(), "Велосипед", 13000);
        Product apple = new DiscountedProduct(UUID.randomUUID(), "Яблоко", 500, 10);
        Product banana = new FixPriceProduct(UUID.randomUUID(), "Банан");
        Product water = new DiscountedProduct(UUID.randomUUID(), "Вода", 100, 20);
        Product cheese = new FixPriceProduct(UUID.randomUUID(), "Сыр");
        Product juice = new SimpleProduct(UUID.randomUUID(), "Сок", 350);

        productStorage.put(bike.getId(), bike);
        productStorage.put(apple.getId(), apple);
        productStorage.put(banana.getId(), banana);
        productStorage.put(water.getId(), water);
        productStorage.put(cheese.getId(), cheese);
        productStorage.put(juice.getId(), juice);

        Article appleArticle = new Article(UUID.randomUUID(), "Статья о яблоках", "Яблоко - очень полезно");
        Article bikeArticle = new Article(UUID.randomUUID(), "Статья о велосипеде", "Велосипед - средство передвижения");
        Article longArticle = new Article(UUID.randomUUID(), "Очень длинная статья о здоровом питании", "Здоровое питание - это важно");
        Article shortArticle = new Article(UUID.randomUUID(), "Короткая статья", "Коротко и ясно");

        articleStorage.put(appleArticle.getId(), appleArticle);
        articleStorage.put(bikeArticle.getId(), bikeArticle);
        articleStorage.put(longArticle.getId(), longArticle);
        articleStorage.put(shortArticle.getId(), shortArticle);
    }

    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    public Collection<Searchable> getAllSearchables() {
        Collection<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productStorage.values());
        searchables.addAll(articleStorage.values());
        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productStorage.get(id));
    }
}




