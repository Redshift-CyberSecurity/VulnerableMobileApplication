package com.redshift.rs_vapourtraining;

public class ProductModal {
    // variables for our productName,
    // description, tracks and duration, id.
    private String productName;
    private String productURL;
    private String productPrice;
    private String productDescription;
    private int productCreditsworth;
    private int id;

    // creating getter and setter methods
    public String getproductName() {
        return productName;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public String getproductURL() {
        return productURL;
    }

    public void setproductURL(String productURL) {
        this.productURL = productURL;
    }

    public String getproductPrice() {
        return productPrice;
    }

    public void setproductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getproductDescription() {
        return productDescription;
    }

    public void setproductDescription(String productDescription) {this.productDescription = productDescription;}

    public int getProductCreditsworth() { return productCreditsworth;}

    public void setProductCreditsworth(int productCreditsworth) { this.productCreditsworth = productCreditsworth;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // constructor
    public ProductModal(String productName, String productURL, String productPrice, String productDescription, int id, int credits) {
        this.productName = productName;
        this.productURL = productURL;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.id = id;
        this.productCreditsworth = credits;
    }
}
