$(document).ready(function () {
    var user={};
    console.log(Cookies.get('Cookie'));
    if( Cookies.get('Cookie') != null && Cookies.get('Role') == "ROLE_ADMIN" ){
        window.location.href = '../index.html';
    }
    if( Cookies.get('Cookie') != null && Cookies.get('Role') == "ROLE_USER" ){
        window.location.href = '../user_site/index.html';
    }

    $(document).ajaxStart(function(){
        $("#wait").css("display", "block");
    });
    $(document).ajaxComplete(function(){
        $("#wait").css("display", "none");
    });

    $("#password").on('keypress', function (e) {
        if (e.which == 13) {

            var user = $("#username").val();
            var pass = $("#password").val();
            $.ajax({
                url: '/rest/login',
                dataType: 'json',
                type: 'POST',
                cache: false,
                contentType: 'application/json',
                data:  JSON.stringify({
                    userName: user,
                    password: pass
                }),

                success: function (data) {
                    Cookies.set('Cookie',data.token)
                    Cookies.set('UserName', data.userName);
                    Cookies.set('UserID', data.id);
                    Cookies.set('Role', data.role);
                    Cookies.set('HoVaTen',data.hovaten);
                    if(data.role == "ROLE_ADMIN"){
                        window.location.href = '../index.html';
                        // $.ajax({
                        //     url: '/rest/index',
                        //     type: 'GET',
                        //     cache: false,
                        //     headers: {
                        //         "Authorization": Cookies.get('Cookie'),
                        //     },
                        //     contentType: 'application/json',
                        //
                        //     success: function (data) {
                        //
                        //     },
                        //     error: function (data) {
                        //             $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'})
                        //     }
                        // })
                    }
                    else {
                        window.location.href = '../user_site/index.html';
                    }
                },
                error: function (data) {
                    if(data.responseText = "Wrong userId and password"){
                        $.toaster({message: 'Sai tên đăng nhập hoặc mật khẩu', title: 'Thất bại', priority: 'danger'});
                    }

                    else {
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }
                }
            })

        }
    })

    $("#btn-login").click(
        function () {
            var user = $("#username").val();
            var pass = $("#password").val();
            $.ajax({
                url: '/rest/login',
                dataType: 'json',
                type: 'POST',
                cache: false,
                contentType: 'application/json',
                data:  JSON.stringify({
                    userName: user,
                    password: pass
                }),

                success: function (data) {
                    Cookies.set('Cookie',data.token)
                    Cookies.set('UserName', data.userName);
                    Cookies.set('UserID', data.id);
                    Cookies.set('Role', data.role);
                    Cookies.set('HoVaTen',data.hovaten);
                    if(data.role == "ROLE_ADMIN"){
                        window.location.href = '../index.html';
                    }
                    else {
                        window.location.href = '../user_site/index.html';
                    }
                },
                error: function (data) {
                    if(data.responseText = "Wrong userId and password"){
                        $.toaster({message: 'Sai tên đăng nhập hoặc mật khẩu', title: 'Thất bại', priority: 'danger'});
                    }
                    else {
                        $.toaster({message: 'Có lỗi xảy ra: ', title: 'Thất bại', priority: 'danger'});
                    }
                }
            })
        });

});

