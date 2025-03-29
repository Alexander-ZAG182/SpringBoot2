package skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProductToBasket_WhenProductNotExists_ThrowsException() {
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProductToBasket(productId);
        });
    }

    @Test
    void addProductToBasket_WhenProductExists_AddsToBasket() {
        UUID productId = UUID.randomUUID();
        Product mockProduct = mock(Product.class);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

        basketService.addProductToBasket(productId);

        verify(productBasket).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketEmpty_ReturnsEmptyBasket() {
        when(productBasket.getBasketItems()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertTrue(result.getBasketItems().isEmpty());
        assertEquals(0, result.getTotal());
    }

    @Test
    void getUserBasket_WhenBasketHasItems_ReturnsCorrectBasket() {
        UUID productId = UUID.randomUUID();
        Product mockProduct = mock(Product.class);
        when(mockProduct.getCostProduct()).thenReturn(100);

        when(productBasket.getBasketItems()).thenReturn(Collections.singletonMap(productId, 2));
        when(storageService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

        UserBasket result = basketService.getUserBasket();

        assertEquals(1, result.getBasketItems().size());
        assertEquals(200, result.getTotal()); // 100 * 2 = 200
    }

    @Test
    void getUserBasket_ShouldCalculateTotalCorrectlyForMultipleItems() {
        UUID product1 = UUID.randomUUID();
        UUID product2 = UUID.randomUUID();



        Product mockProduct1 = mock(Product.class);
        when(mockProduct1.getCostProduct()).thenReturn(100);

        Product mockProduct2 = mock(Product.class);
        when(mockProduct2.getCostProduct()).thenReturn(200);

        when(productBasket.getBasketItems())
                .thenReturn(Map.of(
                        product1, 3,
                        product2, 1
                ));

        when(storageService.getProductById(product1)).thenReturn(Optional.of(mockProduct1));
        when(storageService.getProductById(product2)).thenReturn(Optional.of(mockProduct2));

        UserBasket result = basketService.getUserBasket();

        assertEquals(2, result.getBasketItems().size());
        assertEquals(500, result.getTotal()); // (100*3) + (200*1) = 500
    }
}