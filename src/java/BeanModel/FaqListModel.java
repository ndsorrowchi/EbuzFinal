/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;

import java.util.ArrayList;

/**
 *
 * @author chiming
 */
public class FaqListModel {
    private String type;
    private ArrayList<FaqModel> list;
    
    public FaqListModel(){type=null;list=null;}
    public FaqListModel(String name, ArrayList<FaqModel> li){type=name;list=li;}
    
    public String getType(){return type;}
    public ArrayList<FaqModel> getList(){return list;}
    
    public void settype(String str){type=str;}
    public void setList(ArrayList<FaqModel> li){list=li;}    
}
