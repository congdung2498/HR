$(document).ready(function () {
    if(Cookies.get('Cookie')==null){
        window.location.href = 'login.html';
    }
    if( Cookies.get('Cookie') != null && Cookies.get('Role') == "ROLE_USER" ){
        window.location.href = '/user_site/index.html';
    }
    $(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });

    $(document).ajaxError(function( event, jqxhr, settings, thrownError ) {
        console.log(jqxhr);
        if ( jqxhr.responseText == "Access Denied!" && jqxhr.status == 403 ) {
                   $.toaster({message: 'Bạn không có quyền thực hiện thao tác này ', title: ' Lỗi(403) Access Denied !!', priority: 'danger'});
               }
    });

      $("#news1").empty();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "/rest/getNotice",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                timeout: 600000,
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $("#news1").append("<div class=\"grid col-md-6 col-lg-4 mb-4 mb-lg-4\" style=\" padding: 0;padding-left: 7px;padding-right: 7px; margin:0\">"+
                                                                           " <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 99%;height: 230px;\" >"+
                                                                               " <img src=\"rest/getImg/"+data[i].img+"\"  alt=\"img12\"/>"+
                                                                               " <figcaption>"+
                                                                                    "<div>" +
                                                                                       " <h2>News</h2>"+
                                                                                   "     <p>"+data[i].title+"</p>"+
                                                                                  "  </div>"+
                                                                                   " <a href=\"Detail.html\" onclick='detail("+data[i].iD+")'>View more</a>"+
                                                                                "</figcaption>"+
                                                                          "  </figure>"+
                                                                           " <h2 class=\"font-size-regular\"  style=\"font-size: 20px;\"><a href=\"Detail.html\" onclick='detail("+data[i].iD+")'style=\"color: #0d0cb5;\">"+data[i].title+"</a></h2>"+
                                                                           " <div class=\"meta mb-1\"  style=\"color: #696666;\">"+data[i].author+"<span class=\"mx-2\">&bullet;</span>"+data[i].date+"</div>"+
                                                                           " <p id=\"summary\">"+data[i].summary+"</p>"+
                                                                         "   <p><a style=\"color: #0d0cb5;\" href=\"Detail.html\" onclick='detail("+data[i].iD+")'>Continue Reading...</a></p>"+
                                                                        "</div>")
                    }
                }
            });

});
function detail(id) {
    localStorage.setItem("id",id);
}

