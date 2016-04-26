<%-- 
    Document   : faq
    Created on : 2016-4-25, 22:50:53
    Author     : chiming
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.FaqModel,BeanModel.FaqListModel"%>
<%@page language="java" import="java.sql.*,BeanModel.UserBean"%>
<%@page language="java" import="java.math.RoundingMode"%>
<%@page language="java" import="java.math.BigDecimal"%>
<%@page language="java" import="DataAccess.ServiceDA"%>

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
    
    FaqListModel arr=ServiceDA.getFaq();
    ArrayList<FaqModel> list=arr.getList();
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
         <%@include file="include/includestyle.txt" %>
    </head>
    <body>
        <%@include file="include/headerpiece.txt" %>
        <div class="container-fluid" style="margin-top: 50px;">
            <div class="row">
                <div class="col-sm-1"></div>
                <div class="col-sm-10">
                    <h2>Frequently Asked Questions</h2>
                    <div class="panel-group">
                        <%
                            for(int i=0;i<list.size();i++)
                            {
                                int j=i+1;
                               String id="collapse"+j;
                               String id2="#"+id;
                               FaqModel m=list.get(i);
                        %>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                              <h3 class="panel-title">
                                  <a style="display: block;" data-toggle="collapse" href=<%= id2 %>><%=m.getTitle()%></a>
                              </h3>
                            </div>
                              <div class="panel-collapse collapse" id=<%= id %>>
                              <div class="panel-body"><%=m.getContent()%></div>
                              <div class="panel-footer"></div>
                            </div>
                        </div>
                        <% }%>
                    </div>                    
                </div>
                <div class="col-sm-1"></div>
            </div>
        </div>
                        
        <%@include file="include/footerpiece.txt" %>
    </body>
</html>


