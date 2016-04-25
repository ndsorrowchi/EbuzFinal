<%-- 
    Document   : index
    Created on : 2016-4-25, 2:43:31
    Author     : chiming
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="java.sql.*,BeanModel.UserBean"%>

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
    <title>Hail2Books Homepage</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
    <script src="include/bootstrap/js/jquery-2.1.3.min.js"></script>
    <script src="include/bootstrap/js/bootstrap.min.js"></script>
    <script src="include/handlelogin.js"></script>
    <style>
        /* Remove the navbar's default rounded borders and increase the bottom margin */ 
        .navbar {
            margin-bottom: 0px;
            border-radius: 0;
        }
        /* Remove the jumbotron's default bottom margin */ 
        .jumbotron {
            margin-bottom: 10px;
        }
        /* Add a gray background color and some padding to the footer */
        footer {
            background-color: #f2f2f2;
            padding: 25px;
        }

        .modal-header, h4, .close {
            background-color: #5cb85c;
            color:white !important;
            text-align: center;
            font-size: 30px;
        }
        .modal-footer {
            background-color: #f9f9f9;
        }
    </style>

</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>                        
                </button>
                <a class="navbar-brand" href="#mynav">Hail2Books</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="index.jsp">HomePage</a></li>
                    <li><a href="category.jsp">Category</a></li>
                    <li><a href="#">FAQ</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a id="login-navbtn" data-toggle="modal" href="#myModal"><span class="glyphicon glyphicon-user"></span> Your Account</a></li>
                    <li><a href="cart.jsp"><span class="glyphicon glyphicon-shopping-cart"></span> Cart</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="jumbotron">
        <div class="container text-center">
            <h1>Online Store</h1> 
            <form class="form-inline">
            <input type="text" class="form-control" size="80" >
            <button type="button" class="btn btn-danger" >Search</button>
        </form>
        </div>
    </div>
    <div class="container">    
        <div class="row">
            <div class="col-sm-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">ItemName</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
                </div>
            </div>
            <div class="col-sm-4"> 
                <div class="panel panel-danger">
                    <div class="panel-heading">ItemName</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
                </div>
            </div>
            <div class="col-sm-4"> 
                <div class="panel panel-success">
                    <div class="panel-heading">ItemName</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
                </div>
            </div>
        </div>
    </div><br>

    <div class="container">    
        <div class="row">
            <div class="col-sm-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">ItemName</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
                </div>
            </div>
            <div class="col-sm-4"> 
                <div class="panel panel-primary">
                    <div class="panel-heading">ItemName</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
                </div>
            </div>
            <div class="col-sm-4"> 
                <div class="panel panel-primary">
                    <div class="panel-heading">ItemName</div>
                    <div class="panel-body"><img src="http://placehold.it/150x80?text=IMAGE" class="img-responsive" style="width:100%" alt="Image"></div>
                </div>
            </div>
        </div>
    </div><br><br>

    <footer class="container-fluid text-center">
        <p>Online Store Copyright</p>  
        <form class="form-inline">Get deals:
            <input type="email" class="form-control" size="50" placeholder="Email Address">
            <button type="button" class="btn btn-danger">Sign Up</button>
        </form>
    </footer>

    <!-- Modal , this is displayed by situation-->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">
        <% if(!hasuser) { %>
        <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header" style="padding:35px 50px;">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4><span class="glyphicon glyphicon-lock"></span> Login</h4>
                </div>
                <div class="modal-body" style="padding:20px 50px;">
                    <form role="form" action="javascript:void(0);" onSubmit="return false;" method="POST">
                      <div class="form-group">
                        <label for="usrname"><span class="glyphicon glyphicon-user"></span> Email</label>
                        <input type="email" class="form-control" id="username" placeholder="Enter email" required>
                      </div>
                      <div class="form-group">
                        <label for="psw"><span class="glyphicon glyphicon-lock"></span> Password</label>
                        <input type="password" class="form-control" id="password" placeholder="Enter password" required>
                      </div>
                        <button type="submit" class="btn btn-success btn-block"><span class="glyphicon glyphicon-off"></span> Login</button>
                    </form>
                    <br>
                    <p>Not a registered member? <a href="userregister.jsp">Sign Up</a></p>
                </div>
                <div id ="modal-footer" class="modal-footer">
                    <button type="submit" class="btn btn-default btn-default pull-right" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Close</button>
                </div>
            </div>
            <% } else { %>
            <div class="modal-content">
                <div class="modal-header" style="padding:35px 50px;">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4><span class="glyphicon glyphicon-lock"></span> <%=navbarGreeting%> </h4>
                </div>
                <div class="modal-body" style="padding:20px 50px;">
                    <div>
                        <p class="text-warning lead">You are signed in.</p>
                        <button class="btn btn-danger btn-block" ><span class="glyphicon glyphicon-off"></span> Sign Out</button>
                    </div>
                </div>
                <div id ="modal-footer" class="modal-footer">
                    <button type="submit" class="btn btn-default btn-default pull-right" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Close</button>
                </div>
            </div>                    
            <% } %>
        </div>
    </div>        
</body>
</html>
