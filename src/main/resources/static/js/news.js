$(document).ready(function () {
    $(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
    if(Cookies.get('Cookie')==null){
        window.location.href = 'login.html';
    }

    $(document).ajaxError(function( event, jqxhr, settings, thrownError ) {
        console.log(jqxhr);
        if ( jqxhr.responseText == "Access Denied!" && jqxhr.status == 403 ) {
                   $.toaster({message: 'Bạn không có quyền thực hiện thao tác này ', title: ' Lỗi(403) Access Denied !!', priority: 'danger'});
               }
    });
    var List=[];
    var Obj={};
    var Category=[];
    var Trend = [];
     // get manv option
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/rest/getCategory",
            dataType: 'json',
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            cache: false,
            timeout: 600000,
            data:{
                    search: "",
                    order: "asc",
                    offset : "0",
                    limit : "100000"
            },
            success: function (data) {
                Category = data.rows;
                for (var i = 0; i < data.rows.length; i++) {
                      $("#category").append("<li class=\"\"><a href=\"#section-linebox-"+data.rows[i].id+"\"><span>"+data.rows[i].name+"</span></a></li>");
                      $("#section-news").append("<section style='max-width: unset;' id=\"section-"+data.rows[i].id+"\">" +
                          "\n" +
                          "                                <div class=\"col-md-9 listnews-"+data.rows[i].id+"\">\n" +
                          "\n" +
                          "\n" +
                          "                                </div></section>");
                }

                  (function () {

                        [].slice.call(document.querySelectorAll('.tabs')).forEach(function (el) {
                            new CBPFWTabs(el);
                        });

                    })();
                GetData();
            }
        });
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/getTrend",
        dataType: 'json',
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            Trend = data;
        }
    });
    $("#tinTuc").addClass('active');
    function GetData(){
        $(".newsbody").empty();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/rest/getNews",
            dataType: 'json',
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            cache: false,
            timeout: 600000,
            success: function (data) {
                console.log(data);
                List = data;
                if (data.length>0){
                    $("#first").append("     <div style='padding: 0' class=\"col-md-12\">\n" +
                        "                                     <div class=\"grid col-md-12 mb-4 mb-lg-4\" style=\"padding:0 ;border-bottom:1px solid #6966661a; max-width: unset; padding: 0;padding-left: 7px;padding-right: 7px;\">\n" +
                        "                                    <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 100%;height: 300px;\" >\n" +
                        "                                        <img src=\"rest/getImg/"+data[0].img+"\" alt=\"img12\"/>\n" +
                        "                                        <figcaption>\n" +
                        "                                            <div>\n" +
                        "                                                <h2>"+data[0].category.name+"</h2>\n" +
                        "                                                <p>"+data[0].title+"</p>\n" +
                        "                                            </div>\n" +
                        "                                            <a onclick=\"detail(" + data[0].iD +")\" href=\"Detail.html\">View more</a>\n" +
                        "                                        </figcaption>\n" +
                        "                                    </figure>\n" +
                        "                                    <h2 class=\"font-size-regular\"  style=\"font-size: 20px;\"><a onclick=\"detail(" + data[0].iD +")\" href=\"Detail.html\" style=\"color: #0d0cb5;\">"+data[0].title+"</a></h2>\n" +
                        "                                    <div class=\"meta mb-1\"  style=\"color: #696666;\">"+data[0].author+"<span class=\"mx-2\">&bullet;</span> "+data[0].date+"</div>\n" +
                        "                                    <p>"+data[0].summary+"</p>\n" +
                        "                                    <p><a onclick=\"detail(" + data[0].iD +")\" style=\"color: #0d0cb5;\"href=\"Detail.html\">Continue Reading...</a></p>\n" +
                        "                                </div>\n" +
                        "                                </div>");

                    for (var i = 1; i < data.length; i++) {
                        $(".listnews").append("<div class=\"grid col-md-12 mb-4 mb-lg-4\" style=\" padding:0; border-bottom:1px solid #6966661a;border-right:1px solid #6966661a; text-align: unset; padding: 0;padding-left: 7px;padding-right: 7px;\">\n" +
                            "                                        <div style='padding:0; ' class=\" col-md-4\">\n" +
                            "                                            <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 99%;height: 230px;\" >\n" +
                            "                                                <img src=\"rest/getImg/"+data[i].img+"\" alt=\"img12\"/>\n" +
                            "                                                <figcaption>\n" +
                            "                                                    <div style='padding: 0.2em;'>\n" +
                            "                                                        <h2>"+data[i].category.name+"</h2>\n" +
                            "                                                        <p style='font-size: small;'>"+data[i].title+"</p>\n" +
                            "                                                    </div>\n" +
                            "                                                 <a onclick=\"detail(" + data[i].iD +")\" href=\"Detail.html\">View more</a>\n" +
                            "                                                </figcaption>\n" +
                            "                                            </figure>\n" +
                            "                                        </div>\n" +
                            "                                        <div class=\"col-md-8\">\n" +
                            "                                            <h2 class=\"font-size-regular\"  style=\"font-size: 20px;\"><a href=\"Detail.html\" onclick=\"detail(" + data[i].iD +")\" style=\"color: #0d0cb5;\">"+data[i].title+"</a></h2>\n" +
                            "                                            <div class=\"meta mb-1\"  style=\"color: #696666;\">"+data[i].author+"<span class=\"mx-2\">&bullet;</span>"+data[i].date+"</div>\n" +
                            "                                            <p>"+data[i].summary+"</p>\n" +
                            "                                             <p><a onclick=\"detail(" + data[i].iD +")\" style=\"color: #0d0cb5;\"href=\"Detail.html\">Continue Reading...</a></p>\n" +
                            "                                        </div>\n" +
                            "                                    </div>")
                    }
                    for(var i=0; i<data.length; i++){
                        var a = $("#section-news").find('#section-'+data[i].category.id).find('.listnews-'+data[i].category.id);
                        a.append("<div class=\"grid col-md-12 mb-4 mb-lg-4\" style=\" padding:0; border-bottom:1px solid #6966661a;border-right:1px solid #6966661a; text-align: unset; padding: 0;padding-left: 7px;padding-right: 7px;\">\n" +
                            "                                        <div style='padding:0; ' class=\" col-md-4\">\n" +
                            "                                            <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 99%;height: 230px;\" >\n" +
                            "                                                <img src=\"rest/getImg/"+data[i].img+"\" alt=\"img12\"/>\n" +
                            "                                                <figcaption>\n" +
                            "                                                    <div style='padding: 0.2em;'>\n" +
                            "                                                        <h2>"+data[i].category.name+"</h2>\n" +
                            "                                                        <p style='font-size: small;'>"+data[i].title+"</p>\n" +
                            "                                                    </div>\n" +
                            "                                                 <a onclick=\"detail(" + data[i].iD +")\" href=\"Detail.html\">View more</a>\n" +
                            "                                                </figcaption>\n" +
                            "                                            </figure>\n" +
                            "                                        </div>\n" +
                            "                                        <div class=\"col-md-8\">\n" +
                            "                                            <h2 class=\"font-size-regular\"  style=\"font-size: 20px;\"><a href=\"Detail.html\" onclick=\"detail(" + data[i].iD +")\" style=\"color: #0d0cb5;\">"+data[i].title+"</a></h2>\n" +
                            "                                            <div class=\"meta mb-1\"  style=\"color: #696666;\">"+data[i].author+"<span class=\"mx-2\">&bullet;</span>"+data[i].date+"</div>\n" +
                            "                                            <p>"+data[i].summary+"</p>\n" +
                            "                                             <p><a onclick=\"detail(" + data[i].iD +")\" style=\"color: #0d0cb5;\"href=\"Detail.html\">Continue Reading...</a></p>\n" +
                            "                                        </div>\n" +
                            "                                    </div>");

                    }
                    for(var i=0; i<Category.length; i++){
                        var a = $("#section-news").find('#section-'+Category[i].id);
                        a.append("<div class=\"col-md-3 trend text-center\"  id=\"category-"+Category[i].id+"\">\n" +
                            "                                </div>")
                        for(var j=0; j<Trend.length; j++){
                            var b =  $("#section-news").find('#section-'+Category[i].id).find('#category-'+Category[i].id)

                            b.append(
                                "                                    <div class=\"grid col-md-6 col-lg-4 mb-4 mb-lg-4\" style=\" padding: 0;padding-left: 7px;padding-right: 7px;\">\n" +
                                "                                        <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 99%;height: 230px;\">\n" +
                                "                                            <img src=\"rest/getImg/"+Trend[j].img+"\" alt=\"img12\">\n" +
                                "                                            <figcaption>\n" +
                                "                                                <div style='padding: 0.2em;'>\n" +
                                "                                                    <h2>Trend</h2>\n" +
                                "                                                    <p style='font-size: small;'>"+Trend[j].title+"</p>\n" +
                                "                                                </div>\n" +
                                "                                                <a onclick=\"detail(" + Trend[j].iD +")\" href=\"Detail.html\">View more</a>\n" +
                                "                                            </figcaption>\n" +
                                "                                        </figure>\n" +
                                "                                        <h2 class=\"font-size-regular\" style=\"font-size: 20px;\"><a  onclick=\"detail(" + Trend[j].iD +")\" href=\"Detail.html\" style=\"color: #0d0cb5;\" >"+Trend[j].title+"</a></h2>\n" +
                                "\n" +
                                "                                </div>")
                        }

                    }

                    for(var j=0; j<Trend.length; j++){
                        $("#trend").append(
                            "                                    <div class=\"grid col-md-6 col-lg-4 mb-4 mb-lg-4\" style=\" padding: 0;padding-left: 7px;padding-right: 7px;\">\n" +
                            "                                        <figure class=\"effect-lily\" style=\"margin: 0;margin-bottom: 30px;min-width: 99%;height: 230px;\">\n" +
                            "                                            <img src=\"rest/getImg/"+Trend[j].img+"\" alt=\"img12\">\n" +
                            "                                            <figcaption>\n" +
                            "                                                <div style='padding: 0.2em;'>\n" +
                            "                                                    <h2>Trend</h2>\n" +
                            "                                                    <p style='font-size: small;'>"+Trend[j].title+"</p>\n" +
                            "                                                </div>\n" +
                            "                                                <a  onclick=\"detail(" + Trend[j].iD +")\" href=\"Detail.html\">View more</a>\n" +
                            "                                            </figcaption>\n" +
                            "                                        </figure>\n" +
                            "                                        <h2 class=\"font-size-regular\" style=\"font-size: 20px;\"><a  onclick=\"detail(" + Trend[j].iD +")\" href=\"Detail.html\" style=\"color: #0d0cb5;\">"+Trend[j].title+"</a></h2>\n" +
                            "                                    </div>\n" +
                            "\n"
                           )
                    }
                }




            }
        });
    }




});
function detail(id) {
    localStorage.setItem("id",id);
}
