<%-- 
    Document   : emplhome
    Created on : 2016-4-22, 14:40:17
    Author     : chiming
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.EmplBean"%>
<jsp:useBean id="emplbean" class="BeanModel.EmplBean" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Portal</title>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="include/bootstrap/css/bootstrap.min.css"/>
    <%
        if(session.getAttribute("emplbean")!=null)
        {
            EmplBean eb=(EmplBean)session.getAttribute("emplbean");
            if(eb.getNickname()!=null&&eb.getId()!=-1)
            {
                ;
            }
            else
            {
                response.setHeader("Refresh", "1; URL=EmplLogin");
            }
        }
        else
        {
            response.setHeader("Refresh", "1; URL=EmplLogin");
        }
    %>  
</head>
<body>
    <nav class="navbar navbar-inverse navbar-static-top" role="navigation" id="mynav" style="margin-bottom: 0px;">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" 
                    data-target="#navbar-collapse">
                <span class="sr-only">switch</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#mynav">Hail2Books</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right" style="margin-right: 10px;">
                <li><a href="#user-profile-wrapper"><span class="glyphicon glyphicon-user" style="margin-right:5px;"></span><jsp:getProperty name="emplbean" property="nickname"/></a></li>
                <li><a href="emphome"><span class="glyphicon glyphicon-home" style="margin-right:5px;"></span>Home</a></li>
                <li><a href="EmplLogout"><span class="glyphicon glyphicon-off" style="margin-right:5px;"></span>Logout</a></li>
            </ul>
        </div>
    </nav>
    <div style="width:100%;">
        <div class="container">
            <div class="text-center">
                <h1 style="color: #23527c"><big>Employee Login</big></h1>
                <h4 style="color: #23527c"><big></big></h4>
            </div>    
            <div class="row">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                        <form role="form" action="Login" method="post">
                            <div class="form-group">
                                <label for="login-username">Employee ID:</label>
                                <input id="login-username" type="email" name="username" maxlength="40" class="form-control" required />
                            </div>
                            <div class="form-group">
                                <label for="login-password">Password</label>
                                <input id="login-password" type="password" name="password" maxlength="20" class="form-control" required />
                            </div>
                            <input type="submit" value="sign in" class="btn btn-primary pull-right"/>
                        </form>
                </div>
                <div class="col-sm-4"></div>
            </div>
        </div>
            
    </div>
    
<script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
<script src="include/bootstrap/js/bootstrap.min.js"></script>
<script src="include/json2.js"></script>
</body>
</html>
