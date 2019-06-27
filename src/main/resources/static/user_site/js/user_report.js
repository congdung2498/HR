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
    var maNV = Cookies.get('UserName');
    $("#edit-maNV").val(maNV);

    $(document).ajaxError(function( event, jqxhr, settings, thrownError ) {
        console.log(jqxhr);
        if ( jqxhr.responseText == "Access Denied!" && jqxhr.status == 403 ) {
                   $.toaster({message: 'Bạn không có quyền thực hiện thao tác này ', title: ' Lỗi(403) Access Denied !!', priority: 'danger'});
               }
    });

    $("#btn-xem").click(function () {
        var maNV = $("#edit-maNV").val();
        var thang = $("#select-thang").val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/rest/user/getSalaryByMaCvMonth",
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
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
        $.ajax({
            type: "GET",
            contentType: "application/json",
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            url: "/rest/getAllMonthByMaNV?maNV=" + maNV,
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
    function formatNumber(num) {
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
    }
});

