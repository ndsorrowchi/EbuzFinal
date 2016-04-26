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
public class UserListModel extends Object implements Serializable {

    private ArrayList<UserBean> list;
    
    public UserListModel(){}
    public UserListModel(ArrayList<UserBean> userslist)
    {
        this.list=userslist;
    }
    
    public ArrayList<UserBean> getList(){return this.list;}
    public void setList(ArrayList<UserBean> userslist){this.list=userslist;}
}
