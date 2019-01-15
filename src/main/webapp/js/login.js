$(document).ready(function () {

    $(window).load(function () {
        console.log("Token is =" + localStorage.getItem("token"));
    });

    $('.message a').click(function () {
        $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });

    $(".registration").click(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/api/users/registration",
            data: JSON.stringify({
                username: $("#username").val(),
                password: $("#password").val(),
                fullName: $("#fullName").val()
            }),
            timeout: 100000,
            success: function (response) {
                window.location.reload();
            },
            error: function (e) {
            }
        });
    });

    $(".login").click(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/api/users/login",
            data: JSON.stringify({
                username: $("#username2").val(),
                password: $("#password2").val()
            }),
            timeout: 100000,
            success: function (response) {
                window.localStorage.setItem('token', response['token']);
                window.localStorage.setItem('currentUser', JSON.stringify(response['user']));
                console.log(response['user']);

                console.log(localStorage.getItem("currentUser"));

                $.ajaxSetup({
                    headers: {'Authorization': localStorage.getItem('token')},
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader('Authorization', localStorage.getItem('token'));
                    }
                });
                window.location.replace("/");
            },
            error: function (e) {
            }
        });
    });
});