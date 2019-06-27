$(document).ready(function () {

    $(document).ajaxError(function( event, jqxhr, settings, thrownError ) {
        console.log(jqxhr);
        if ( jqxhr.responseText == "Access Denied!" && jqxhr.status == 403 ) {
                   $.toaster({message: 'Bạn không có quyền thực hiện thao tác này ', title: ' Lỗi(403) Access Denied !!', priority: 'danger'});
               }
    });
    $(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });

    if(Cookies.get('Cookie')==null){
        window.location.href = 'login.html';
    }

    var List=[];
    var Obj={};
    var Result = {};
    var $table = $('#table')
    var $remove = $('#remove')
    var selections = []


    window.ajaxOptions = {
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', Cookies.get('Cookie'))
        }
    }

    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
        })
    }

    function responseHandler(res) {
        $.each(res.rows, function (i, row) {
            row.state = $.inArray(row.id, selections) !== -1
        })
        return res
    }

    function detailFormatter(index, row) {
        var html = []
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>')
        })
        return html.join('')
    }

    function operateFormatter(value, row, index) {
        return [
            '<a class="eye" href="javascript:void(0)"  data-toggle=\"modal\" data-target=\"#myModal\" title="Edit">',
            '<i class="fa fa-pencil" aria-hidden="true"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="fa fa-trash"></i>',
            '</a>'
        ].join('')
    }

    window.operateEvents = {
        'click .eye': function (e, value, row, index) {
            Obj = JSON.stringify(row);
            console.log(Obj.maNV);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "rest/getStaffInfoByMaNV",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                data: JSON.stringify({
                    maNV: row.maNV,
                }),
                timeout: 600000,
                success: function (data) {
                    Obj = data;
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
            // alert('You click like action, row: ' + JSON.stringify(row))
        },
        'click .remove': function (e, value, row, index) {
            Obj = row;
            $("#delete").click();
        }
    }

    // function totalTextFormatter(data) {
    //     return 'Total'
    // }
    //
    // function totalNameFormatter(data) {
    //     return data.length
    // }
    //
    // function totalPriceFormatter(data) {
    //     var field = this.field
    //     return '$' + data.map(function (row) {
    //         return +row[field].substring(1)
    //     }).reduce(function (sum, i) {
    //         return sum + i
    //     }, 0)
    // }

    function initTable() {
        $table.bootstrapTable('destroy').bootstrapTable({
            locale: $('#locale').val(),
            columns: [
                [ {
                    title: 'Mã nhân viên',
                    field: 'maNV',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                }, {
                    title: 'Thông tin',
                    field: 'maNV',
                    colspan: 6,
                    align: 'center'
                }],
                [{
                    field: 'hoVaTen',
                    title: 'Họ tên',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'ngaySinh',
                    title: 'Ngày sinh',
                    sortable: true,
                    align: 'center',
                }, {
                    field: 'donVi',
                    title: 'Đơn vị',
                    sortable: true,
                    align: 'center',
                },
                    {
                    field: 'chucDanh',
                    title: 'Chức vụ',
                    sortable: true,
                    align: 'center',
                }, {
                    title: 'Hành động',
                    align: 'center',
                    events: window.operateEvents,
                    formatter: operateFormatter
                }]
            ]
        })
        $table.on('check.bs.table uncheck.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table',
            function () {
                $remove.prop('disabled', !$table.bootstrapTable('getSelections').length)

                // save your data, here just save the current page
                selections = getIdSelections()
                // push or splice the selections if you want to save all data selections
            })
        $table.on('all.bs.table', function (e, name, args) {
            console.log(name, args)
        })
        $remove.click(function () {
            var ids = getIdSelections()
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids
            })
            $remove.prop('disabled', true)
        })
    }

    $(function() {
        initTable()

        $('#locale').change(initTable)
    })


    function GetData(){
        $("#staff").empty();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/getStaffInfo",
            dataType: 'json',
            cache: false,
            timeout: 600000,
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            success: function (data) {
                List = data;
                $('#dtBasicExample').DataTable( {
                    "processing": true,
                    "serverSide": true,
                    data:data ,
                    columns: [
                        { data: 'iD' },
                        { data: 'maNV' },
                        { data: 'hoVaTen' },
                    ]
                } );
                for (var i = 0; i < data.length; i++) {
                    $("#staff-table").append(
                        "<tr class='test noselect'  data-toggle=\"modal\" data-target=\"#myModal\">\n" +
                        "    <td class='maNV'>" + data[i].maNV + "</td>\n" +
                        "    <td>" + data[i].hoVaTen + "</td>\n" +
                        "    <td>" + data[i].ngaySinh + "</td>\n" +
                        "    <td>" + data[i].donVi + "</td>\n" +
                        "    <td>" + data[i].chucDanh + "</td>\n" +
                        "</tr>")
                }
            }
        });
    }

    // GetData();

    $('#staff-table').on('click', 'tbody tr', function (event) {
        var maNV = $(this).find(".maNV").text();
        maNV = maNV.trim();
        console.log(maNV);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/rest/user/getStaffInfoByMaNV",
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
                Obj = data;
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
    });
    $("#btn-add").click(function () {
        $("#maNV").val("");
        $("#hovaten").val("");
        $("#ngaysinh").val("");
        $("#donvi").val("");
        $("#soDT").val("");
        $("#chucdanh").val("");
        $("#soCMND").val("");
        $("#ngaycap").val("");
        $("#noicap").val("");
        $("#sotaikhoan").val("");
        $("#nganhanghuong").val("");

        $("#hoten").val("");
        $("#username").val("");
        $("#password").val("123456");
        $("#repassword").val("123456");
    })
    $("#maNV").change(function () {
        $("#username").val($("#maNV").val());
    })
    $("#hovaten").change(function () {
        $("#hoten").val($("#hovaten").val());
    })

    $("#btn-create").click(function () {
        var maNV = $("#maNV").val();
        var hovaten = $("#hovaten").val();
        var ngaySinh = $("#ngaysinh").val();
        var donVi = $("#donvi").val();
        var soDienThoai = $("#soDT").val();
        var chucDanh = $("#chucdanh").val();
        var soCMND = $("#soCMND").val();
        var ngayCap = $("#ngaycap").val();
        var noiCap = $("#noicap").val();
        var soTaiKhoan = $("#sotaikhoan").val();
        var nganHangHuong = $("#nganhanghuong").val();
        var status = $("#select-status").val();
        if (status == "admin")
            status = "ROLE_ADMIN";
        else
            status = "ROLE_USER";
        var password =  $("#password").val();
        var repassword =  $("#repassword").val();
        if (maNV.trim() == "") {
            $.toaster('Mã NV không được để trống', 'Thông báo', 'warning');
        } else if (hovaten.trim() == "") {
            $.toaster('Họ tên không được để trống', 'Thông báo', 'warning');
        }
        else if (password.trim() == "") {
            $.toaster('Mật khẩu không được để trống', 'Thông báo', 'warning');
        }
        else if (password.trim() != repassword.trim()) {
            $.toaster('Mật khẩu nhập lại không khớp', 'Thông báo', 'warning');
        }
        else {
            $.ajax({
                type: "GET",
                contentType: "application/json",  headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                url: "rest/checkMaNV?code="+maNV,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    if (data == true) {
                        $.ajax({
                            type: "POST",
                            contentType: "application/json",  headers: {
                                "Authorization": Cookies.get('Cookie'),
                            },
                            url: "rest/createProfile",
                            dataType: 'json',
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
                                    $.toaster('Thêm mới thành công 1 profile', 'Thông báo', 'success');
                                    initTable();

                                    $.ajax({
                                        url: '/rest/users',
                                        dataType: 'json',
                                        type: 'POST',
                                        cache: false,
                                        headers: {
                                            "Authorization": Cookies.get('Cookie'),
                                        },
                                        contentType: 'application/json',
                                        data: JSON.stringify({
                                            userName: maNV,
                                            password: password,
                                            hovaten: hovaten,
                                            role: status
                                        }),

                                        success: function (data) {
                                            console.log(data);
                                            if (data == true) {
                                                $.toaster('Thêm mới thành công 1 Account', 'Thông báo', 'success');
                                            } else {
                                                $.toaster({
                                                    message: 'Có lỗi xảy ra khi tạo tài khoản: UserName có thể đã tồn tại',
                                                    title: 'Thất bại',
                                                    priority: 'danger'
                                                });
                                            }
                                        },
                                        error: function (data) {
                                            if (data="User Existed!"){
                                                $.toaster({
                                                    message: 'Có lỗi xảy ra khi tạo tài khoản: UserName đã tồn tại',
                                                    title: 'Thất bại',
                                                    priority: 'danger'
                                                });
                                            }
                                            else $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                                        }
                                    })

                                } else {
                                    $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                                }
                            },
                            error: function (data) {
                                $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                            }
                        });
                    } else {
                        $.toaster({message: 'Mã nhân viên đã bị trùng ', title: 'Thất bại', priority: 'danger'});
                    }
                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                }
            });
        }
    })


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
                contentType: "application/json",  headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                url: "rest/updateProfile",
                dataType: 'json',
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
                        $.toaster('Cập nhật thành công 1 profile', 'Thông báo', 'success');
                        initTable();
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
    $("#delete").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa profile '" + Obj.hoVaTen + "'");
        console.log(Obj.iD);
        var id=Obj.iD;
        if (r == true) {
            $.ajax({
                url: 'rest/deleteStaff',
                dataType: 'json',
                type: 'POST',  headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify({
                   iD:id,
                }),

                success: function (data) {

                    if (data == true) {
                        $.toaster('Xóa thành công 1 Profile', 'Thông báo', 'success');
                        initTable()
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
    })

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
                Result = data;
                $.toaster('Import thành công !', 'Thông báo', 'success');
                var a = Result.success - Result.accError;
                $("#singleFileUploadSuccess1").empty();
                if(a > 0){
                    $("#singleFileUploadSuccess1").append("<a class='mt-2 btn btn-primary' href='/download1'>Tải xuống danh sách tài khoản được tạo mới <i class=\"fa fa-download\" aria-hidden=\"true\"></i></a>")
                }
                $('#uploadModal').modal('hide');
                $('#resultModal').modal('show');

                $('#resulttbl').empty();
                $('#resulttbl').append(" <tr class='text-center'>\n" +
                    "                        <td>"+Result.success+"</td>\n" +
                    "                        <td>"+Result.skip+"</td>\n" +
                    "                        <td>"+Result.error+"</td>\n" +
                    "                        <td>"+a+"</td>\n" +
                    "                    </tr>")
                initTable();
            },
            error: function (jqXHR) {

            }
        });
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



});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            var str_img = e.target.result;
            var maNV = document.getElementById("edit-maNV").value;

            $.ajax({
                type: "POST",
                url: "rest/upload/user/avata",  headers: {
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

