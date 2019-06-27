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
    var List=[];
    var Obj={};
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
                url: "/rest/getCVInfoByMaCV",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                data: JSON.stringify({
                    maCV: row.maCV,
                }),
                timeout: 600000,
                success: function (data) {
                    Obj = data;
                    $("#edit-maCV").val(data.maCV);
                    $("#edit-hunter").val(data.hunter);
                    $("#edit-hovaten").val(data.hoVaTen);
                    $("#edit-namSinh").val(data.namSinh);
                    $("#edit-soDT").val(data.soDT);
                    $("#edit-email").val(data.email);
                    $("#edit-totNghiepTruong").val(data.truong);
                    $("#edit-nganhHoc").val(data.nganh);
                    $("#edit-doiTuong").val(data.doiTuong);
                    $("#edit-viTriUngTuyen").val(data.viTri);
                    $("#edit-donViPhongVan").val(data.donViPV);
                    $("#edit-nguoiPhongVan").val(data.nguoiPV);
                    $("#edit-nguonCV").val(data.nguonCV);
                    $("#edit-nguoiGioiThieu").val(data.nguoiGT);
                    $("#edit-ngayPV1").val(data.ngayPVVong1);
                    $("#edit-ketQua1").val(data.ketQuaV1);
                    $("#edit-ngayPV2").val(data.ngayPVVong2);
                    $("#edit-ketQua2").val(data.ketQuaV2);
                    $("#edit-ketQuaCuoi").val(data.ketQuaCuoi);
                    $("#edit-nhanXet").val(data.nhanXet);
                    $("#edit-ghiChu").val(data.note);
                }
            });
            // alert('You click like action, row: ' + JSON.stringify(row))
        },
        'click .remove': function (e, value, row, index) {
            Obj = row;
            $("#delete").click();
        }
    }


    function initTable() {
        $table.bootstrapTable('destroy').bootstrapTable({
            locale: $('#locale').val(),
            columns: [
                [ {
                    title: 'Mã CV',
                    field: 'maCV',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                }, {
                    title: 'Thông tin',
                    colspan: 6,
                    align: 'center'
                }],
                [{
                    field: 'hoVaTen',
                    title: 'Họ tên',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'namSinh',
                    title: 'Ngày sinh',
                    sortable: true,
                    align: 'center',
                }, {
                    field: 'soDT',
                    title: 'Số điện thoại',
                    sortable: true,
                    align: 'center',
                },{
                    field: 'doiTuong',
                    title: 'Đối tượng',
                    sortable: true,
                    align: 'center',
                },{
                    field: 'viTri',
                    title: 'Vị Trí',
                    sortable: true,
                    align: 'center',
                },{
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
        $("#recruitment").empty();
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/getCVInfo",
        dataType: 'json',
        headers: {
            "Authorization": Cookies.get('Cookie'),
        },
        cache: false,
        timeout: 600000,
        success: function (data) {
            List = data;
            for (var i = 0; i < data.length; i++) {
                $("#recruitment-table").append(
                    "<tr data-toggle=\"modal\" data-target=\"#myModal\">\n" +
                    "    <td class='maCV'>" + data[i].maCV + "</td>\n" +
                    "    <td>" + data[i].hoVaTen + "</td>\n" +
                    "    <td>" + data[i].namSinh + "</td>\n" +
                    "    <td>" + data[i].soDT + "</td>\n" +
                    "    <td>" + data[i].doiTuong + "</td>\n" +
                    "    <td>" + data[i].viTri + "</td>\n" +
                    "</tr>")
            }
        }
    });
    }
    // GetData();
    $('#recruitment-table').on('click', 'tbody tr', function (event) {
        var maCV = $(this).find(".maCV").text();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/rest/getCVInfoByMaCV",
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            dataType: 'json',
            cache: false,
            data: JSON.stringify({
                maCV: maCV,
            }),
            timeout: 600000,
            success: function (data) {
                Obj = data;
                $("#edit-maCV").val(data.maCV);
                $("#edit-hunter").val(data.hunter);
                $("#edit-hovaten").val(data.hoVaTen);
                $("#edit-namSinh").val(data.namSinh);
                $("#edit-soDT").val(data.soDT);
                $("#edit-email").val(data.email);
                $("#edit-totNghiepTruong").val(data.truong);
                $("#edit-nganhHoc").val(data.nganh);
                $("#edit-doiTuong").val(data.doiTuong);
                $("#edit-viTriUngTuyen").val(data.viTri);
                $("#edit-donViPhongVan").val(data.donViPV);
                $("#edit-nguoiPhongVan").val(data.nguoiPV);
                $("#edit-nguonCV").val(data.nguonCV);
                $("#edit-nguoiGioiThieu").val(data.nguoiGT);
                $("#edit-ngayPV1").val(data.ngayPVVong1);
                $("#edit-ketQua1").val(data.ketQuaV1);
                $("#edit-ngayPV2").val(data.ngayPVVong2);
                $("#edit-ketQua2").val(data.ketQuaV2);
                $("#edit-ketQuaCuoi").val(data.ketQuaCuoi);
                $("#edit-nhanXet").val(data.nhanXet);
                $("#edit-ghiChu").val(data.note);

            }
        });
    });
    $('#btn-add').click(function () {
        $("#edit-maCV").val("");
        $("#edit-hunter").val("");
        $("#edit-hovaten").val("");
        $("#edit-namSinh").val("");
        $("#edit-soDT").val("");
        $("#edit-email").val("");
        $("#edit-totNghiepTruong").val("");
        $("#edit-nganhHoc").val("");
        $("#edit-doiTuong").val("");
        $("#edit-viTriUngTuyen").val("");
        $("#edit-donViPhongVan").val("");
        $("#edit-nguoiPhongVan").val("");
        $("#edit-nguonCV").val("");
        $("#edit-nguoiGioiThieu").val("");
        $("#edit-ngayPV1").val("");
        $("#edit-ngayPV2").val("");
        $("#edit-ketQuaCuoi").val("");
        $("#edit-nhanXet").val("");
        $("#edit-ghiChu").val("");
    })
    $("#btn-create").click(function () {
        var maCV = $("#maCV").val();
        var hunter = $("#hunter").val();
        var hoVaTen = $("#hovaten").val();
        var namSinh = $("#namSinh").val();
        var soDT = $("#soDT").val();
        var email = $("#email").val();
        var truong = $("#totNghiepTruong").val();
        var nganh = $("#nganhHoc").val();
        var doiTuong = $("#doiTuong").val();
        var viTri = $("#viTriUngTuyen").val();
        var donViPV = $("#donViPhongVan").val();
        var nguoiPV = $("#nguoiPhongVan").val();
        var nguonCV = $("#nguonCV").val();
        var nguoiGT = $("#nguoiGioiThieu").val();
        var ngayPVVong1 = $("#ngayPV1").val();
        var ketQuaV1 = $("#ketQua1").val();
        var ngayPVVong2 = $("#ngayPV2").val();
        var ketQuaV2 = $("#ketQua2").val();
        var ketQuaCuoi = $("#ketQuaCuoi").val();
        var nhanXet = $("#nhanXet").val();
        var note = $("#ghiChu").val();
        if (maCV.trim() == "") {
            $.toaster('Mã CV không được để trống', 'Thông báo', 'warning');
        } else if (hoVaTen.trim() == "") {
            $.toaster('Họ tên không được để trống', 'Thông báo', 'warning');
        } else {
            $.ajax({
                type: "GET",
                contentType: "application/json",  headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                url: "rest/checkMaCV?code="+maCV,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    if (data == true) {
                        $.ajax({
                            type: "POST",
                            contentType: "application/json",
                            url: "/rest/createRecuitment",
                            dataType: 'json',
                            headers: {
                                "Authorization": Cookies.get('Cookie'),
                            },
                            cache: false,
                            data: JSON.stringify({
                                maCV: maCV,
                                hunter: hunter,
                                namSinh: namSinh,
                                hoVaTen: hoVaTen,
                                soDT: soDT,
                                email: email,
                                truong: truong,
                                nganh: nganh,
                                doiTuong: doiTuong,
                                viTri : viTri,
                                donViPV: donViPV,
                                nguoiPV: nguoiPV,
                                nguonCV: nguonCV,
                                nguoiGT: nguoiGT,
                                ngayPVVong1: ngayPVVong1,
                                ketQuaV1: ketQuaV1,
                                ngayPVVong2: ngayPVVong2,
                                ketQuaV2: ketQuaV2,
                                ketQuaCuoi: ketQuaCuoi,
                                nhanXet: nhanXet,
                                note: note,
                            }),
                            timeout: 600000,
                            success: function (data) {
                                if (data == true) {
                                    $.toaster('Thêm mới thành công 1 bản ghi', 'Thông báo', 'success');
                                    initTable()
                                } else {
                                    $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                                }
                            },
                            error: function (data) {
                                $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                            }
                        });
                    }
                    else {
                        $.toaster({message: 'Mã CV đã bị trùng ', title: 'Thất bại', priority: 'danger'});
                    }
                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                }
            });




        }
    })
    $("#btn-update").click(function () {
        var maCV = $("#edit-maCV").val();
        var hunter = $("#edit-hunter").val();
        var hoVaTen = $("#edit-hovaten").val();
        var namSinh = $("#edit-namSinh").val();
        var soDT = $("#edit-soDT").val();
        var email = $("#edit-email").val();
        var truong = $("#edit-totNghiepTruong").val();
        var nganh = $("#edit-nganhHoc").val();
        var doiTuong = $("#edit-doiTuong").val();
        var viTri = $("#edit-viTriUngTuyen").val();
        var donViPV = $("#edit-donViPhongVan").val();
        var nguoiPV = $("#edit-nguoiPhongVan").val();
        var nguonCV = $("#edit-nguonCV").val();
        var nguoiGT = $("#edit-nguoiGioiThieu").val();
        var ngayPVVong1 = $("#edit-ngayPV1").val();
        var ketQuaV1 = $("#edit-ketQua1").val();
        var ngayPVVong2 = $("#edit-ngayPV2").val();
        var ketQuaV2 = $("#edit-ketQua2").val();
        var ketQuaCuoi = $("#edit-ketQuaCuoi").val();
        var nhanXet = $("#edit-nhanXet").val();
        var note = $("#edit-ghiChu").val();
        var id= Obj.iD;
        console.log(id);
        if (maCV.trim() == "") {
            $.toaster('Mã CV không được để trống', 'Thông báo', 'warning');
        } else if (hoVaTen.trim() == "") {
            $.toaster('Họ tên không được để trống', 'Thông báo', 'warning');
        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/rest/updateRecruitment",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                data: JSON.stringify({
                    iD: id,
                    maCV: maCV,
                    hunter: hunter,
                    namSinh: namSinh,
                    hoVaTen: hoVaTen,
                    soDT: soDT,
                    email: email,
                    truong: truong,
                    nganh: nganh,
                    doiTuong: doiTuong,
                    viTri : viTri,
                    donViPV: donViPV,
                    nguoiPV: nguoiPV,
                    nguonCV: nguonCV,
                    nguoiGT: nguoiGT,
                    ngayPVVong1: ngayPVVong1,
                    ketQuaV1: ketQuaV1,
                    ngayPVVong2: ngayPVVong2,
                    ketQuaV2: ketQuaV2,
                    ketQuaCuoi: ketQuaCuoi,
                    nhanXet: nhanXet,
                    note: note,
                }),
                timeout: 600000,
                success: function (data) {
                    if (data == true) {
                        $.toaster('Cập nhật thành công 1 bản ghi', 'Thông báo', 'success');
                        initTable()
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
        var r = confirm("Bạn có chắc chắn muốn xóa ứng viên '" + Obj.hoVaTen + "'");
        console.log(Obj.iD);
        var id=Obj.maCV;
        if (r == true) {
            $.ajax({
                url: '/rest/deleteRecruitment',
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                type: 'POST',
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify({
                    maCV:id,
                }),

                success: function (data) {

                    if (data == true) {
                        $.toaster('Xóa thành công 1 hồ sơ', 'Thông báo', 'success');
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

    $("#submitButton2").click(function (event) {

        // Stop default form Submit.
        event.preventDefault();

        // Get form
        var form = $('#singleUploadForm2')[0];

        var data = new FormData(form);

        // Call Ajax Submit.
        ajaxSubmitForm(data, "/rest/upload/candidate");
    });

    $(document).ajaxStart(function(){
        $(".wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $(".wait").css("display", "none");
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
            },
            error: function (jqXHR) {
                $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
            }
        });
    }

});
