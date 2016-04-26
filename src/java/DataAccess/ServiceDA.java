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
import java.util.ArrayList;
/**
 *
 * @author chiming
 */
public class ServiceDA {
    private ServiceDA(){}
    //==================
    
    public static final FaqListModel getFaq() throws Exception{
        String str="";
        //select most purchased item purchased by the users who has bought the current item
        String sql = "select title,content from faq";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<FaqModel> arr = new ArrayList<FaqModel> ();
            while(rs.next()){
                String title=rs.getString(1);
                String content=rs.getString(2);
                
                arr.add(new FaqModel(title,content));
            }
            return new FaqListModel("faq-list",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
    
    public static final BookListModel getTopSelling() throws Exception{
        
        //select most purchased item purchased by the users who has bought the current item
        String sql = "";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            while(rs.next()){
                int bid=rs.getInt(1);
                String bname=rs.getString(2);
                int quantity=rs.getInt(3);
                java.math.BigDecimal bi=rs.getBigDecimal(4);
                String category = rs.getString(5);
                
                arr.add(new BookModel(bid,bname,quantity,bi.toPlainString(),category));
            }
            return new BookListModel("top-selling",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }       
}
