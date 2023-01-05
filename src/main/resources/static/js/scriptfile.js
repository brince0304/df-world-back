$(window).scroll(function() {
    if ($(document).scrollTop() > 50) {
        $('.nav').addClass('affix');
        console.log("OK");
    } else {
        $('.nav').removeClass('affix');
    }
});

$(document).ready(function() {$('#loginModal').modal('show');
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
});


$(window).scroll(function() {
    if ($(document).scrollTop() > 50) {
        $('.nav').addClass('affix');
        console.log("OK");
    } else {
        $('.nav').removeClass('affix');
    }
});
$(document).ready(function() {
    var s = $("#navbar");
    var pos = s.position();

    $(window).scroll(function() {
        var windowpos = $(window).scrollTop();

        if (windowpos >= pos.top) {
            s.addClass("stick");
        } else {
            s.removeClass("stick");
        }
    });

});
function logoutAlert(){
    $.ajax({
        url: "/api/user/logout",
        type: "GET",
        success: function (data) {
            console.log(data);
            alert("로그아웃 되었습니다.");
            location.reload();
        }
    });
}

let nicknameCheck = false;
let emailCheck = false;
let usernameCheck = false;
let passwordCheck = false;

$("input[type=password]").keyup(function(){
    var ucase = new RegExp("[A-Z]+");
    var lcase = new RegExp("[a-z]+");
    var num = new RegExp("[0-9]+");

    if($("#password1").val().length >= 8){
        $("#8char").removeClass("glyphicon-remove");
        $("#8char").addClass("glyphicon-ok");
        $("#8char").css("color","#00A41E");
        passwordCheck = true;
    }else{
        $("#8char").removeClass("glyphicon-ok");
        $("#8char").addClass("glyphicon-remove");
        $("#8char").css("color","#FF0004");
        passwordCheck = false;
    }

    if(ucase.test($("#password1").val())){
        $("#ucase").removeClass("glyphicon-remove");
        $("#ucase").addClass("glyphicon-ok");
        $("#ucase").css("color","#00A41E");
        passwordCheck = true;
    }else{
        $("#ucase").removeClass("glyphicon-ok");
        $("#ucase").addClass("glyphicon-remove");
        $("#ucase").css("color","#FF0004");
        passwordCheck = false;
    }

    if(lcase.test($("#password1").val())){
        $("#lcase").removeClass("glyphicon-remove");
        $("#lcase").addClass("glyphicon-ok");
        $("#lcase").css("color","#00A41E");
        passwordCheck = true;
    }else{
        $("#lcase").removeClass("glyphicon-ok");
        $("#lcase").addClass("glyphicon-remove");
        $("#lcase").css("color","#FF0004");
        passwordCheck = false;
    }

    if(num.test($("#password1").val())){
        $("#num").removeClass("glyphicon-remove");
        $("#num").addClass("glyphicon-ok");
        $("#num").css("color","#00A41E");
        passwordCheck = true;
    }else{
        $("#num").removeClass("glyphicon-ok");
        $("#num").addClass("glyphicon-remove");
        $("#num").css("color","#FF0004");
        passwordCheck = false;
    }

    if($("#password1").val() == $("#password2").val()){
        $("#pwmatch").removeClass("glyphicon-remove");
        $("#pwmatch").addClass("glyphicon-ok");
        $("#pwmatch").css("color","#00A41E");
        passwordCheck = true;
    }else{
        $("#pwmatch").removeClass("glyphicon-ok");
        $("#pwmatch").addClass("glyphicon-remove");
        $("#pwmatch").css("color","#FF0004");
        passwordCheck = false;
    }

});

$('#usernameRegister').blur(function(){
    let username = $('#usernameRegister').val();
    if(username===''){return;}
    $.ajax({
        url: '/api/user/id',
        type: 'POST',
        data: JSON.stringify({
            "username": username
        }),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            if(data === 'false'){
                $("#usernameCheck").removeClass("glyphicon-ok");
                $("#usernameCheck").addClass("glyphicon-remove");
                $("#usernameCheck").css("color","#FF0004");
                usernameCheck = false;
            }
            else if(data=== 'true'){
                $("#usernameCheck").removeClass("glyphicon-remove");
                $("#usernameCheck").addClass("glyphicon-ok");
                $("#usernameCheck").css("color","#00A41E");
                usernameCheck = true;
            }
        }
    });
})
$('#nicknameRegister').blur(function(){
    let nickname = $('#nicknameRegister').val();
    if(nickname===''){return;}
    $.ajax({
        url: '/api/user/nickname',
        type: 'POST',
        data: JSON.stringify({
            "nickname": nickname
        }),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            if(data === 'false'){
                $("#nicknameCheck").removeClass("glyphicon-ok");
                $("#nicknameCheck").addClass("glyphicon-remove");
                $("#nicknameCheck").css("color","#FF0004");
                nicknameCheck = false;
            }
            else if(data=== 'true'){
                $("#nicknameCheck").removeClass("glyphicon-remove");
                $("#nicknameCheck").addClass("glyphicon-ok");
                $("#nicknameCheck").css("color","#00A41E");
                nicknameCheck = true;
            }
        }
    });
})
$('#emailRegister').blur(function(){
    let email = $('#emailRegister').val();
    if(email===''){return;}
    $.ajax({
        url: '/api/user/email',
        type: 'POST',
        data: JSON.stringify({
            "email": email
        }),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            if(data === 'false'){
                $("#emailCheck").removeClass("glyphicon-ok");
                $("#emailCheck").addClass("glyphicon-remove");
                $("#emailCheck").css("color","#FF0004");
                emailCheck = false;
            }
            else if(data=== 'true'){
                $("#emailCheck").removeClass("glyphicon-remove");
                $("#emailCheck").addClass("glyphicon-ok");
                $("#emailCheck").css("color","#00A41E");
                emailCheck = true;
            }
        }
    });
})

function registerSubmit(){
    let username = $('#usernameRegister').val();
    let nickname = $('#nicknameRegister').val();
    let email = $('#emailRegister').val();
    let password = $('#password1').val();
    let password2 = $('#password2').val();

    if(password!==password2 || !passwordCheck || !usernameCheck || !nicknameCheck || !emailCheck){
        alert('입력값을 확인해주세요');
        return;
    }
    $.ajax({
        url: '/api/user/',
        type: 'POST',
        data: JSON.stringify({
            "userId": username,
            "nickname": nickname,
            "email": email,
            "password": password,
            "passwordCheck" : password2
        }),
        contentType: "application/json; charset=utf-8",
        success: function(data){
            alert(data+'님의 회원가입을 환영합니다.');
            location.href='/';
        },
        error: function(data){
            alert('회원가입에 실패했습니다. 다시 시도해주세요.');
        }

    });
}
