package com.example.mad_project;

import com.google.firebase.firestore.auth.User;

import java.io.Serializable;

public class Product implements Serializable {

    String productName, productDescription,productPrice;

    public Product(){}

    public Product(String enter_product, String enter_product_description, String enter_product_price) {
        this.productName = enter_product;
        this.productDescription = enter_product_description;
        this.productPrice = enter_product_price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
