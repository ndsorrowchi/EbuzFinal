<%-- 
    Document   : index
    Created on : 2016-4-25, 2:43:31
    Author     : chiming
--%>

<%@page import="DataAccess.ProductDA"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.UserBean"%>
<%@page language="java" import="java.sql.*,BeanModel.BookListModel,BeanModel.BookModel"%>
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
    BookListModel bl=ProductDA.getTopSelling();
%> 
<!DOCTYPE html>
<html>
<head>
    <title>Hail2Books Homepage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
    <script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
    <script src="include/bootstrap/js/bootstrap.min.js"></script>
    <script src="include/json2.js"></script>
    <script src="include/handlelogin.js"></script>

<%@include file="include/includestyle.txt" %>
</head>
<body>
<%@include file="include/headerpiece.txt" %>
    <div class="jumbotron">
        <div class="container text-center">
            <h1>Online Store</h1> 
            
        </div>
    </div>

    <div class="container-fluid">  
        <div>
            <h3>Top Sellers</h3>
        </div>        
        <div class="row">
            <% if(bl!=null){
                for(int i=0;i<bl.getList().size()&&i<4;i++){ 
                    BookModel bm=bl.getList().get(i);
                    String src=String.format("bookIMG/%s.jpg",bm.getName());
                    String href=String.format("itemdetail.jsp?bid=%d", bm.getBid());
            %>
            <div class="col-sm-3">
                <div class="panel panel-warning">
                    <div class="panel-heading"><a href=<%=href%>><%=bm.getName()%></a></div>
                    <div class="panel-body"><img class="img-responsive img-mybox center-block" alt="Book Image"  src=<%=src.replaceAll("\\s", "%20")%>></img></div>
                </div>
            </div>
            <%
                }
            }
            %>
        </div>
    </div><br><br>
<%@include file="include/footerpiece.txt" %>
</body>
</html>
