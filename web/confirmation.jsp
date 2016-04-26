<%-- 
    Document   : confirmation
    Created on : 2016-4-25, 18:41:52
    Author     : chiming
--%>

<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.ShoppingCart,BeanModel.ShoppingCartItem"%>
<%@page language="java" import="java.sql.*,BeanModel.UserBean"%>
<%@page language="java" import="java.math.RoundingMode"%>
<%@page language="java" import="java.math.BigDecimal"%>

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
            ;
        }
        
    }
    else
    {
        //response.setHeader("Refresh", "1; URL=EmplLogin");
    }
    
    if(null==session.getAttribute("cart"))
    {
        ShoppingCart sc=new ShoppingCart();
        session.setAttribute("cart", sc);
    }
%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm Purchase</title>
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
                    <table class="table table-hover" style="margin-bottom: 50px;">
                        <h3>Confirm order</h3>
                        <thead>
                            <tr>
                                <th>Book Image</th>
                                <th>Book Name</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Item Total</th>
                            </tr>                            
                        </thead>
                        <tbody>
<% 
ShoppingCart sc=(ShoppingCart)session.getAttribute("cart");

if(sc.getItems().isEmpty())
{%>
                        <p class="text-muted lead">Your cart is empty.</p>
<%}else
{
    for(ShoppingCartItem ci : sc.getItems())
    {
        String bname=ci.getProduct().getName();
        int q=ci.getQuantity();
        String price=ci.getProduct().getPrice();
        double total=ci.getTotal();
        BigDecimal bi=new java.math.BigDecimal(total).setScale(2,RoundingMode.HALF_EVEN);
        
        out.println("<tr data-bid=\""+ci.getProduct().getBid()+"\">");
        String imgsrc=String.format("bookIMG/%s.jpg",ci.getProduct().getName());
        String raw2=imgsrc.replaceAll(":", "-");
        String encoded=raw2.replaceAll("\\s", "%20");        
        out.println(String.format("<td><img class=\"img-responsive img-mybox-sm\" src=\"%s\" alt=\"Book\"></img></td>", encoded));
        out.println(String.format("<td>%s</td>", bname));
        out.println(String.format("<td>%d</td>", q));
        out.println(String.format("<td>%s</td>", price));
        out.println(String.format("<td>%s</td>", bi.toPlainString()));
        out.println("</tr>");
    }
}
BigDecimal stot=new BigDecimal(sc.getSubtotal()).setScale(2,RoundingMode.HALF_EVEN);
BigDecimal stax=new BigDecimal(sc.getTaxes()).setScale(2,RoundingMode.HALF_EVEN);
BigDecimal tot=new BigDecimal(sc.getTotal()).setScale(2,RoundingMode.HALF_EVEN);
%>
                        </tbody>
                    </table>
                    <div class="row">
                        <div class="pull-right">
                            <p>Sub Total: <%= stot.toPlainString() %></p>
                            <p>Tax: <%= stax.toPlainString() %></p>
                            <p  class="lead text-danger">Total: <%= tot.toPlainString() %></p>                            
                        </div>
                    </div>


                    <div style="margin-bottom: 30px;">
                        <a class="btn btn-info" href="cart.jsp">Back To Cart</a>
                        <a class="btn btn-success" href="Checkout">Pay</a> 
                    </div>
                </div>
                <div class="col-sm-1"></div>
            </div>
        </div>
                        
        <%@include file="include/footerpiece.txt" %>
    </body>
</html>
