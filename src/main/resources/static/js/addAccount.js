$(document).ready(function () {
    var ListAcc = [];
    var Acc = {};
    $(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });
    if(Cookies.get('Cookie')==null){
        $.toaster('Xin mời đăng nhập', 'Thông báo', 'warning');
        window.location.href = 'login.html';
    }

    $(document).ajaxError(function( event, jqxhr, settings, thrownError ) {
        console.log(jqxhr);
        if ( jqxhr.responseText == "Access Denied!" && jqxhr.status == 403 ) {
                   $.toaster({message: 'Bạn không có quyền thực hiện thao tác này ', title: ' Lỗi(403) Access Denied !!', priority: 'danger'});
               }
    });

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
            Acc = row;
            $.ajax({
                type: "GET",
                contentType: "application/json",
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                url: "/rest/users/"+row.id,
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {
                    Acc = data;
                    $("#hoten").val(data.hovaten);
                    $("#quyen").val(data.role);
                    $("#username").val(data.userName);
                }
            });
            // alert('You click like action, row: ' + JSON.stringify(row))
        },
        'click .remove': function (e, value, row, index) {
            Acc = row;
            $("#deleteAccount").click();
        }
    }

    function initTable() {
        $table.bootstrapTable('destroy').bootstrapTable({
            locale: $('#locale').val(),
            columns: [
                [ {
                    title: 'ID',
                    field: 'id',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                }, {
                    title: 'Thông tin',
                    colspan: 4,
                    align: 'center'
                }],
                [{
                    field: 'userName',
                    title: 'User name',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'hovaten',
                    title: 'Họ tên',
                    sortable: true,
                    align: 'center',
                }, {
                    field: 'role',
                    title: 'Quyền truy cập',
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


    $('#account-table').on('click', 'tbody tr', function (event) {
        var id = $(this).find(".id").text();
        id = id.trim();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            url: "/rest/users/"+id,
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {
                Acc = data;
                $("#hoten").val(data.hovaten);
                $("#quyen").val(data.role);
                $("#username").val(data.userName);
            }
        });
    });


    $("#btn-addAccount").click(function () {
        var userName = $("#edit-username").val();
        var pass = $("#edit-pass").val();
        var hoten = $("#edit-hoten").val();
        var status = $("#select-status").val();


        if (status == "admin")
            status = "ROLE_ADMIN";
        else
            status = "ROLE_USER";
        if (userName == "") {
            $.toaster('Username không được để trống', 'Thông báo', 'warning');
        } else if (pass == "") {
            $.toaster('Mật khẩu không được để trống', 'Thông báo', 'warning');
        }else if (status == "") {
            $.toaster('Quyền không được để trống', 'Thông báo', 'warning');
        } else {
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
                    userName: userName,
                    password: pass,
                    hovaten: hoten,
                    role: status
                }),

                success: function (data) {
                    console.log(data);
                    if (data == true) {
                        $.toaster('Thêm mới thành công 1 Account', 'Thông báo', 'success');
                        initTable();
                         $("#edit-username").val("");
                         $("#edit-pass").val("");
                         $("#edit-hoten").val("");
                    } else {
                        $.toaster({
                            message: 'Có lỗi xảy ra: UserName có thể đã tồn tại',
                            title: 'Thất bại',
                            priority: 'danger'
                        });
                    }
                },
                error: function (data) {
                    if (data="User Existed!"){
                        $.toaster({
                            message: 'Có lỗi xảy ra: UserName đã tồn tại',
                            title: 'Thất bại',
                            priority: 'danger'
                        });
                    }
                    else $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                }
            })
        }

    })


    $("#editAccount").click(function () {
            var name = $("#hoten").val();
            var status = $("#quyen").val();
            var id = Acc.id;
            if (name == "") {
                $.toaster('Họ tên không được để trống', 'Thông báo', 'warning');
            } else if (status == "") {
                $.toaster('Quyền không được để trống', 'Thông báo', 'warning');
            } else {
                $.ajax({
                    url: '/rest/updateUser',
                    dataType: 'json',
                    type: 'POST',
                    cache: false,
                    headers: {
                        "Authorization": Cookies.get('Cookie'),
                    },
                    contentType: 'application/json',
                    data: JSON.stringify({
                        id: id,
                        hovaten: name,
                        role: status
                    }),

                    success: function (data) {
                        if (data == true) {
                            $.toaster('Cập nhật thành công 1 Account', 'thông báo', 'success');
                            initTable()                        } else {
                            $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                        }
                        window.location.href = '#';
                    },
                    error: function (data) {
                        $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                    }
                })
            }
        }
    )

    $("#changePass").click(function () {
        var newPass = $("#newpass").val();
        var rePass = $("#renew").val();
        console.log(newPass + " " + rePass);
        if (newPass == "") {
            $.toaster('Bạn chưa nhập mật khẩu', 'Thông báo', 'warning');
        } else if (rePass == "") {
            $.toaster('Bạn chưa nhập lại mật khẩu', 'Thông báo', 'warning');
        } else if (newPass != rePass) {
            $.toaster('Mật khẩu nhập lại không khớp', 'Thông báo', 'warning');
        } else {
            $.ajax({
                url: '/rest/changePass',
                dataType: 'json',
                type: 'POST',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify({
                    id: Acc.id,
                    password: newPass
                }),

                success: function (data) {
                    if (data == true) {
                        $.toaster('Đổi mật khẩu thành công cho tài khoản ' + Acc.userName, 'thông báo', 'success');
                    } else {
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }

                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                }
            })
        }

    })

    $("#deleteAccount").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa tài khoản '" + Acc.userName + "'");
        if (r == true) {
            $.ajax({
                url: '/rest/users/'+Acc.id,
                dataType: 'json',
                type: 'DELETE',
                cache: false,
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                contentType: 'application/json',

                success: function (data) {
                    if (data == true) {
                        $.toaster('Xóa thành công tài khoản ' + Acc.userName, 'thông báo', 'success');
                        window.location.href = '#';
                        initTable();                    } else {
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }

                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                }
            })
        }
    })
});
