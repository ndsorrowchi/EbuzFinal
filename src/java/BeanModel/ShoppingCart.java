/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chiming
 */
public class ShoppingCart  extends Object implements Serializable {

    private List<ShoppingCartItem> items;
    private int numberOfItems;
    private double total;

    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    public ShoppingCart(ArrayList<ShoppingCartItem> arr, int count, int sum) {
        items = arr;
        numberOfItems = count;
        total = sum;
    }
    
    public ShoppingCartItem findById(int id){
        if(id<1)
            return null;
        ShoppingCartItem item = null;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProduct().getBid() == id) {
                item = scItem;
                break;
            }
        }

        return item;
    }
    
    public synchronized void addItem(BookModel product) {

        boolean newItem = true;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProduct().getBid() == product.getBid()) {

                newItem = false;
                scItem.incrementQuantity();
            }
        }

        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(product);
            items.add(scItem);
        }
    }

    public synchronized void remove(BookModel product) {
        ShoppingCartItem item = null;

        for (ShoppingCartItem scItem : items) {

            if (scItem.getProduct().getBid() == product.getBid()) {
                    item = scItem;
                    break;
            }
        }
        if (item != null) {
            // remove from cart
            items.remove(item);
        }        
    }
    
    public synchronized void update(BookModel product, String quantity) {

        short qty = -1;

        // cast quantity as short
        qty = Short.parseShort(quantity);

        if (qty >= 0) {

            ShoppingCartItem item = null;

            for (ShoppingCartItem scItem : items) {

                if (scItem.getProduct().getBid() == product.getBid()) {

                    if (qty != 0) {
                        // set item quantity to new value
                        scItem.setQuantity(qty);
                    } else {
                        // if quantity equals 0, save item and break
                        item = scItem;
                        break;
                    }
                }
            }

            if (item != null) {
                // remove from cart
                items.remove(item);
            }
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {

        return items;
    }

    public synchronized int getNumberOfItems() {

        numberOfItems = 0;

        for (ShoppingCartItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getSubtotal() {

        double amount = 0;

        for (ShoppingCartItem scItem : items) {
            amount += (scItem.getTotal());
        }

        return amount;
    }

    
    public synchronized void calculateTotal() {

        double amount = 0;

        // tax 7%
        double tax_rate = 0.07;

        amount = this.getSubtotal();

        total = amount*(1.0+tax_rate);
    }

    public synchronized double getTotal() {
        //this.calculateTotal();
        // tax 7%
        double tax_rate = 0.07;
        return this.getSubtotal()*tax_rate;
    }

    public synchronized double getTaxes() {
        this.calculateTotal();
        return total;
    }
    
    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }
    
    
}
