/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function loadContent(){
    console.log($('input[name=category]:checked', '#left-form').val());
    console.log($('input[name=keywords]', '#left-form').val());    
    updateData();
}

function updateData()
{
    var category=$('input[name=category]:checked', '#left-form').val();
    var keywords=$('input[name=keywords]', '#left-form').val();
 
    var geturl="category="+encodeURIComponent(category)+"&keywords=%20"+encodeURIComponent(keywords);
    
    jQuery.ajax({
      type: "Get",
      url: "GetBooks?"+geturl,
      dataType: "html",
      success: function (response) {
        console.log(response);
        var real=JSON.parse(response);
        if(!real.hasOwnProperty('status'))//so did not fail at all
        {
            var count=real.list.length;
            if(count>0)
            {
                var table="<table id=\"table\" class=\"table table-hover\">\n" +
                "          <tr>\n" +
                "            <th>Book Image</th>\n" +
                "            <th>Book Name</th>\n" +
                "            <th>Quantity</th>\n" +
                "            <th>Unit Price</th>\n" +
                "            <th>Add to Cart</th>\n" +
                "          </tr>\n" +
                "        </table>";
                document.getElementById('search-result').innerHTML=table;
                
                for (var i = 1; i < count; i++) {
                    var row='<tr class="data">';
                    var raw="bookIMG/"+real.list[i].name+".jpg";
                    var res = raw.replace(/:/g, "-");
                    
                    var img='<td><img class="img-responsive img-mybox-sm" alt="book image" src="'+res.replace(/\s/g,"%20")+'"></img></td>';
                    row=row+img;
                    row=row+'<td>'+real.list[i].name+'</td>';
                    row=row+'<td>'+real.list[i].quantity+'</td>';
                    row=row+'<td>'+real.list[i].price+'</td>';
                    row=row+'<td><button class="btn btn-warning btn-xs" onclick="onAdd(this)" data-bid="'+real.list[i].bid+'">Add to Cart</button></td>\n</tr>';
                    $('#table').append(row);
                }
                $('#search-result').append('<div class="paging-container center-block" style="margin-top:20px;" id="tablePaging"> </div>');
                window.tp=null;
                window.tp = new Pagination('#tablePaging', {
                itemsCount: count,
                onPageSizeChange: function (ps) {
                        console.log('changed to ' + ps);
                },
                onPageChange: function (paging) {
                        //custom paging logic here
                        console.log(paging);
                        var start = paging.pageSize * (paging.currentPage - 1),
                                end = start + paging.pageSize,
                                $rows = $('#table').find('.data');

                        $rows.hide();

                        for (var i = start; i < end; i++) {
                                $rows.eq(i).show();
                        }
                }
        });
            }
            else
            {
                document.getElementById('search-result').innerHTML="Sorry. No match product found.";
            }
        }
      },
      error: function (xhr, ajaxOptions, thrownError) {
          
        console.log(xhr.status);
        console.log(thrownError);
        console.log(xhr.responseText);
      }
    });     
}

function onRefreshData()
{
//    alert($('input[name=category]:checked', '#left-form').val());
//    alert($('input[name=keywords]', '#left-form').val());
    updateData();
}



