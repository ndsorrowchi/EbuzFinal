/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import BeanModel.*;
import Utils.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author chiming
 */
public class EmplDAO{
    private EmplDAO(){}
    //==================
    
    public static final EmplBean Login(EmplLoginModel model) throws Exception{
        if(model==null)
        {return null;}
        
        String sql = "Select nickname,email from Employee where eid=? and password=?";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, model.getId());
            ps.setString(2, model.getPassword());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String nickname = rs.getString(1);
                return new EmplBean(model.getId(),model.getPassword(),nickname);
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