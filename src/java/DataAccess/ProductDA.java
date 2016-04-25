/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import BeanModel.BookListModel;
import BeanModel.BookModel;
import java.util.ArrayList;
import Utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author chiming
 */
public class ProductDA {
    private ProductDA(){}
    //==================
    
    public static final BookListModel getRecommendation(int bid) throws Exception{
        if(bid==-1)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("recommendation",arr);
        }
        
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
            return new BookListModel("recommendation",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
    
    public static final BookListModel getTopSelling() throws Exception{
        
        //top selling 10 items
        String sql = "select book.BID,book.BOOKNAME,book.QUANTITY,book.PRICE,book.CATEGORY from\n" +
                        "book,(\n" +
                        "SELECT bid,sum(quantity) as totalsales FROM orders\n" +
                        "group by bid\n" +
                        "order by totalsales desc\n" +
                        "fetch first 10 rows only) as T\n" +
                        "where T.bid=book.bid";
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

    public static final BookListModel getSearchResult(String keywords) throws Exception{
        
        if(keywords==null)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("keyword-search",arr);
        }
        
        String splitted[]=keywords.split("\\s+");
        
        String head = "select book.BID,book.BOOKNAME,book.QUANTITY,book.PRICE,book.CATEGORY, Temp.relativity\n" +
                    "from book, (\n" +
                    "select T.bid, count (T.bid) as relativity from\n" +
                    "(\n"; 
                    
        String tail = ") T\n" +
                    "group by (T.bid)\n" +
                    "order by (relativity) desc) as Temp\n" +
                    "where Temp.bid=book.bid\n" +
                    "order by (Temp.relativity) desc";
        String sql="";
        StringBuilder sb = new StringBuilder();
        if(splitted.length>0)
        {
            for(int i=0;i<splitted.length;i++)
            {
                String temp = "select bid from BOOK\n" +
                                "where bookname like '%"+splitted[i]+"%'";
                if(i!=splitted.length-1)
                {
                    temp+="\n Union All \n ";
                }

                sb.append(temp);
            }
            sql=head+sb.toString()+tail;
        }
        else//no keywords means all
        {
            sql="select * from book";
        }


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
            return new BookListModel("keyword-search",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }  

    public static final BookListModel getSearchResult(String keywords, int rowsPerPage, int pageNumber) throws Exception{
        
        if(keywords==null||rowsPerPage<1||pageNumber<1)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("keyword-search",arr);
        }
        
        String splitted[]=keywords.split("\\s+");
        
        String head = "select book.BID,book.BOOKNAME,book.QUANTITY,book.PRICE,book.CATEGORY, Temp.relativity\n" +
                    "from book, (\n" +
                    "select T.bid, count (T.bid) as relativity from\n" +
                    "(\n"; 
                    
        String tail = ") T\n" +
                    "group by (T.bid)\n" +
                    "order by (relativity) desc) as Temp\n" +
                    "where Temp.bid=book.bid\n" +
                    "order by (Temp.relativity) desc";
        String sql="";
        StringBuilder sb = new StringBuilder();
        if(splitted.length>0)
        {
            for(int i=0;i<splitted.length;i++)
            {
                String temp = "select bid from BOOK\n" +
                                "where bookname like '%"+splitted[i]+"%'";
                if(i!=splitted.length-1)
                {
                    temp+="\n Union All \n ";
                }

                sb.append(temp);
            }
            sql=head+sb.toString()+tail;
        }
        else//no keywords means all
        {
            sql="select * from book";
        }
        
        String limitClause = "\n offset ? rows fetch first ? rows only";

        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql+limitClause);
            ps.setInt(1, pageNumber * rowsPerPage);
            ps.setInt(2, rowsPerPage);
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
            return new BookListModel("keyword-search",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }     
    
    public static final BookListModel getAll() throws Exception{

        String sql = "select * from book";
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
            return new BookListModel("all",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
    
    public static final BookListModel getAll(int rowsPerPage, int pageNumber) throws Exception{
        if(rowsPerPage<1||pageNumber<1)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("all",arr);
        }
        String sql = "select * from book";
        Connection con = DBUtils.getConnFromPool();
        String limitClause = "\n offset ? rows fetch first ? rows only";
        try{
            
            PreparedStatement ps = con.prepareStatement(sql+limitClause);
            ps.setInt(1, pageNumber * rowsPerPage);
            ps.setInt(2, rowsPerPage);
            
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
            return new BookListModel("all",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }       

    public static final BookListModel getFromCategory(String input_category) throws Exception{
        
        //top selling 6 items
        String sql = "select * from book";
        if(input_category!=null && !input_category.replaceAll("\\s+", "").equals(""))
        {
            sql = String.format("select * from book where category='%s'",input_category);
        }
        
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
            return new BookListModel("category",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    } 

    public static final BookListModel getFromCategory(String input_category, int rowsPerPage, int pageNumber) throws Exception{
        
        if(rowsPerPage<1||pageNumber<1)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("category",arr);
        }
        String sql = "select * from book";
        String limitClause = "\n offset ? rows fetch first ? rows only";
        if(input_category!=null && !input_category.replaceAll("\\s+", "").equals(""))
        {
            sql = String.format("select * from book where category='%s'",input_category);
        }
        
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql+limitClause);
            ps.setInt(1, pageNumber * rowsPerPage);
            ps.setInt(2, rowsPerPage);
            
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
            return new BookListModel("category",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }

    public static final BookListModel getComboSearchResult(String keywords, String category) throws Exception{
        
        if(keywords==null)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("complex-search",arr);
        }
        String categoryClause="";
        if(category!=null && !category.replaceAll("\\s+", "").equals(""))
        {
            categoryClause = String.format("category='%s'",category);
        }
        String splitted[]=keywords.split("\\s+");
        
        String head = "select book.BID,book.BOOKNAME,book.QUANTITY,book.PRICE,book.CATEGORY, Temp.relativity\n" +
                    "from book, (\n" +
                    "select T.bid, count (T.bid) as relativity from\n" +
                    "(\n"; 
                    
        String tail = ") T\n" +
                    "group by (T.bid)\n" +
                    "order by (relativity) desc) as Temp\n" +
                    "where Temp.bid=book.bid\n" +
                    "order by (Temp.relativity) desc";
        String sql="";
        StringBuilder sb = new StringBuilder();
        if(splitted.length>0)
        {
            for(int i=0;i<splitted.length;i++)
            {
                String temp = "select bid from BOOK\n" +
                                "where "+categoryClause+" AND bookname like '%"+splitted[i]+"%'";
                if(i!=splitted.length-1)
                {
                    temp+="\n Union All \n ";
                }

                sb.append(temp);
            }
            sql=head+sb.toString()+tail;
        }
        else//no keywords means all
        {
            sql="select * from book where "+categoryClause;
        }


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
                String _category = rs.getString(5);
                
                arr.add(new BookModel(bid,bname,quantity,bi.toPlainString(),_category));
            }
            return new BookListModel("complex-search",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }  

    public static final BookListModel getComboSearchResult(String keywords, String category, int rowsPerPage, int pageNumber) throws Exception{
        
        if(keywords==null||rowsPerPage<1||pageNumber<1)
        {
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            return new BookListModel("complex-search",arr);
        }
        
        String categoryClause="";
        if(category!=null && !category.replaceAll("\\s+", "").equals(""))
        {
            categoryClause = String.format("category='%s'",category);
        }
        String splitted[]=keywords.split("\\s+");
        
        String head = "select book.BID,book.BOOKNAME,book.QUANTITY,book.PRICE,book.CATEGORY, Temp.relativity\n" +
                    "from book, (\n" +
                    "select T.bid, count (T.bid) as relativity from\n" +
                    "(\n"; 
                    
        String tail = ") T\n" +
                    "group by (T.bid)\n" +
                    "order by (relativity) desc) as Temp\n" +
                    "where Temp.bid=book.bid\n" +
                    "order by (Temp.relativity) desc";
        String sql="";
        StringBuilder sb = new StringBuilder();
        if(splitted.length>0)
        {
            for(int i=0;i<splitted.length;i++)
            {
                String temp = "select bid from BOOK\n" +
                                "where "+categoryClause+" AND bookname like '%"+splitted[i]+"%'";
                if(i!=splitted.length-1)
                {
                    temp+="\n Union All \n ";
                }

                sb.append(temp);
            }
            sql=head+sb.toString()+tail;
        }
        else//no keywords means all
        {
            sql="select * from book where "+categoryClause;
        }
        
        String limitClause = "\n offset ? rows fetch first ? rows only";

        Connection con = DBUtils.getConnFromPool();
        
        try{
            
            PreparedStatement ps = con.prepareStatement(sql+limitClause);
            ps.setInt(1, pageNumber * rowsPerPage);
            ps.setInt(2, rowsPerPage);
            ResultSet rs = ps.executeQuery();
            ArrayList<BookModel> arr = new ArrayList<BookModel> ();
            while(rs.next()){
                int bid=rs.getInt(1);
                String bname=rs.getString(2);
                int quantity=rs.getInt(3);
                java.math.BigDecimal bi=rs.getBigDecimal(4);
                String _category = rs.getString(5);
                
                arr.add(new BookModel(bid,bname,quantity,bi.toPlainString(),_category));
            }
            return new BookListModel("complex-search",arr);
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }     
    
}
