package com.example.pricechecker.logic.productOperations;

import com.example.pricechecker.model.Product;

import java.util.List;

public class ProductOperations {
    public List<Product> findCheapest(List<Product> products, int limit){
        return products.stream().filter(x->x.getPrice()!=null).sorted((product1,product2)->product1.getPrice()>product2.getPrice()?1:-1).limit(limit).toList();
    }

    public String toString(Product product){
        StringBuilder builder = new StringBuilder();
        builder.append("site: ").append(product.getSiteName()).append("\n");
        builder.append("url: ").append(product.getUrl()).append("\n");
        builder.append("name: ").append(product.getName()).append("\n");
        builder.append("price: ").append(product.getPrice()).append("\n");
        return builder.toString();
    }
}
