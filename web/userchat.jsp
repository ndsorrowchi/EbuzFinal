<%-- 
    Document   : userchat
    Created on : 2016-4-26, 11:35:51
    Author     : chiming
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.OrderModel,BeanModel.OrderListModel"%>
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
        <title>Contact Service</title>
        <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
        <script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
        <script src="include/bootstrap/js/bootstrap.min.js"></script>
        <script src="include/json2.js"></script>
        <script src="include/handlelogin.js"></script>
        <script src="include/handle-user-chat.js"></script>
        
         <%@include file="include/includestyle.txt" %>
    <style type="text/css">
        .mylistbox{
            overflow-y: scroll;
            min-height: 300px;
            max-height: 500px;
            width: 100%;
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
    </head>
    <body>
        <%@include file="include/headerpiece.txt" %>
        <div class="container-fluid" style="margin-top: 50px;">
        <div class="row">
            <div class="col-sm-3">
                <div class="panel panel-default">
                    <div class="panel-heading">Service Representatives:</div>
                    <div class="panel-body">
                        <form id="left-form" role="form" action="javascript:void(0);" onSubmit="return false;" method="POST">
                            <h5>Chat with:</h5>
                            <div class="radio">
                                <label><input type="radio" name="eid" value="100000" data-fname="Evan" checked>Evan</label>
                            </div>
                            <div class="radio">
                              <label><input type="radio" name="eid" data-fname="Taylor" value="100001">Taylor</label>
                            </div>
                            <div class="radio">
                              <label><input type="radio" name="eid" data-fname="Coby" value="100002">Coby</label>
                            </div>                   
                        </form>
                        <button class="btn btn-info" style="margin-top: 0px;" onclick="startChat(this)">Start Chat</button> 
                    </div>
                </div>

            </div>
            <div class="col-sm-9">
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
                        
        <%@include file="include/footerpiece.txt" %>
    </body>
</html>

