/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author chiming
 */
public class BookModel extends Object implements Serializable{
    private int bid;
    private String name;
    private int quantity;
    private String price;
    private String category;
    
    public BookModel(){bid=-1;name=null;quantity=-1;price=null;category=null;}
    public BookModel(int id, String title, int stock, String theprice, String thecategory)
    {bid=id;name=title;quantity=stock;price=theprice;category=thecategory;}
    
    public int getBid(){return bid;}
    public String getName(){return name;}
    public int getQuantity(){return quantity;}
    public String getPrice(){return price;}
    public String getCategory(){return category;}
    
    public void setBid(int id){bid=id;}
    public void setName(String title){name=title;}
    public void setQuantity(int stock){quantity=stock;}
    public void setPrice(String val){price=val;}
    public void setCategory(String type){category=type;}    


}
