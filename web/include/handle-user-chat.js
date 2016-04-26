/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var chattimer=null;
var laststamp=0;
var lastfriendid=-1;


function listenChat()
{
    var uidstr=document.getElementById("user-greeting").getAttribute("data-uid");
    var elem=document.getElementById("chat-wrapper");
    var eidstr=elem.getAttribute("data-fid");
    var fname=elem.getAttribute("data-fname");
    
    var reqobj={};    
    reqobj["uid"]=parseInt(uidstr);
    reqobj["eid"]=parseInt(eidstr);
    reqobj["direction"]=1;
    reqobj["message"]=null;
    reqobj["time"]=laststamp;
    
    jQuery.ajax({
      type: "POST",
      url: "Chat",
      dataType: "text",
      data: JSON.stringify(reqobj),
      success: function (response) {
              
        var jsonobj=response;
        console.log(jsonobj);

        var JSONresp = JSON.parse(jsonobj);
        var arr=JSONresp.msglist;
        if(arr.length>0)
        {
            laststamp=arr[arr.length-1].time;
        }
        for (var i = 0; i < arr.length; i++) {
            var msg = arr[i];
            console.log(JSONresp);
            var tempbox=document.createElement("div");
            var temp=document.createElement("div");
            var dateline = document.createElement("p");
            var msgline = document.createElement("p");
            dateline.className="small text-muted";
            dateline.setAttribute("style","margin-bottom:3px;");
            msgline.setAttribute("style","margin-bottom:3px;");
            var x=new Date(msg.time);
            var classtxt="";
            var datestr="You (";
            classtxt="pull-right"
            if(msg.direction==0)
            {
                classtxt="pull-left income-msg";
                datestr=fname+" (";
            }
            datestr=datestr+x.toLocaleString('en-US')+"):";
            dateline.textContent=datestr;
            msgline.textContent=arr[i].message;
            classtxt="well all-msg "+classtxt;
            temp.className=classtxt;
            temp.setAttribute("style","margin: 2px 7px;")
            temp.appendChild(dateline);
            temp.appendChild(msgline);
            tempbox.appendChild(temp);
            tempbox.setAttribute("style","width:100%; clear:both;");
            elem.appendChild(tempbox);
        }            

      },
      error: function (xhr, ajaxOptions, thrownError) {

        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
        
        var JSONresp = JSON.parse(xhr.responseText);
        alert(JSONresp["error"]);
      }
    });    
}    


function startChat()
{
    var friendid=$('input[name=eid]:checked', '#left-form').val();
    var friendname=$('input[name=eid]:checked', '#left-form').attr('data-fname');
    //only do sth when starting a new chat
    if(friendid!=lastfriendid&&friendid!=null)
    {
        if(chattimer!=null)
        {
           clearInterval(chattimer);
           chattimer=null;     
        }       
        laststamp=0;
        document.getElementById("chat-box-all").setAttribute("style","display:block;");
        
        document.getElementById("chat-heading").textContent="Chat with "+friendname;
        var temp=document.getElementById("chat-wrapper");
        temp.setAttribute("data-fid",friendid);
        temp.setAttribute("data-fname",friendname);
        // new chat should remove all
        while(temp.hasChildNodes())
        {
            temp.removeChild(temp.firstChild);
        }       
        
        lastfriendid=parseInt(friendid);
        listenChat();
        chattimer=setInterval('listenChat()',3000);
        
    }
    
}

function sendMessage()
{
    var textarea=document.getElementById("send-post");
    var msg=textarea.value;
    
    if(msg.replace(/\s+/g,"")=="")//no white msg allowed
    {return;}
    if(msg.length>300)
    {alert("Message should not contain more than 300 characters (including white space).");return;}
    
    console.log("msg valid");
    var uidstr=document.getElementById("user-greeting").getAttribute("data-uid");
    var elem=document.getElementById("chat-wrapper");
    var eidstr=elem.getAttribute("data-fid");
    var fname=elem.getAttribute("data-fname");
    
    var reqobj={};
    reqobj["uid"]=parseInt(uidstr);
    reqobj["eid"]=parseInt(eidstr);
    reqobj["direction"]=1;
    reqobj["message"]=textarea.value.toString();
    reqobj["time"]=Date.now(); 
    
    jQuery.ajax({
      type: "POST",
      url: "Chat",
      dataType: "text",
      data: JSON.stringify(reqobj),
      success: function (response) {
              
            listenChat();
            textarea.value="";           

      },
      error: function (xhr, ajaxOptions, thrownError) {

        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
        
        var JSONresp = JSON.parse(xhr.responseText);
        alert(JSONresp["error"]);
      }
    });        
  

}


function terminate()
{
    var textarea=document.getElementById("send-post");
    var uidstr=document.getElementById("user-greeting").getAttribute("data-uid");
    var elem=document.getElementById("chat-wrapper");
    var eidstr=elem.getAttribute("data-fid");
    var fname=elem.getAttribute("data-fname");
    
    var reqobj={};
    reqobj["uid"]=parseInt(uidstr);
    reqobj["eid"]=parseInt(eidstr);
    reqobj["direction"]=-1;
    reqobj["message"]=textarea.value.toString();
    reqobj["time"]=0;  
    
    jQuery.ajax({
      type: "POST",
      url: "Chat",
      dataType: "text",
      data: JSON.stringify(reqobj),
      success: function (response) {
            laststamp=0;
            lastfriendid=-1;
            if(chattimer!=null)
            {
               clearInterval(chattimer);
               chattimer=null;     
            }             
            textarea.value="";           
            var temp=document.getElementById("chat-wrapper");
            temp.setAttribute("data-fid","");
            temp.setAttribute("data-fname","");
            document.getElementById("chat-box-all").setAttribute("style","display:none;");
      },
      error: function (xhr, ajaxOptions, thrownError) {

        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
        
        var JSONresp = JSON.parse(xhr.responseText);
        alert(JSONresp["error"]);
      }
    });        
}

