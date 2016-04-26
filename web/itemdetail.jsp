<%-- 
    Document   : itemdetail
    Created on : 2016-4-26, 1:29:50
    Author     : chiming
--%>

<%@page import="java.net.URLEncoder"%>
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
    if(request.getParameter("bid")==null)
    {response.setHeader("Refresh", "0; URL=index.jsp");}
    
    String bookid=request.getParameter("bid");
    int bid=Integer.parseInt(bookid);
    BookListModel bl=ProductDA.getRecommendation(bid);
    BookModel bm=ProductDA.getById(bid);
    
%> 
<!DOCTYPE html>
<html>
<head>
    <title>Details of <%=bm.getName()%></title>
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
    <div class="container-fluid">
        <div class="row" style="margin-top:50px;">
            <%
    if(bm!=null)
    {
        String imgsrc=String.format("bookIMG/%s.jpg",bm.getName());
        String raw2=imgsrc.replaceAll(":", "-");
        String encoded=raw2.replaceAll("\\s", "%20");
    %>
            <div class="col-sm-4">
                <img class="img-responsive img-mybox" alt="Book Image"  src=<%=encoded%> ></img>
                
            </div>
            <div class="col-sm-8">
                <h2><%=bm.getName()%></h2>
                <div>
                    <h5>A popular book which is worth reading.</h5>
                    <br>
                    <br>
                    Currently In Stock:<p class="text-muted"><%=bm.getQuantity()%></p>
                    <br>
                    Price:<p class="lead text-danger"><%="$"+bm.getPrice()%></p>
                </div>
                <button class="btn btn-warning" style="margin-top: 30px;" onclick="onAdd(this)" data-bid=<%=String.valueOf(bm.getBid())%>>Add to Cart</button>
            </div>
    <%
    }
    %>
        </div>
    </div>


    <div class="container-fluid">  
        <div>
            <h3>People bought this also bought:</h3>
        </div>        
        <div class="row">
            <% if(bm!=null&&bl!=null){
                for(int i=0;i<bl.getList().size()&&i<4;i++){ 
                    BookModel book=bl.getList().get(i);
                    String src=String.format("bookIMG/%s.jpg",book.getName());
                    String href=String.format("itemdetail.jsp?bid=%d", book.getBid());
            %>
            <div class="col-sm-3">
                <div class="panel panel-info">
                    <div class="panel-heading"><a href=<%=href%>><%=book.getName()%></a></div>
                    <div class="panel-body"><img class="img-responsive img-mybox center-block" alt="Book Image" src=<%=src.replaceAll("\\s", "%20")%> ></img></div>
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
