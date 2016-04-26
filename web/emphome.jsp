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
    <style type="text/css">
        .mylistbox{
            overflow-y: scroll;
            min-height: 300px;
            max-height: 500px;
            width: 100%;
            border:1px;
        }

        .mychatbox{
            overflow-y: scroll;
            min-height: 200px;
            max-height: 300px;
            width: 100%;
        }
        .special .list-group-item:first-child {
            border-top-right-radius: 0px !important;
            border-top-left-radius: 0px !important;
         }

         .special .list-group-item:last-child {
           border-bottom-right-radius: 0px !important;
           border-bottom-left-radius: 0px !important;
         }
         
         .online {
             color: #d9ffb3;
         }
         .offline{
             color:#ffffff;
         }
         .income-msg
         {
             background-color: #ddffaa;
         }
         .all-msg
         {
             min-width:30%; padding: 5px; margin:5px; box-shadow: 0px 1px 2px #C1C1C1
         }
    </style>             
    <%
        int eid=-1;
        String ename="Employee";
        if(session.getAttribute("emplbean")!=null)
        {
            EmplBean eb=(EmplBean)session.getAttribute("emplbean");
            if(eb.getNickname()!=null&&eb.getId()!=-1)
            {
                eid=eb.getId();
                ename=eb.getNickname();
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
<body onload="init()">
    <nav class="navbar navbar-inverse navbar-static-top" role="navigation" id="mynav" style="margin-bottom: 0px;">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" 
                    data-target="#navbar-collapse">
                <span class="sr-only">switch</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">Hail2Books</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right" style="margin-right: 10px;">
                <li><a href="#user-profile-wrapper"><span id="hide-id"  class="glyphicon glyphicon-user" style="margin-right:5px;" data-eid=<%="'"+String.valueOf(eid)+"'"%> data-ename=<%="'"+ename+"'"%>></span><jsp:getProperty name="emplbean" property="nickname"/></a></li>
                <li><a href="EmplHome"><span class="glyphicon glyphicon-home" style="margin-right:5px;"></span>Home</a></li>
                <li><a href="EmplLogout"><span class="glyphicon glyphicon-off" style="margin-right:5px;"></span>Logout</a></li>
            </ul>
        </div>
    </nav>
    <div class="container-fluid">
        <div class="row" style="margin-top:30px;">
            <div class="col-sm-4">
                <div class="list-group special" style="margin-bottom: 0px;">
                    <div class="list-group-item active">Pending Service Requests:</div>
                    <div class="list-group special mylistbox" id="friends">
                        
                    </div>                    
                </div>
            </div>
            <div class="col-sm-8">
                <div id="chat-box-all" style="display:none;">
                    <div class="panel panel-info">
                        <div class="panel-heading" id="chat-heading"></div>
                        <div class="panel-body" style="padding:0px;">
                            <div id="chat-wrapper" class="mychatbox">
                                
                            </div>
                            <hr style="border-color: #bce8f1; width: 100%; margin-top: 5px; margin-bottom: 5px;"/>
                            <div style="padding-bottom: 10px; margin-bottom: 5px;">
                                <form id="chat -form" onsubmit="return false" action="" role="form" style="padding-top: 0px;padding-left:10px; padding-right:10px; padding-bottom: 0px;">
                                    <div class="form-group">
                                        <label for="send-post">Write Message <small>(Max 300 Characters.)</small></label>
                                        <textarea class="form-control" rows="2" id="send-post"></textarea>
                                    </div>
                                    <button onclick="sendMessage()" class="btn btn-primary pull-right" style="margin-bottom:10px;">Send</button>
                                    <button class="btn btn-danger pull-left" style="margin-bottom: 10px;" onclick="terminate()">Terminate Chat</button>
                                </form>                                
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    
<script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
<script src="include/bootstrap/js/bootstrap.min.js"></script>
<script src="include/json2.js"></script>
<script src="include/handle-service.js"></script>
</body>
</html>
