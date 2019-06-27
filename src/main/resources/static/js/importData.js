if(Cookies.get('Cookie')==null) {
    window.location.href = 'login.html';
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
$("#submitButton1").click(function (event) {

    // Stop default form Submit.
    event.preventDefault();

    // Get form
    var form = $('#singleUploadForm1')[0];

    var data = new FormData(form);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        url: "/rest/upload/staff",
        data: data,

        // prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (data) {
            $("#singleFileUploadSuccess1").empty();
            $("#singleFileUploadSuccess1").append("<a href='/download1'>New Account</a>")
        },
        error: function (jqXHR) {

        }
    });
});


$("#submitButton2").click(function (event) {

    // Stop default form Submit.
    event.preventDefault();

    // Get form
    var form = $('#singleUploadForm2')[0];

    var data = new FormData(form);

    // Call Ajax Submit.
    ajaxSubmitForm(data, "/rest/upload/candidate");
});

$("#submitButton3").click(function (event) {
    // Stop default form Submit.
    event.preventDefault();

    // Get form
    var form = $('#singleUploadForm3')[0];

    var data = new FormData(form);

    // Call Ajax Submit.
    ajaxSubmitForm(data, "/rest/upload/salary");
});

function ajaxSubmitForm(data, url) {

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        url: url,
        data: data,

        // prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (data) {
            alert("thanh cong")
        },
        error: function (jqXHR) {

        }
    });
}
