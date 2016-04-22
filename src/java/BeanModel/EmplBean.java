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
public class EmplBean extends Object implements Serializable{
    
    private int id;
    private String password;
    private String nickname;
    
    public EmplBean(){id=-1;password=null;nickname=null;}
    public EmplBean(int eid, String pwd, String name){id=eid;password=pwd;nickname=name;}
    
    public int getId(){return id;}
    public String getPassword(){return password;}
    public String getNickname(){return nickname;}
    
    public void setId(int eid){id=eid;}
    public void setPassword(String pwd){password=pwd;}  
    public void setNickname(String name){nickname=name;}
}
