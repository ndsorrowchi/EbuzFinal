<%-- 
    Document   : UserRegister
    Created on : 2016-4-24, 23:26:39
    Author     : chiming
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link rel="stylesheet" href="include/bootstrap/css/bootstrap.min.css">
        <title>Customer Register</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
    </head>
    <body onload="initfield()">
        <h2>Dear Customer, Welcome to Hail2Books.com</h2>
        <div class="row" style="margin-top: 50px;">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
                <form role="form" action="UserRegister" method="post">
                    <div class="form-group">
                        <label for="register-username">Email Address</label>
                        <input id="register-username" onblur="checkit()" maxlength="45" type="email" name="email" class="form-control" required />
                        <div id="chkemail" style="display: inline"><span></span></div>
                    </div>
                    <div class="form-group">
                        <label for="register-nickname">Display Name</label>
                        <input id="register-nickname" type="text" name="nickname" maxlength="45" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label for="register-password">Password</label>
                        <input maxlength="20" id="register-password" type="password" name="password" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label for="confirm-password">Confirm Password</label>
                        <input maxlength="20" id="confirm-password" type="password" name="confirm-password" class="form-control" required />
                    </div>
                    <input id="submitbtn" type="submit" value="Sign up" class="btn btn-success pull-right"/>
                </form>               
            </div>
            <div class="col-sm-4"></div>
        </div>
        <script src="include/myscript.js"></script>
    </body>
</html>
