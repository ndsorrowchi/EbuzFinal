/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function decrement(btn){
    var div=btn.parentElement;
    var td=div.parentElement;
    var tr=td.parentElement;
    if(tr==null)
        return;
    var id=tr.getAttribute("data-bid");
    
    jQuery.ajax({
      type: "POST",
      url: "CartController",
      dataType: "html",
      data: "command=decrement&bid=" + id,
      success: function (response) {
        console.log(response);
        var real=JSON.parse(response);
        if(real.status=="success")
        {
            var quantity=tr.children[2].textContent;
            var price=tr.children[3].textContent;
            var total=tr.children[4];
            
            var qty=parseInt(quantity)-1;
            var ppu=parseFloat(price);
            tr.children[2].textContent=qty.toString();
            var tot=qty*ppu;
            
            total.textContent=tot.toFixed(2);
            
        }
      },
      error: function (xhr, ajaxOptions, thrownError) {
          
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
      }
    });
}

function increment(btn){
    var div=btn.parentElement;
    var td=div.parentElement;
    var tr=td.parentElement;
    if(tr==null)
        return;
    var id=tr.getAttribute("data-bid");
    
    jQuery.ajax({
      type: "POST",
      url: "CartController",
      dataType: "html",
      data: "command=increment&bid=" + id,
      success: function (response) {
        console.log(response);
        var real=JSON.parse(response);
        if(real.status=="success")
        {
            var quantity=tr.children[2].textContent;
            var price=tr.children[3].textContent;
            var total=tr.children[4];
            
            var qty=parseInt(quantity)+1;
            var ppu=parseFloat(price);
            tr.children[2].textContent=qty.toString();
            var tot=qty*ppu;
            
            total.textContent=tot.toFixed(2);
            
        }
      },
      error: function (xhr, ajaxOptions, thrownError) {
          
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
      }
    });    
}

function removeItem(btn){
    
    var td=btn.parentElement;;
    var tr=td.parentElement;
    if(tr==null)
        return;
    var id=tr.getAttribute("data-bid");
    
    jQuery.ajax({
      type: "POST",
      url: "CartController",
      dataType: "html",
      data: "command=remove&bid=" + id,
      success: function (response) {
        console.log(response);
        var real=JSON.parse(response);
        if(real.status=="success")
        {
            var tbody=tr.parentElement;
            tbody.removeChild(tr);
            
        }
      },
      error: function (xhr, ajaxOptions, thrownError) {
          
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
      }
    });    
}

function onCheckout()
{
    var span=document.getElementById("user-greeting");
    if(span.getAttribute("data-uid")!="-1")
    {
        window.location.href = "confirmation.jsp";
    }
    else if(null!=document.getElementById("txt-empty-cart"))
    {
        alert("No items in cart");
    }
    else
    {
        alert("please login first then check out");
    }
}

function onClear(){
    
    
    
    jQuery.ajax({
      type: "POST",
      url: "CartController",
      dataType: "html",
      data: "command=clear",
      success: function (response) {
        console.log(response);
        var real=JSON.parse(response);
        if(real.status=="success")
        {
            location.reload(true);
        }
      },
      error: function (xhr, ajaxOptions, thrownError) {
          
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
      }
    });    
}
