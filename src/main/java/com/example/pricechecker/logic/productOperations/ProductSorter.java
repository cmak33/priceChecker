package com.example.pricechecker.logic.productOperations;

import com.example.pricechecker.model.Product;

import java.util.List;

public class ProductSorter {
    public List<Product> findCheapest(List<Product> products, int limit){
        return products.stream()
                .filter(product->product.getPrice()!=null)
                .sorted(this::comparePrices)
                .limit(limit)
                .toList();
    }

    private int comparePrices(Product product1,Product product2){
        return product1.getPrice().compareTo(product2.getPrice());
    }
}
