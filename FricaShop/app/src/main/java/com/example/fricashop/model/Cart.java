package com.example.fricashop.model;

public class Cart {
    public int idCart;
    private String nameCart;
    private int priceCart;
    private String imageCart;
    private int quantityCart;

    public Cart(int idCart, String nameCart, int priceCart, String imageCart, int quantityCart) {
        this.idCart = idCart;
        this.nameCart = nameCart;
        this.priceCart = priceCart;
        this.imageCart = imageCart;
        this.quantityCart = quantityCart;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getNameCart() {
        return nameCart;
    }

    public void setNameCart(String nameCart) {
        this.nameCart = nameCart;
    }

    public int getPriceCart() {
        return priceCart;
    }

    public void setPriceCart(int priceCart) {
        this.priceCart = priceCart;
    }

    public String getImageCart() {
        return imageCart;
    }

    public void setImageCart(String imageCart) {
        this.imageCart = imageCart;
    }

    public int getQuantityCart() {
        return quantityCart;
    }

    public void setQuantityCart(int quantityCart) {
        this.quantityCart = quantityCart;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "idCart=" + idCart +
                ", nameCart='" + nameCart + '\'' +
                ", priceCart=" + priceCart +
                ", imageCart='" + imageCart + '\'' +
                ", quantityCart=" + quantityCart +
                '}';
    }
}
