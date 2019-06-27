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
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/rest/getCatebyID",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                data: JSON.stringify({
                    id: row.id,
                }),
                timeout: 600000,
                success: function (data) {
                    Obj = data;
                    $("#edit-id").val(data.id);
                    $("#edit-name").val(data.name);

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
                    title: 'ID',
                    field: 'id',
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
                    field: 'name',
                    title: 'Tên Danh Mục',
                    sortable: true,
                    align: 'center'
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




    $('#btn-add').click(function () {
        $("#cateName").val("");
    })
    $("#btn-create").click(function () {
        var cateName = $("#cateName").val();
        if (cateName.trim() == "") {
            $.toaster('Tên danh mục không được đẻ trống', 'Thông báo', 'warning');
        } else {
            $.ajax({
                                        type: "POST",
                                        contentType: "application/json",
                                        url: "/rest/createCategory",
                                        dataType: 'json',
                                        headers: {
                                            "Authorization": Cookies.get('Cookie'),
                                        },
                                        cache: false,
                                        data: JSON.stringify({
                                            name: cateName
                                        }),
                                        timeout: 600000,
                                        success: function (data) {
                                            if (data == true) {
                                                $.toaster('Thêm mới thành công 1 bản ghi', 'Thông báo', 'success');
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
    $("#btn-update").click(function () {
        var name = $("#edit-name").val();
        var id= Obj.id;
        console.log(id);
        if (name.trim() == "") {
            $.toaster('Tên danh mục không được để trống', 'Thông báo', 'warning');
        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/rest/updateCategory",
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                cache: false,
                data: JSON.stringify({
                    id: id,
                    name: name
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
        var r = confirm("Bạn có chắc chắn muốn xóa danh mục '" + Obj.name + "'");
        console.log(Obj.id);
        var id=Obj.id;
        if (r == true) {
            $.ajax({
                url: '/rest/deleteCategory',
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                type: 'POST',
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify({
                    id:id,
                }),

                success: function (data) {

                    if (data == true) {
                        $.toaster('Xóa thành công 1 danh mục', 'Thông báo', 'success');
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


    $(document).ajaxStart(function(){
        $(".wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $(".wait").css("display", "none");
    });


});
