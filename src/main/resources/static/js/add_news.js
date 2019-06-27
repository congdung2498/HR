$(document).ready(function () {
    if(Cookies.get('Cookie')==null){
        $.toaster('Xin mời đăng nhập', 'Thông báo', 'warning');
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
    var name = Cookies.get('HoVaTen');
    var Category={};
    var editCategory={};
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
    function fileFormatter(value, row, index) {
        // return [
        //     '<a class="eye" href="javascript:void(0)"  data-toggle=\"modal\" data-target=\"#myModal\" title="Edit">',
        //     '<i class="fa fa-pencil" aria-hidden="true"></i>',
        //     '</a>  ',
        //     '<a class="remove" href="javascript:void(0)" title="Remove">',
        //     '<i class="fa fa-trash"></i>',
        //     '</a>'
        // ].join('')
        return value.length;
    }
    var id;
    window.operateEvents = {
        'click .eye': function (e, value, row, index) {
             id =row.iD;

            console.log(row.iD);
            getNewByID(id);
        },
        'click .remove': function (e, value, row, index) {
            Obj = row;
            $("#delete").click();
        },
    }
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
            for (var i = 0; i < data.rows.length; i++) {
                $('#select-category').append($('<option>', {
                    value: data.rows[i].id,
                    text: data.rows[i].name
                }));
                 $('#edit-select-category').append($('<option>', {
                                    value: data.rows[i].id,
                                    text: data.rows[i].name
                                }));
            }
            $("#select-category").select2( {
                placeholder: "Chọn danh mục",
                allowClear: true
            } );
             $("#edit-select-category").select2( {
                            placeholder: "Chọn danh mục",
                            allowClear: true
             } );
        }
    });
    function getNewByID(id) {
        $.ajax({
            url: '/rest/getNewsbyID?id='+id,
            headers: {
                "Authorization": Cookies.get('Cookie'),
            },
            dataType: 'json',
            type: 'GET',
            cache: false,
            contentType: 'application/json',

            success: function (data) {
                Obj = data;
                $("#edit-title").val(data.title);
                $("#edit-author").val(data.author);
                $("#edit-summary").val(data.summary);
                $("#edit-content").val(data.content);
                $("#edit-select-category").val(data.category.id);
                 $("#edit-select-category").select2( {
                                            placeholder: "Chọn danh mục",
                                            allowClear: true
                             } );
                $("#files").empty();
                if(data.files.length>0){
                    $("#files").append(" <h3 class='mt-2'>Danh sách tệp đính kèm</h3>");
                    data.files.forEach(function(file) {
                        $("#files").append("<p><a class='delFile'><span style='display: none' class='fileID'>"+file.id+"</span><i style='color: darkblue;' class=\"fa fa-times \" aria-hidden=\"true\"></i><a> <a style='color: #0e6498' href="+file.fileName+">"+file.fileName+"</a> </p> ")
                    });

                }
                $("#news-image").empty();
                if(data.img != null){
                    $("#news-image").append("<img style='width: 50%' src=\"rest/getImg/"+data.img+"\" >")
                }
            },
            error: function (data) {
                $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
            }
        })
    }
    function initTable() {
        $table.bootstrapTable('destroy').bootstrapTable({
            locale: $('#locale').val(),
            columns: [
                [ {
                    title: 'Mã bài viết',
                    field: 'iD',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                }, {
                    title: 'Thông tin',
                    colspan: 6,
                    align: 'center'
                }],
                [
                {
                    field: 'category.name',
                    title: 'Danh mục',
                    sortable: true,
                    align: 'center'
                 },
                {
                    field: 'title',
                    title: 'Tiêu đề',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'author',
                    title: 'Tác giả',
                    sortable: true,
                    align: 'center',
                }, {
                    field: 'files',
                    title: ' Số tệp đính kèm',
                    sortable: true,
                    align: 'center',
                    formatter:fileFormatter
                },{
                    field: 'date',
                    title: 'Thời gian',
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
        initTable();

        $('#locale').change(initTable)
    })


    $("#author").val(name);
    console.log(name);

    $( '#content' ).ckeditor();
    $( '#edit-content' ).ckeditor();
    // GetData();
    $('#btn-addNews').click(function () {
        Category.id =  $("#select-category").val();
        var title = $("#title").val();
        var author = $("#author").val();
        var summary = $("#summary").val();
        var content = $("#content").val();
        if(title.trim()==""){
            $.toaster('Tiêu đề bài viết không được để trống', 'Thông báo', 'warning');
        }else if(author.trim()==""){
            $.toaster('Tác giả không được để trống', 'Thông báo', 'warning');
        } else if(Category.id == null){
            $.toaster('Danh mục không được để trống', 'Thông báo', 'warning');
         }else if(summary.trim()==""){
            $.toaster('Mô tả được để trống', 'Thông báo', 'warning');
        }else if(content.trim()==""){
            $.toaster('Nội dung được để trống', 'Thông báo', 'warning');
        }else{
            $.ajax({
                url: '/rest/addNews',
                dataType: 'json',
                type: 'POST',
                cache: false,
                contentType: 'application/json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                data: JSON.stringify({
                    title: title,
                    summary: summary,
                    content:content,
                    author:author,
                    category : Category
                }),

                success: function (data) {
                    if (data != 0) {
                        $.toaster('Đăng thành công 1 bài viết', 'Thông báo', 'success');
                        initTable();
                        $("#title").val("");
                        $("#summary").val("")
                        $("#content").val("");

                        var files = multipleFileUploadInput.files;
                        if(files.length === 0) {
                            multipleFileUploadError.innerHTML = "Please select at least one file";
                            multipleFileUploadError.style.display = "block";
                        }
                        else{
                            uploadMultipleFiles(files,data);
                            event.preventDefault();
                        }
                        var filesImage = ImageFileUploadInput.files;
                        if(filesImage.length === 0) {
                            ImageFileUploadError.innerHTML = "Please select at least one file";
                            ImageFileUploadError.style.display = "block";
                        }else{
                            UpImage(filesImage,data);
                            event.preventDefault();
                        }

                    } else  if (data == 0){
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }
                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                }
            })
        }
    })

    $('#btn-update').click(function () {
        editCategory.id = $("#edit-select-category").val();
        var title = $("#edit-title").val();
        var author = $("#edit-author").val();
        var summary = $("#edit-summary").val();
        var content = $("#edit-content").val();
        if(title.trim()==""){
            $.toaster('Tiêu đề bài viết không được để trống', 'Thông báo', 'warning');
        }else if(author.trim()==""){
            $.toaster('Tác giả không được để trống', 'Thông báo', 'warning');
        }else if(editCategory.id == null){
            $.toaster('Danh mục không được để trống', 'Thông báo', 'warning');
        }else if(summary.trim()==""){
            $.toaster('Mô tả được để trống', 'Thông báo', 'warning');
        }else if(content.trim()==""){
            $.toaster('Nội dung được để trống', 'Thông báo', 'warning');
        }else{
            $.ajax({
                url: '/rest/editNews',
                dataType: 'json',
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                type: 'POST',
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify({
                    iD : Obj.iD,
                    title: title,
                    summary: summary,
                    content:content,
                    author:author,
                    category : editCategory
                }),

                success: function (data) {
                    if (data == true) {

                        var files = multipleFileUploadInput2.files;
                        if(files.length === 0) {
                            multipleFileUploadError2.innerHTML = "Please select at least one file";
                            multipleFileUploadError2.style.display = "block";
                        }else{
                            uploadMultipleFiles2(files,Obj.iD);
                            event.preventDefault();
                        }

                        var filesupload = ImageFileUploadInput2.files;
                        if(filesupload.length === 0) {
                            ImageFileUploadError2.innerHTML = "Please select at least one file";
                            ImageFileUploadError2.style.display = "block";
                        }else{
                            EditImage(filesupload,Obj.iD);
                            event.preventDefault();
                        }

                        $.toaster('Cập nhật thành công 1 bài viết', 'Thông báo', 'success');
                        initTable();
                        getNewByID(id);

                    } else {
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }
                    window.location.href = '#';
                },
                error: function (data) {
                    $.toaster({message: 'Có lỗi xảy ra: ' + data, title: 'Thất bại', priority: 'danger'});
                }
            })
        }
    })
    $("#delete").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa tin '" + Obj.title + "'");
        if (r == true) {
            $.ajax({
                url: '/rest/deleteNews?id='+Obj.iD,
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                dataType: 'json',
                type: 'POST',
                cache: false,
                contentType: 'application/json',

                success: function (data) {

                    if (data == true) {
                        $.toaster('Xóa thành công 1 bài viết', 'Thông báo', 'success');
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

    var multipleUploadForm = document.querySelector('#multipleUploadForm');
    var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
    var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
    var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

    function uploadMultipleFiles(files,newsID) {
        var formData = new FormData();
        for(var index = 0; index < files.length; index++) {
            formData.append("files", files[index]);
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/uploadMultipleFiles/"+newsID);
        xhr.setRequestHeader('Authorization',Cookies.get('Cookie')  );
        xhr.onload = function() {
            console.log(xhr.responseText);
            var response = JSON.parse(xhr.responseText);
            if(xhr.status == 200) {
                multipleFileUploadError.style.display = "none";
                var content = "<p>All Files Uploaded Successfully</p>";
                for(var i = 0; i < response.length; i++) {
                    content += "<p>DownloadUrl : <a style='color: #007bff;' href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
                }
                multipleFileUploadSuccess.innerHTML = content;
                multipleFileUploadSuccess.style.display = "block";
            } else {
                multipleFileUploadSuccess.style.display = "none";
                multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
            }
        }

        xhr.send(formData);
    }



    multipleUploadForm.addEventListener('submit', function(event){
        var files = multipleFileUploadInput.files;
        if(files.length === 0) {
            multipleFileUploadError.innerHTML = "Please select at least one file";
            multipleFileUploadError.style.display = "block";
        }
        uploadMultipleFiles(files);
        event.preventDefault();
    }, true);



    var multipleUploadForm2 = document.querySelector('#multipleUploadForm2');
    var multipleFileUploadInput2 = document.querySelector('#multipleFileUploadInput2');
    var multipleFileUploadError2 = document.querySelector('#multipleFileUploadError2');
    var multipleFileUploadSuccess2 = document.querySelector('#multipleFileUploadSuccess2');

    function uploadMultipleFiles2(files,newsID) {
        var formData = new FormData();
        for(var index = 0; index < files.length; index++) {
            formData.append("files", files[index]);
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/uploadMultipleFiles/"+newsID);
        xhr.setRequestHeader('Authorization',Cookies.get('Cookie')  );

        xhr.onload = function() {
            console.log(xhr.responseText);
            var response = JSON.parse(xhr.responseText);
            if(xhr.status == 200) {
                multipleFileUploadError2.style.display = "none";
                var content = "<p>All Files Uploaded Successfully</p>";
                for(var i = 0; i < response.length; i++) {
                    content += "<p>DownloadUrl : <a style='color: #007bff;' href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
                }
                initTable();
                getNewByID(id);
            } else {
                multipleFileUploadSuccess2.style.display = "none";
                multipleFileUploadError2.innerHTML = (response && response.message) || "Some Error Occurred";
            }
        }

        xhr.send(formData);

    }

    var ImageuploadMultipleFiles = document.querySelector('#ImageuploadMultipleFiles');
    var ImageFileUploadInput = document.querySelector('#ImageFileUploadInput');
    var ImageFileUploadError = document.querySelector('#ImageFileUploadError');
    var ImageFileUploadSuccess = document.querySelector('#ImageFileUploadSuccess');

    function UpImage(files,newsID) {
        var formData = new FormData();
        for(var index = 0; index < files.length; index++) {
            formData.append("file", files[index]);
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/rest/uploadImg/"+newsID);
        xhr.setRequestHeader('Authorization',Cookies.get('Cookie') );
        xhr.onload = function() {
            console.log(xhr.responseText);
            var response = JSON.parse(xhr.responseText);
            if(xhr.status == 200) {
                ImageFileUploadError.style.display = "none";
                var content = "<p>All Files Uploaded Successfully</p>";
                ImageFileUploadSuccess.innerHTML = content;
                ImageFileUploadSuccess.style.display = "block";
            } else {
                ImageFileUploadSuccess.style.display = "none";
                ImageFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
            }
        }

        xhr.send(formData);
    }

    var ImageuploadMultipleFiles2 = document.querySelector('#ImageuploadMultipleFiles2');
    var ImageFileUploadInput2 = document.querySelector('#ImageFileUploadInput2');
    var ImageFileUploadError2 = document.querySelector('#ImageFileUploadError2');
    var ImageFileUploadSuccess2 = document.querySelector('#ImageFileUploadSuccess2');

    function EditImage(files,newsID) {
        var formData = new FormData();
        for(var index = 0; index < files.length; index++) {
            formData.append("file", files[index]);
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/rest/uploadImg/"+newsID);
        xhr.setRequestHeader('Authorization',Cookies.get('Cookie') );
        xhr.onload = function() {
            console.log(xhr.responseText);
            var response = JSON.parse(xhr.responseText);
            if(xhr.status == 200) {
                ImageFileUploadError2.style.display = "none";
                var content = "<p>All Files Uploaded Successfully</p>";
                ImageFileUploadSuccess2.innerHTML = content;
                ImageFileUploadSuccess2.style.display = "block";
            } else {
                ImageFileUploadSuccess2.style.display = "none";
                ImageFileUploadError2.innerHTML = (response && response.message) || "Some Error Occurred";
            }
        }

        xhr.send(formData);
    }



    ImageuploadMultipleFiles.addEventListener('submit', function(event){
        var files = multipleFileUploadInput.files;
        if(files.length === 0) {
            multipleFileUploadError.innerHTML = "Please select at least one file";
            multipleFileUploadError.style.display = "block";
        }
        ImageuploadMultipleFiles(files);
        event.preventDefault();
    }, true);

    $('#files').on('click', '.delFile', function (event) {
        var fileID = $(this).find('.fileID').text().trim();
        var r = confirm("Bạn có chắc chắn muốn xóa file này không?");
        if (r == true) {
            $.ajax({
                url: '/rest/deleteFile?id='+fileID,
                headers: {
                    "Authorization": Cookies.get('Cookie'),
                },
                dataType: 'json',
                type: 'POST',
                cache: false,
                contentType: 'application/json',

                success: function (data) {

                    if (data == true) {
                        $.toaster('Xóa thành công', 'Thông báo', 'success');
                        getNewByID(id);

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




});

