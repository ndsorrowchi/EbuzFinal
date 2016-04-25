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
    
    private String email;
    private String nickname;
    
    public UserBean(){email=null;nickname=null;}
    public UserBean(String eid, String name){email=eid;nickname=name;}
    
    public String getEmail(){return email;}
    public String getNickname(){return nickname;}
    
    public void setEmail(String eid){email=eid;} 
    public void setNickname(String name){nickname=name;}
}
