$(document).ready(function(){

        $("#noti").empty();
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
                List = data;
                for (var i = 0; i < data.length; i++) {
                    $("#noti").append("  <a class=\"dropdown-item media notice\" onclick='detail("+data[i].iD+")' style='border-bottom: 1px solid #f4f4f4;' href=\"Detail.html\">\n" +
                        "                                    <i class=\"fa fa-check\"></i>\n" +
                        "                                    <p style='display: none' id='notiID'>"+data[i].iD+"</p>\n" +
                        "                                    <p>"+data[i].title+"</p>\n" +
                        "                                </a>")
                }
            }
        });

    // $(".notice").click(function (){
    //     var newsID = $(this).find("#notiID").text();
    //     localStorage.setItem("id",newsID);
    //     window.location.href = 'Detail.html';
    // })
   $("#logout").click(function (){
       Cookies.remove('Cookie');
       Cookies.remove('Role');
       Cookies.remove('UserName');
       Cookies.remove('HoVaTen');
       Cookies.remove('UserID');
       window.location.href = '/';
   })
});
function detail(id) {
    localStorage.setItem("id",id);
}
