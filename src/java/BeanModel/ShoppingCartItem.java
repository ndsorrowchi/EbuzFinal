/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author chiming
 */
public class ShoppingCartItem {

    private BookModel product;
    private int quantity;

    public ShoppingCartItem(BookModel model) {
        product = model;
        quantity = 1;
    }

    public BookModel getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        if(quantity>1)//no decrementing if only 1, should use remove item instead of decrement
            quantity--;
    }

    public double getTotal() {
        double amount = 0;
        BigDecimal bi = new BigDecimal(product.getPrice()).setScale(2,RoundingMode.HALF_EVEN);
        amount = (this.getQuantity() * bi.doubleValue());
        return amount;
    }    
}
