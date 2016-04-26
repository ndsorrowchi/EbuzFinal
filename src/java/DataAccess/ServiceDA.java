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
    
    public static final String post(MessageModel mm) throws Exception{
        if(mm==null)
            return null;
        if(mm.getMessage()==null || mm.getMessage().replaceAll("\\s+", "").equals(""))// should not be a blank
            return null;        
        //select most purchased item purchased by the users who has bought the current item
        String sql = "";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            
        PreparedStatement ps=con.prepareStatement("select count(*) from(\n" +
            "select uid from users where users.uid=?\n" +
            "union all\n" +
            "select eid from employee where employee.eid=?) Temp");
        ps.setInt(1, mm.getUid());
        ps.setInt(2, mm.getEid());
        ResultSet rs=ps.executeQuery();
        if(rs.next()&&rs.getInt(1)==2)//make sure two users exist in db.
        {
            sql="insert into chat (uid, eid, direction, content, timeulong) values(?,?,?,?,?)";

            ps=con.prepareStatement(sql);
            ps.setInt(1, mm.getUid());
            ps.setInt(2, mm.getEid());
            ps.setInt(3, mm.getDirection());
            ps.setString(4, mm.getMessage());
            ps.setLong(5, mm.getTime());

            ps.executeUpdate();

            return "{\"success\":\"post successfully submitted\"}";
        }
        else
        {return null;}
        
        }
        catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
    
    public static final MessageListModel update(MessageModel mm) throws Exception{
        if(mm==null)
            return null;        
        //select most purchased item purchased by the users who has bought the current item
        String sql = "";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            sql="select uid, eid, direction, content, timeulong from chat where (uid=? and eid=?) and (timeulong>?) order by timeulong asc";
            PreparedStatement ps=con.prepareStatement("select count(*) from(\n" +
            "select uid from users where users.uid=?\n" +
            "union all\n" +
            "select eid from employee where employee.eid=?) Temp");
            ps.setInt(1, mm.getUid());
            ps.setInt(2, mm.getEid());
            ResultSet rs=ps.executeQuery();
            if(rs.next()&&rs.getInt(1)==2)//make sure two users exist in db.
            {
                ps=con.prepareStatement(sql);
                ps.setInt(1, mm.getUid());
                ps.setInt(2, mm.getEid());
                ps.setLong(3, mm.getTime());
                rs=ps.executeQuery();

                ArrayList<MessageModel> list=new ArrayList<>();

                while(rs.next())
                {
                    MessageModel temp=new MessageModel(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getLong(5));
                    list.add(temp);
                }
                return new MessageListModel(list);
            }
            else
                return null;
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
        
    public static final String terminate(MessageModel mm) throws Exception{
        if(mm==null)
            return null;        
        //select most purchased item purchased by the users who has bought the current item
        String sql = "";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            sql="delete from chat where uid=? and eid=?";
            PreparedStatement ps=con.prepareStatement("select count(*) from(\n" +
                "select uid from users where users.uid=?\n" +
                "union all\n" +
                "select eid from employee where employee.eid=?) Temp");
            ps.setInt(1, mm.getUid());
            ps.setInt(2, mm.getEid());
            ResultSet rs=ps.executeQuery();
            if(rs.next()&&rs.getInt(1)==2)//make sure two users exist in db.
            {
                ps=con.prepareStatement(sql);
                ps.setInt(1, mm.getUid());
                ps.setInt(2, mm.getEid());

                ps.executeUpdate();
                return "{\"success\":\"chat successfully terminated\"}";
            }
            else
                return null;
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }

            
    public static final UserListModel getPendingList(int eid) throws Exception{
        if(eid<0)
            return null;        
        //select most purchased item purchased by the users who has bought the current item
        String sql = "";
        Connection con = DBUtils.getConnFromPool();
        
        try{
            sql="select users.uid,users.email, users.nickname from users, chat where eid=? and chat.uid=users.uid order by (chat.timeulong) asc fetch first 1 rows only";
            PreparedStatement ps=con.prepareStatement("select count(eid) from employee where employee.eid=?");
            ps.setInt(1, eid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()&&rs.getInt(1)==1)//make sure two users exist in db.
            {
                ps=con.prepareStatement(sql);
                ps.setInt(1, eid);

               ResultSet rs2 = ps.executeQuery();
                
               ArrayList<UserBean> list=new ArrayList<>();

                while(rs2.next())
                {
                    UserBean temp=new UserBean(rs2.getInt(1),rs2.getString(2),rs2.getString(3));
                    list.add(temp);
                }
                return new UserListModel(list);
            }
            else
                return null;
        }catch(Exception e)
        {
            throw e;
        }
        finally{con.close();} 
    }
}
