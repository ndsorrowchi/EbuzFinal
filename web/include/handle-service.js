/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var friendstimer;
var chattimer=null;
var laststamp=0;
var lastfriendid=-1;

var itemclk=null;

function init()
{
    updatelist();
    friendstimer=setInterval('updatelist()',5000);
}

function geteid()
{
    var eidstr=document.getElementById("hide-id").getAttribute("data-eid");
    return eidstr;
}

function handleclick(elem)
{
   itemclk=elem; 
   startChat();
}

function updatelist()
{
    jQuery.ajax({
      type: "Get",
      url: "PendingList?eid="+geteid(),
      dataType: "text",
      success: function (response) {
            console.log(response);
            var JSONresp = JSON.parse(response);
            var listdom = document.getElementById("friends");
            while (listdom.hasChildNodes()) {   
                listdom.removeChild(listdom.firstChild);
            }
            var arr=JSONresp.list;
            for (var i = 0; i < arr.length; i++) {
                var user = arr[i];
                var temp=document.createElement("a");
                temp.setAttribute("href","#");
                temp.setAttribute("data-fid",""+user.uid);
                temp.setAttribute("data-fname",""+user.nickname);
                temp.setAttribute("onclick","handleclick(this);return true;");
                console.log(user);
                temp.textContent=user.nickname;
                
                temp.setAttribute("class","list-group-item");

                listdom.appendChild(temp);
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

function listenChat()
{
    var uidstr=geteid();
    var elem=document.getElementById("chat-wrapper");
    var eidstr=elem.getAttribute("data-fid");
    var fname=elem.getAttribute("data-fname");
    
    var reqobj={};    
    reqobj["uid"]=parseInt(eidstr);
    reqobj["eid"]=parseInt(uidstr);
    reqobj["direction"]=0;
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
            if(msg.direction==1)
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
    if(itemclk==null)
        return;
    var friendid=itemclk.getAttribute("data-fid");
    var friendname=itemclk.getAttribute('data-fname');
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

    var elem=document.getElementById("chat-wrapper");    
    var uidstr=elem.getAttribute("data-fid");
    var eidstr=geteid();
    var fname=elem.getAttribute("data-fname");
    
    var reqobj={};
    reqobj["uid"]=parseInt(uidstr);
    reqobj["eid"]=parseInt(eidstr);
    reqobj["direction"]=0;
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
    var elem=document.getElementById("chat-wrapper");
    var eidstr=geteid();
    var fname=elem.getAttribute("data-fname");
    var uidstr=elem.getAttribute("data-fid");
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
            // new chat should remove all
            while(temp.hasChildNodes())
            {
                temp.removeChild(temp.firstChild);
            } 
            document.getElementById("chat-box-all").setAttribute("style","display:none;");
            updatelist();
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

