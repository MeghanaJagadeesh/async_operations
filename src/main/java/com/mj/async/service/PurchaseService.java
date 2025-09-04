package com.mj.async.service;

import com.mj.async.dto.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {


    private boolean isProductAvailable(Product product) {
        return true;
    }

    public Product purchase(Product product) {
        if (isProductAvailable(product)) {
            paymentProcessed();
        } else {
            throw new RuntimeException("product not in stock");
        }
        return product;
    }

    private void paymentProcessed() {
        System.out.println("paymentProcessed = " + Thread.currentThread().getName());
    }


    @Async("asyncTaskExecution")
    public void sendNotify() {
        System.out.println("send notification = " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("asyncTaskExecution")
    public void assignVendor() {
        System.out.println("assing to vendor = " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("asyncTaskExecution")
    public void packing() {
        System.out.println("packing = " + Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("asyncTaskExecution")
    public void logistics() {
        System.out.println("logistics = " + Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async("asyncTaskExecution")
    public void Delivered() {
        System.out.println("Deliver = " + Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
