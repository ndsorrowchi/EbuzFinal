/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.io.Serializable;

/**
 *
 * @author chiming
 */
public class OrderModel extends Object implements Serializable{
    private int oid;
    private BookModel book;
    private int quantity;
    private long time;

    
    public OrderModel(){book=null;oid=-1;quantity=-1;time=0;}
    public OrderModel(int Oid, BookModel Book, int qty, long timestamp)
    {oid=Oid;book=Book;quantity=qty;time=timestamp;}
    
    public BookModel getBook(){return book;}
    public long getTime(){return time;}
    public int getQuantity(){return quantity;}
    
    public int getOid(){return oid;}
    
    public void setBook(BookModel Book){book=Book;}
    public void setTime(long timestamp){time=timestamp;}
    public void setQuantity(int qty){quantity=qty;}

    public void setOid(int Oid){oid=Oid;}          
}
