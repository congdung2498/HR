$(document).ready(function () {
    if(Cookies.get('Cookie')==null){
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
    $("#btn-xem").click(function () {
        var maNV = $("#select-manv").val();
        var thang = $("#select-thang").val();
        if (maNV == null || thang == null) {
            $.toaster('Bạn chưa nhập đầy đủ thông tin', 'Thông báo', 'warning');
        } else
            $.ajax({
                type: "POST",
                contentType: "application/json",
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                url: "/rest/user/getSalaryByMaCvMonth",
                dataType: 'json',
                cache: false,
                data: JSON.stringify({
                    thang: thang,
                    maNV: maNV
                }),
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    $("#txt-thang1").html(data.salary.thang);
                    $("#txt-thang2").html(data.salary.thang);
                    $("#txt-hoten").html(data.salary.hoVaTen);
                    $("#txt-ngayCongCheDo").html(data.salary.congCheDo);
                    $("#txt-ngayConglamThucTe").html(data.salary.congThucTe);
                    $("#txt-ngayNghiLe").html(data.salary.ngayNghiLePhep);
                    $("#txt-KI").html(data.salary.ki);
                    $("#txt-tongThuNhap").html(formatNumber(data.salary.tongThuNhap));
                    $("#txt-luongChucDanh").html(formatNumber(data.salary.luongChucDanh));
                    $("#txt-luongThamNien").html(formatNumber(data.salary.luongThamNien));
                    $("#txt-luongLePhep").html(formatNumber(data.salary.luongLePhep));
                    $("#txt-dieuChinhBoSung").html(formatNumber(data.salary.dieuChinhBoSung));
                    $("#txt-phuCapDoanThe").html(formatNumber(data.salary.phuCapDoanThe));
                    $("#txt-phuCapAnCa").html(formatNumber(data.salary.phuCapAnCa));
                    $("#txt-phuCapDienThoai").html(formatNumber(data.salary.phuCapDienThoai));
                    $("#txt-boiDuongTruc").html(formatNumber(data.salary.boiDuongTruc));
                    $("#txt-cacKhoanGiamTru").val(formatNumber(data.salary.tongKhauTru));
                    $("#txt-BHXH").html(formatNumber(data.salary.bhxh));
                    $("#txt-BHYT").html(formatNumber(data.salary.bhyt));
                    $("#txt-TNCN").html(formatNumber(data.salary.thueTNCN));
                    $("#txt-BHTN").html(formatNumber(data.salary.bhtn));
                    $("#txt-kinhPhiCongDoan").html(formatNumber(data.salary.kinhPhiCongDoan));
                    $("#txt-soTienChuyenKhoan").html(formatNumber(data.salary.soTienChuyenKhoan));
                    $("#txt-SoTaiKhoanNhanTien").html(data.soTaiKhoan);
                    $("#txt-nganHangHuong").html(data.nganHangHuong);
                }
            });
    })


    // get manv option
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/getStaffInfo",
        dataType: 'json',
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                $('#select-manv').append($('<option>', {
                    value: data[i].maNV,
                    text: data[i].hoVaTen
                }));
            }
            $("#select-manv").select2( {
                placeholder: "Chọn nhân viên",
                allowClear: true
            } );
            var manv = $("#select-manv").val();
                var url = "rest/getAllMonthByMaNV?maNV=" + manv;
            $.ajax({
                type: "GET",
                contentType: "application/json",
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                url: url,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    $("#select-thang").empty();
                    for (var i = 0; i < data.length; i++) {
                        $("#select-thang").append(new Option(data[i], data[i]));
                    }
                    $("#select-thang").select2( {
                        placeholder: "Chọn tháng",
                        allowClear: true
                    } );
                }
            });
        }
    });

    $("#select-manv").change(function (ev) {
        var manv = $("#select-manv").val();
        var url = "/rest/getAllMonthByMaNV?maNV=" + manv;
        $.ajax({
            type: "GET",
            contentType: "application/json",
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            url: url,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                $("#select-thang").empty();
                for (var i = 0; i < data.length; i++) {
                    $("#select-thang").append(new Option(data[i], data[i]));
                    $("#select-thang").select2( {
                        placeholder: "Chọn tháng",
                        allowClear: true
                    } );
                }
            }
        });
    })
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
                $.toaster('Import thành công ', 'Thông báo', 'success');
                if(data.length>0){
                    $.toaster('đã có '+data.length +' nhân viên chưa có trong hệ thống', 'Thông báo', 'warning');
                    $('#uploadModal').modal('hide');
                    $('#existModal').modal('show');
                    $('#empty').empty();
                    for(var i=0; i<data.length; i++){
                        $('#empty').append(" <tr class='text-center'>\n" +
                            "                        <td>"+data[i].maNV+"</td>\n" +
                            "                        <td>"+data[i].hoVaTen+"</td>\n" +
                            "                    </tr>")
                    }
                }

            },
            error: function (jqXHR) {
                $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
            }
        });
    }
    $("#delete").click(function () {
        var maNV = $("#select-manv").val();
        var thang = $("#select-thang").val();
        if (maNV == null || thang == null) {
            $.toaster('Bạn chưa nhập đầy đủ thông tin', 'Thông báo', 'warning');
        } else{
            var r = confirm("Bạn có chắc chắn muốn xóa lương tháng: '" + thang + "' của mã nhân viên: '"+maNV+"'");
            if (r == true) {
                $.ajax({
                    url: '/rest/deleteSalary?thang='+thang+"&maNV="+maNV,
                    headers: {
                        "Authorization": Cookies.get('Cookie'),
                    },
                    dataType: 'json',
                    type: 'POST',
                    cache: false,
                    contentType: 'application/json',

                    success: function (data) {

                        if (data == true) {
                            $.toaster('Xóa thành công 1 báo cáo', 'Thông báo', 'success');
                            $("#select-manv").change();

                        } else {
                            $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                        }
                    },
                    error: function (data) {
                        $.toaster({message: 'Có lỗi xảy ra:' + data.responseText, title: 'Thất bại', priority: 'danger'});
                    }
                })
            } else {
                console.log("cancel");
            }
        }

    })
    function formatNumber(num) {
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
    }
    $(document).ajaxStart(function(){
        $(".wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $(".wait").css("display", "none");
    });
});
