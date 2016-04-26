/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author chiming
 */
public class OrderListModel extends Object implements Serializable{
    private int uid;
    private ArrayList<OrderModel> list;
    public OrderListModel(){uid=-1;list=null;}
    public OrderListModel(int Uid, ArrayList<OrderModel> li){uid=Uid;list=li;}    
    
    public int getUid(){return uid;}
    public void setUid(int Uid){uid=Uid;}
    
    public ArrayList<OrderModel> getList(){return list;}
    public void setList(ArrayList<OrderModel> li){list=li;}
}
