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
public class UserBean extends Object implements Serializable{
    private int uid;
    private String email;
    private String nickname;
    
    public UserBean(){uid=-1;email=null;nickname=null;}
    public UserBean(int id, String eid, String name){uid=id;email=eid;nickname=name;}
    
    public int getUid(){return uid;}
    public String getEmail(){return email;}
    public String getNickname(){return nickname;}
    
    public void setUid(int id){uid=id;}
    public void setEmail(String eid){email=eid;} 
    public void setNickname(String name){nickname=name;}
}
