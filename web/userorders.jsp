<%-- 
    Document   : userorders
    Created on : 2016-4-25, 21:54:10
    Author     : chiming
--%>

<%@page import="java.net.URLEncoder"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.OrderModel,BeanModel.OrderListModel"%>
<%@page language="java" import="java.sql.*,BeanModel.UserBean"%>
<%@page language="java" import="java.math.RoundingMode"%>
<%@page language="java" import="java.math.BigDecimal"%>
<%@page language="java" import="DataAccess.CustomerDA"%>

<%
    boolean hasuser=false;
    int uid=-1;
    String nickname="New Customer";
    String email="none";
    String navbarGreeting="Your Account";
    if(session.getAttribute("userbean")!=null)
    {
        UserBean ub=(UserBean)session.getAttribute("userbean");
        nickname=ub.getNickname();
        if(ub.getNickname()!=null&&ub.getEmail()!=null)
        {
            hasuser=true;
            navbarGreeting="Hi, "+ub.getNickname();
            nickname=ub.getNickname();
            email=ub.getEmail();
            uid=ub.getUid();
        }
        else
        {
            //response.setHeader("Refresh", "0; URL=index.jsp");
        }
        
    }
    else
    {
        //response.setHeader("Refresh", "1; URL=index.jsp");
    }
    
    if(!hasuser)
    {
        response.setHeader("Refresh", "0; URL=index.jsp");
    }
    
    
%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Order Details</title>
        <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
        <script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
        <script src="include/bootstrap/js/bootstrap.min.js"></script>
        <script src="include/json2.js"></script>
        <script src="include/handlelogin.js"></script>
         <%@include file="include/includestyle.txt" %>
    </head>
    <body>
        <%@include file="include/headerpiece.txt" %>
        <div class="container-fluid" style="margin-top: 50px;">
            <div class="row">
                <div class="col-sm-1"></div>
                <div class="col-sm-10">
                    <h3>Order History</h3>
                    <table class="table table-hover" style="margin-bottom: 50px;">
                        <thead>
                            <tr>
                                <th>Book Image</th>
                                <th>Book Name</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Item Total</th>
                                <th>Time and Date<th>
                            </tr>                            
                        </thead>
                        <tbody>
<% 
UserBean ub=(UserBean)session.getAttribute("userbean"); 
if(ub==null)
{
    ub=new UserBean();
}
OrderListModel sc=CustomerDA.GetOrders(ub.getUid());

if(sc==null || sc.getList().isEmpty())
{%>
                        <p class="text-muted lead">Your order history is empty.</p>
<%}else
{
    for(OrderModel ci : sc.getList())
    {
        String bname=ci.getBook().getName();
        int q=ci.getQuantity();
        String price=ci.getBook().getPrice();
        int number=ci.getQuantity();
        BigDecimal bi=new java.math.BigDecimal(price).setScale(2,RoundingMode.HALF_EVEN);
        double total=bi.doubleValue()*number*1.07;
        BigDecimal bi2=new java.math.BigDecimal(total).setScale(2,RoundingMode.HALF_EVEN);
        
        out.println("<tr data-bid=\""+ci.getBook().getBid()+"\">");
        String imgsrc=String.format("bookIMG/%s.jpg",ci.getBook().getName());
        String raw2=imgsrc.replaceAll(":", "-");
        String encoded=raw2.replaceAll("\\s", "%20");
        out.println(String.format("<td><img class=\"img-responsive img-mybox-sm\" src=\"%s\" alt=\"Book\"></img></td>", encoded));
        out.println(String.format("<td>%s</td>", bname));
        out.println(String.format("<td>%d</td>", q));
        out.println(String.format("<td>%s</td>", price));
        out.println(String.format("<td>%s</td>", bi2.toPlainString()));
        
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(ci.getTime());
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out.println(String.format("<td>%s</td>", df.format(c.getTime())));
        out.println("</tr>");
    }
}

%>
                        </tbody>
                    </table>
                    
                </div>
                <div class="col-sm-1"></div>
            </div>
        </div>
                        
        <%@include file="include/footerpiece.txt" %>
    </body>
</html>

