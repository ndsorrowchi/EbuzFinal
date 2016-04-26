/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function dologin()
{
    
    jQuery.ajax({
      type: "Get",
      url: "UserLogin?email=" + encodeURIComponent(document.getElementById("username").value)+"&password="+encodeURIComponent(document.getElementById("password").value),
      dataType: "html",
      success: function (response) {
              
           var jsonobj=response;
           console.log(jsonobj);
           
          var obj=JSON.parse(jsonobj);
          
          if(obj.status=="fail")
          {
                        alert("Cannot Sign in. Please check your input.");
              return;
          }
           
           var userid=obj.userid;
           var nickname=obj.nickname;
           var dialog=document.getElementById("myModal").children[0];
           var greeting=document.getElementById("user-greeting");
           greeting.innerHTML="Hi, "+nickname;
           greeting.setAttribute("data-uid",""+userid);
           var content="<div class=\"modal-content\">\n" +
                "<div class=\"modal-header\" style=\"padding:35px 50px;\">\n" +
                "    <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
                "    <h4><span class=\"glyphicon glyphicon-lock\"></span> Hi, "+nickname+" </h4>\n" +
                "</div>\n" +
                "<div class=\"modal-body\" style=\"padding:20px 50px;\">\n" +
                "    <div>\n" +
                "        <p class=\"text-warning lead\">You are signed in.</p>\n" +
                "        <a class=\"btn btn-danger\" href=\"UserLogout\"><span class=\"glyphicon glyphicon-off\"></span> Sign Out</a>\n" +
                "     </div>\n" +
                " </div>\n" +
                "<div id =\"modal-footer\" class=\"modal-footer\">\n" +
                "    <button type=\"submit\" class=\"btn btn-default btn-default pull-right\" data-dismiss=\"modal\"><span class=\"glyphicon glyphicon-remove\"></span> Close</button>\n" +
                "</div>\n" +
            "</div>  ";
            dialog.innerHTML=content;
      },
      error: function (xhr, ajaxOptions, thrownError) {

        console.log("login fail");
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
        
        var err=JSON.parse(xhr.responseText);
        
        alert(err.message);
      }
    });    
}


function onAdd(btn){
    
    var id=btn.getAttribute("data-bid");
    
    jQuery.ajax({
      type: "POST",
      url: "CartController",
      dataType: "html",
      data: "command=addtocart&bid=" + id,
      success: function (response) {
        console.log(response);
        var real=JSON.parse(response);
        if(real.status=="success")
        {
            //alert("Add to your cart");
            btn.innerHTML='<span class="glyphicon glyphicon-ok"></span> Added to cart';
        }
      },
      error: function (xhr, ajaxOptions, thrownError) {
          
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
      }
    });    
}