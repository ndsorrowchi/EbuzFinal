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
    
    public void addItem(BookModel product) {

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

    public void remove(BookModel product) {
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
    
    public void update(BookModel product, String quantity) {

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

    public List<ShoppingCartItem> getItems() {

        return items;
    }
    
    public void setItems(List<ShoppingCartItem> li) {
         items=li;
    }

    public int getNumberOfItems() {

        numberOfItems = 0;

        for (ShoppingCartItem scItem : items) {

            numberOfItems += scItem.getQuantity();
        }

        return numberOfItems;
    }
    
    public void setNumberofItems(int li) {
         this.numberOfItems=li;
    }

    public double getSubtotal() {

        double amount = 0;

        for (ShoppingCartItem scItem : items) {
            amount += (scItem.getTotal());
        }

        return amount;
    }

    
    public void calculateTotal() {

        double amount = 0;

        // tax 7%
        double tax_rate = 0.07;

        amount = this.getSubtotal();

        total = amount*(1.0+tax_rate);
    }

    public double getTaxes() {
        //this.calculateTotal();
        // tax 7%
        double tax_rate = 0.07;
        return this.getSubtotal()*tax_rate;
    }

    public double getTotal() {
        this.calculateTotal();
        return total;
    }
    
    public void setTotal(double tot) {
        total=tot;
    }
    
    public void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }
    
    
}
