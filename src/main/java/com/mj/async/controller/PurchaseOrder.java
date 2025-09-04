package com.mj.async.controller;

import com.mj.async.service.PurchaseService;
import com.mj.async.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseOrder {

    @Autowired
    PurchaseService purchaseService;

    @PostMapping("purchase/order")
    public ResponseEntity<?> purchase(@RequestBody Product product) {
        Product product1 = purchaseService.purchase(product);
        purchaseService.sendNotify();
        purchaseService.assignVendor();
        purchaseService.packing();
        purchaseService.logistics();
        purchaseService.Delivered();
        return ResponseEntity.ok(product1);
    }

}
