/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import BeanModel.*;
import Utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author chiming
 */
public class CustomerDA {
    private CustomerDA(){}
    //----------------------------------
    public static final int CheckExist(UserLoginModel model) throws Exception{
        if(model==null)
        {return -1;}
        
        String sql = "Select count(*) from Users where email=?";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, model.getEmail());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int res = rs.getInt(1);
                return res;
            }else{
                return 0;
            }
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }    
    
    public static final UserBean Login(UserLoginModel model) throws Exception{
        if(model==null)
        {return null;}
        
        String sql = "Select nickname,uid from Users where email=? and pwd=?";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, model.getEmail());
            ps.setString(2, model.getPassword());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String nickname = rs.getString(1);
                int id= rs.getInt(2);
                return new UserBean(id,model.getEmail(),nickname);
            }else{
                return null;
            }
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
    
    public static final UserBean Register(UserRegisterModel model) throws Exception{
        if(model==null)
        {return null;}
        
        String sql = "insert into Users(email,pwd,nickname) values (?,?,?);";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, model.getEmail());
            ps.setString(2, model.getPassword());
            ps.setString(3, model.getNickname());
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                PreparedStatement ps2=con.prepareStatement("Select uid from Users where email=?");
                ps.setString(1, model.getEmail());
                ResultSet rs2=ps.executeQuery();
                if(rs2.next())
                {
                    int id= rs2.getInt(1);
                    return new UserBean(id, model.getEmail(),model.getNickname());
                }
                else
                {
                    return null;
                }
            }else{
                return null;
            }
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
    
}
