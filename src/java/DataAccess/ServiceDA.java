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
    
    public static final BookListModel getRecommendation(int bid) throws Exception{
        if(bid==-1)
        {return null;}
        
        //select most purchased item purchased by the users who has bought the current item
        String sql = "select book.bid,book.bookname,book.QUANTITY,book.PRICE,book.CATEGORY from book,\n" +
                        "(select O.bid, count(O.bid) as Relativity\n" +
                        "from Orders O, Book B, (select distinct uid from Orders where bid=?) V\n" +
                        "where O.uid=V.uid and O.bid!=? and B.bid=O.bid\n" +
                        "group by (O.bid)\n" +
                        "order by Relativity desc\n" +
                        "fetch first 1 rows only) as R\n" +
                        "where R.bid=book.bid";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bid);
            ps.setInt(2, bid);
            
            ResultSet rs = ps.executeQuery();
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            while(rs.next()){
                int id=rs.getInt(1);
                String bname=rs.getString(2);
                int quantity=rs.getInt(3);
                java.math.BigDecimal bi=rs.getBigDecimal(4);
                String category = rs.getString(5);
                
                arr.add(new BookModel(id,bname,quantity,bi.toPlainString(),category));
            }
            return new BookListModel("based-on-book",arr);
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
