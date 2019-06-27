
$(document).ready(function () {
    if(Cookies.get('Cookie')==null){
        window.location.href = '../login.html';
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
    var maNV = Cookies.get('UserName');
    function  GetData() {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/rest/getStaffInfoByMaNV",
            dataType: 'json',
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            cache: false,
            data: JSON.stringify({
                maNV: maNV,
            }),
            timeout: 600000,
            success: function (data) {
                $("#fileinput").prop("disabled", false);

                $("#edit-maNV").val(data.maNV);
                $("#edit-hovaten").val(data.hoVaTen);
                $("#edit-ngaysinh").val(data.ngaySinh);
                $("#edit-donvi").val(data.donVi);
                $("#edit-soDT").val(data.soDT);
                $("#edit-chucdanh").val(data.chucDanh);
                $("#edit-soCMND").val(data.soCMND);
                $("#edit-ngaycap").val(data.ngayCap);
                $("#edit-noicap").val(data.noiCap);
                $("#edit-sotaikhoan").val(data.soTaiKhoan);
                $("#edit-nganhanghuong").val(data.nganHangHuong);

                if (data.avata === null) {
                    $("#avata-staff").attr("src", "/images/avata.png");
                } else {
                    $("#avata-staff").attr("src", data.avata);
                }
            }
        });
    }
    GetData();
    $("#btn-update").click(function () {
        var maNV = $("#edit-maNV").val();
        var hovaten = $("#edit-hovaten").val();
        var ngaySinh = $("#edit-ngaysinh").val();
        var donVi = $("#edit-donvi").val();
        var soDienThoai = $("#edit-soDT").val();
        var chucDanh = $("#edit-chucdanh").val();
        var soCMND = $("#edit-soCMND").val();
        var ngayCap = $("#edit-ngaycap").val();
        var noiCap = $("#edit-noicap").val();
        var soTaiKhoan = $("#edit-sotaikhoan").val();
        var nganHangHuong = $("#edit-nganhanghuong").val();

        if (maNV.trim() == "") {
            $.toaster('Mã NV không được để trống', 'Thông báo', 'warning');
        } else if (hovaten.trim() == "") {
            $.toaster('Họ tên không được để trống', 'Thông báo', 'warning');
        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/rest/updateProfile",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                data: JSON.stringify({
                    maNV: maNV,
                    hoVaTen: hovaten,
                    ngaySinh: ngaySinh,
                    donVi: donVi,
                    soDT: soDienThoai,
                    chucDanh: chucDanh,
                    soCMND: soCMND,
                    ngayCap: ngayCap,
                    noiCap: noiCap,
                    soTaiKhoan: soTaiKhoan,
                    nganHangHuong: nganHangHuong
                }),
                timeout: 600000,
                success: function (data) {
                    if (data == true) {
                        $.toaster('Cập nhật thành công ', 'Thông báo', 'success');
                        GetData();
                    } else {
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }
                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                }
            });
        }
    })

});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var str_img = e.target.result;
            var maNV = document.getElementById("edit-maNV").value;

            $.ajax({
                type: "POST",
                url: "/rest/upload/user/avata",
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                contentType: 'application/json',
                data: JSON.stringify({
                    img: str_img,
                    maNV: maNV
                }),

                // prevent jQuery from automatically transforming the data into a query string
                timeout: 60000,
                cache: false,
                success: function (data) {
                    if(data == "false"){
                        $.toaster({message: 'Có lỗi xảy ra: Hãy thử lại với độ phân giải thấp hơn', title: 'Thất bại', priority: 'danger'});
                    }
                    else {
                        $.toaster('Upload thành công', 'Thông báo', 'success');
                        $("#avata-staff").attr("src",data);
                    }

                }
                ,
                error: function (jqXHR) {
                    $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                }
            });
        };
        reader.readAsDataURL(input.files[0]);
    }
}

