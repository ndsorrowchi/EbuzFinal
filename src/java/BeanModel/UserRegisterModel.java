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
public class UserRegisterModel{
    
    private String email;
    private String password;
    private String nickname;
    
    public UserRegisterModel(){email=null;password=null;nickname=null;}
    public UserRegisterModel(String emailaddr, String pwd, String name){email=emailaddr;password=pwd; nickname=name;}
    
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getNickname(){return nickname;}
    
    public void setEmail(String emailaddr){email=emailaddr;}
    public void setPassword(String pwd){password=pwd;}
    public void setNickname(String name){nickname=name;}
}
