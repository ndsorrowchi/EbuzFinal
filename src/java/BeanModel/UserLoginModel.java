/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeanModel;


/**
 *
 * @author chiming
 */
public class UserLoginModel{
    
    private String email;
    private String password;
    
    public UserLoginModel(){email=null;password=null;}
    public UserLoginModel(String emailaddr, String pwd){email=emailaddr;password=pwd;}
    
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    
    public void setEmail(String emailaddr){email=emailaddr;}
    public void setPassword(String pwd){password=pwd;}
}
