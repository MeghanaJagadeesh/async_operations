package com.mj.async.dto;

import lombok.Data;

@Data
public class Product {

    int id;
    String productName;
    String category;
    int qty;

    public Product(int id, String productName, String category, int qty) {
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.qty = qty;
    }
}
