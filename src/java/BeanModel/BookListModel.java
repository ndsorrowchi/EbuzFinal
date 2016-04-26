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
public class BookListModel  extends Object implements Serializable{
    private String type;
    private ArrayList<BookModel> list;
    
    public BookListModel(){type=null;list=null;}
    public BookListModel(String name, ArrayList<BookModel> li){type=name;list=li;}
    
    public String getType(){return type;}
    public ArrayList<BookModel> getList(){return list;}
    
    public void setType(String str){type=str;}
    public void setList(ArrayList<BookModel> li){list=li;}
    
    
}
