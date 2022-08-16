package com.example.priceChecker.productOperations;

import com.example.pricechecker.logic.productOperations.ProductSorter;
import com.example.pricechecker.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductSorterTest {
    private final ProductSorter sorter = new ProductSorter();

    @Test
    void findCheapest_ThreeProducts_SortByPriceAscending() {
        Product cheapest = createProductWithPrice(0L);
        Product medium = createProductWithPrice(5L);
        Product mostExpensive = createProductWithPrice(10L);

        List<Product> expected = List.of(cheapest,medium,mostExpensive);
        List<Product> actual = sorter.findCheapest(List.of(medium,mostExpensive,cheapest),3);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void findCheapest_LimitedProductCount_ReturnWithLimitedSize(){
        Product cheapest = createProductWithPrice(0L);
        Product mostExpensive = createProductWithPrice(10L);

        List<Product> expected = List.of(cheapest);
        List<Product> actual = sorter.findCheapest(List.of(mostExpensive,cheapest),1);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    void findCheapest_NullProduct_ReturnWithoutNullProduct(){
        Product product = createProductWithPrice(0L);
        Product nullPriceProduct = createProductWithPrice(null);

        List<Product> expected = List.of(product);
        List<Product> actual = sorter.findCheapest(List.of(product,nullPriceProduct),2);

        Assertions.assertEquals(expected,actual);
    }

    private Product createProductWithPrice(Long price){
        Product product = new Product();
        product.setPrice(price);
        return product;
    }
}