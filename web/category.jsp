<%-- 
    Document   : category
    Created on : 2016-4-26, 2:48:07
    Author     : chiming
--%>

<%@page import="DataAccess.ProductDA"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.UserBean"%>
<%@page language="java" import="java.sql.*,BeanModel.BookListModel,BeanModel.BookModel"%>
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
    
   
    
    
    
%> 
<!DOCTYPE html>
<html>
<head>
    <title>Details of</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
    <script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
    <script src="include/bootstrap/js/bootstrap.min.js"></script>
    <script src="include/handlelogin.js"></script>
    <script src="include/pagination.js"></script>
    <script src="include/dopaginate.js"></script>
<style>
.pagination.disabled a,  .pagination.disabled a:hover,  .pagination.disabled a:focus,  .pagination.disabled span {
  color: #eee;
  background: #fff;
  cursor: default;
}

.pagination { float: left; }

.pagination.disabled li.active a {
  color: #fff;
  background: #cccccc;
  border-color: #cccccc;
}

.paging-container select {
  float: left;
  margin: 20px 0 20px 10px;
  padding: 9px 3px;
  border-color: #ddd;
  border-radius: 4px;
}

#table { margin-bottom: 0; }
</style>
<%@include file="include/includestyle.txt" %>
</head>
<body onload="loadContent()">
<%@include file="include/headerpiece.txt" %>
    <div class="container-fluid">
        <div class="row" style="margin-top:50px;">
            <div class="col-sm-3">
                <div class="panel panel-default">
                    <div class="panel-heading">Search:</div>
                    <div class="panel-body">
                        <form id="left-form" role="form" action="javascript:void(0);" onSubmit="return false;" method="POST">
                            <h5>Search in:</h5>
                            <div class="radio">
                                <label><input type="radio" name="category" value="all" checked>All Category</label>
                            </div>
                            <div class="radio">
                              <label><input type="radio" name="category" value="humor & entertainment">Humor and Entertainment</label>
                            </div>
                            <div class="radio disabled">
                              <label><input type="radio" name="category" value="health">Health</label>
                            </div>
                            <div class="radio">
                              <label><input type="radio" name="category" value="computer & technology">Computer and Technology</label>
                            </div>
                            <div class="radio">
                              <label><input type="radio" name="category" value="history">History</label>
                            </div>
                            <div class="radio disabled">
                              <label><input type="radio" name="category" value="other">Other</label>
                            </div>
                            <h5>Keywords (use space to separate):</h5>
                            <div class="form-group">
                                <label><input class="form-control" type="text" name="keywords" value=""></label>
                            </div>                    
                        </form>
                        <button class="btn btn-warning" style="margin-top: 0px;" onclick="onRefreshData()">search</button>                        
                    </div>
                </div>

            </div>
            <div class="col-sm-9">
                <h5>Search Result</h5>
                <div id="search-result">
                    
                </div>
            </div>
    <%
    
    %>
        </div>
    </div>
    <br><br>
<%@include file="include/footerpiece.txt" %>
</body>
</html>

