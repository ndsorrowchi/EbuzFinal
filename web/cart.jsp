<%-- 
    Document   : cart
    Created on : 2016-4-25, 9:56:02
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
        <title>Your Shopping Cart</title>
        <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
        <script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
        <script src="include/bootstrap/js/bootstrap.min.js"></script>
        <script src="include/handlelogin.js"></script>
        <script src="include/handlecart.js"></script>
         <%@include file="include/includestyle.txt" %>
    </head>
    <body>
        <%@include file="include/headerpiece.txt" %>
        <div class="container-fluid" style="margin-top: 50px;">
            <div class="row">
                <div class="col-sm-1"></div>
                <div class="col-sm-10">
                    <h3>View Cart</h3>
                    <table class="table table-hover" style="margin-bottom: 50px;">
                        <thead>
                            <tr>
                                <th>Book Image</th>
                                <th>Book Name</th>
                                <th>Quantity</th>
                                <th>Unit Price</th>
                                <th>Item Total</th>
                                <th>Action<th>
                            </tr>                            
                        </thead>
                        <tbody>
<% 
ShoppingCart sc=(ShoppingCart)session.getAttribute("cart");
if(sc.getItems().isEmpty())
{%>
                        <p id="txt-empty-cart" class="text-muted lead">Your cart is empty.</p>
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
        out.println("<td>");
        %>
            <div class="btn-group btn-group-xs">
                <button class="btn btn-default" onclick="decrement(this)"><span class="glyphicon glyphicon-minus"></span></button>
                <button class="btn btn-default" onclick="increment(this)"><span class="glyphicon glyphicon-plus"></span></button>
            </div>
            <button style="margin-left: 15px;" class="btn btn-danger btn-xs" onclick="removeItem(this)">Remove</button>
        <%
        out.println("</td>");
        out.println("</tr>");
    }
}
%>
                        </tbody>
                    </table>
                
                    <div style="margin-bottom: 30px;">
                        <button class="btn btn-primary" onclick="location.reload(true);">Refresh</button>

                        <button class="btn btn-danger" onclick="onClear()">Clear</button>

                        <button class="btn btn-success pull-right" onclick="onCheckout()">CheckOut</button>
                    </div>
                </div>
                <div class="col-sm-1"></div>
            </div>
        </div>
                      
        <%@include file="include/footerpiece.txt" %>
    </body>
</html>
