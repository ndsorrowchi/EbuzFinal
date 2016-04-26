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
import java.util.ArrayList;
import java.util.Calendar;

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
        
        String sql = "insert into Users(email,pwd,nickname) values (?,?,?)";
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
    
    
    
    public static final short Buy(ShoppingCart model, int uid) throws Exception{
        if(model==null||uid==-1)
        {return -1;}
        
        long time=Calendar.getInstance().getTime().getTime();
        
        String sql = "insert into orders(uid,bid,timeulong,quantity) values (?,?,?,?)";
        String sql2 = "update book set book.quantity=book.quantity-? where book.bid=?";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps2 = con.prepareStatement(sql2);
            
            ps.setInt(1, uid);
            con.setAutoCommit(false);
            
            for(ShoppingCartItem ci : model.getItems())
            {
                ps.setInt(2, ci.getProduct().getBid());
                ps.setLong(3, time);
                ps.setInt(4, ci.getQuantity());
                
                ps2.setInt(1, ci.getQuantity());
                ps2.setInt(2, ci.getProduct().getBid());
                
                ps.executeUpdate();
                ps2.executeUpdate();
            }
            
            con.commit();
            return 1;
        }catch(Exception e)
        {
            throw e;//
        }
        finally{con.close();} 
    }
     
    public static final OrderListModel GetOrders(int uid) throws Exception
    {
        if(uid==-1)
        {return null;}
        
        String sql = "Select O.orderid, O.quantity, O.timeulong, B.bid, B.bookname, B.quantity, B.price, B.category from Book B, Orders O where O.uid=? and O.bid=B.bid order by (O.timeulong) desc";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            ArrayList<OrderModel> arr= new ArrayList<OrderModel>();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int oid=rs.getInt(1);
                int count=rs.getInt(2);
                long time=rs.getLong(3);
                int bid=rs.getInt(4);
                String name = rs.getString(5);
                int quantity=rs.getInt(6);
                java.math.BigDecimal bi=rs.getBigDecimal(7);
                String category = rs.getString(8);
                
                arr.add(new OrderModel(oid,new BookModel(bid,name,quantity,bi.toPlainString(),category),count,time));
            }
            return new OrderListModel(uid,arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();}        
    }
}
