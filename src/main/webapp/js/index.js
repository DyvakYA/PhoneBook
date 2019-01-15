$(document).ready(function () {

    var user = JSON.parse(localStorage.getItem('currentUser'))
    var token = localStorage.getItem("token")
    console.log("Token is = " + localStorage.getItem("token"));
    console.log(user);



    $("#submit_contact_save").click(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/api/contacts/add",
            headers: {
                "Authorization": localStorage.getItem("token"),
            },
            data: JSON.stringify({
                firstName: $("#contact_firstName").val(),
                lastName: $("#contact_lastName").val(),
                middleName: $("#contact_middleName").val(),
                phoneNumberMobile: $("#contact_phoneNumberMobile").val(),
                phoneNumberHome: $("#contact_phoneNumberHome").val(),
                address: $("#contact_address").val(),
                email: $("#contact_email").val(),
                userId: user.id
            }),
            timeout: 100000,
            success: function (response) {
                location.reload();
            },
            error: function (e) {
            }
        });
    });


    var id;
    if(user === null || user === 'undefined'){
        id = 'null';
    }else{
        var id = user.id;
    }
    $("#search").click(function () {
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/api/contacts/search",
            headers: {
                "Authorization": localStorage.getItem("token"),
            },
            data: JSON.stringify({
                firstName: $("#search_firstName").val(),
                lastName: $("#search_lastName").val(),
                phoneNumberMobile: $("#search_phoneNumberMobile").val(),
                userId: id
            }),
            timeout: 100000,
            success: function (data) {

                var task_data = '';
                $.each(data, function (key, value) {
                    task_data += '<tr>';
                    task_data += '<td>' + value.lastName + '</td>';
                    task_data += '<td>' + value.firstName + '</td>';
                    task_data += '<td>' + value.middleName + '</td>';
                    task_data += '<td>' + value.phoneNumberMobile + '</td>';
                    task_data += '<td>' + value.phoneNumberHome + '</td>';
                    task_data += '<td>' + value.address + '</td>';
                    task_data += '<td>' + value.email + '</td>';
                    task_data += '<td>' +
                        '<button type="button" class="delete hide btn btn-link">' +
                        '<span class="glyphicon glyphicon-remove-circle">' +
                        '</button>' +
                        '</span>' +
                        '</td>';
                    task_data += '</tr>';
                });
                $('table.tasks tbody tr').remove();
                $('table.tasks').append(task_data);
                checkHideElements();
            },
            error: function (e) {
            }
        });
    });

    var contactsUrl = "http://localhost:8080/api/contacts"
    if (user != null && user !== "undefined") {
        contactsUrl = contactsUrl + "/" + user.id;
    }
    console.log(contactsUrl);

    $.ajax({
        url: contactsUrl,
        type: "GET",
        headers: {
            "Authorization": localStorage.getItem("token"),
        },
    }).then(function (data) {
        var task_data = '';
        $.each(data, function (key, value) {
            task_data += '<tr>';
            task_data += '<td class="id" >' + value.id + '</td>';
            task_data += '<td>' + value.lastName + '</td>';
            task_data += '<td>' + value.firstName + '</td>';
            task_data += '<td>' + value.middleName + '</td>';
            task_data += '<td>' + value.phoneNumberMobile + '</td>';
            task_data += '<td>' + value.phoneNumberHome + '</td>';
            task_data += '<td>' + value.address + '</td>';
            task_data += '<td>' + value.email + '</td>';
            task_data += '<td>' +
                '<button type="button" class="delete hide btn btn-link">' +
                '<span class="glyphicon glyphicon-remove-circle">' +
                '</button>' +
                '</span>' +
                '</td>';
            task_data += '</tr>';
        });
        $('table.tasks').append(task_data);
        checkHideElements();
    });

    $('table.tasks').on('click', '.delete', function () {
        var id = $(this).closest('tr').find('.id').text();
        var deleteLink = "http://localhost:8080/api/contacts/delete/" + id;

        $.ajax({
            type: "GET",
            url: deleteLink,
            headers: {
                "Authorization": localStorage.getItem("token"),
            },
            timeout: 100000,
            success: function (response) {
            },
            error: function (e) {
            }
        });

        console.log(id);
        $(this).closest('tr').remove();
    });

    $(".add_contact").click(function () {
        var task_data = '';
        task_data += '<tr>';
        task_data += '<td>  <input type="text" name="lastName" id="contact_lastName"></td>';
        task_data += '<td>  <input type="text" name="firstName" id="contact_firstName"> </td>';
        task_data += '<td>  <input type="text" name="middleName" id="contact_middleName"> </td>';
        task_data += '<td>  <input type="text" name="phoneNumberMobile" id="contact_phoneNumberMobile"> </td>';
        task_data += '<td>  <input type="text" name="phoneNumberHome" id="contact_phoneNumberHome"> </td>';
        task_data += '<td>  <input type="text" name="address" id="contact_address"> </td>';
        task_data += '<td>  <input type="text" name="email" id="contact_email"> </td>';
        task_data += '</tr>';
        $('table.tasks').append(task_data);
    });

    $("#logout").click(function () {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('token');
        console.log("logout");
        window.location.replace("/login.html");
    });

    function checkHideElements() {
        if (token === null || token === "undefined") {
            $(".hide").hide();
            $(".show").show();
        } else {
            $(".hide").show();
            $(".show").hide();
        }
    };
});

