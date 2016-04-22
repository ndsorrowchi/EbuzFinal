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
public class EmplLoginModel{
    
    private int id;
    private String password;
    
    public EmplLoginModel(){id=-1;password=null;}
    public EmplLoginModel(int eid, String pwd){id=eid;password=pwd;}
    
    public int getId(){return id;}
    public String getPassword(){return password;}
    
    public void setId(int eid){id=eid;}
    public void setPassword(String pwd){password=pwd;}
}
