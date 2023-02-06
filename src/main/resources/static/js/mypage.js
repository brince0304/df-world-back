function checkNotification(){
    let notificationId = $('.toast-link').attr('data-id');
    $.ajax({
        url: '/notifications/'+notificationId,
        type: 'GET',
        success: function(data){
            location.href=data;
        },
        error: function(data){
            alert('알림을 확인할 수 없습니다.');
        }
    })
}
let subscribeUrl = "http://localhost:8080/sub";
$(document).ready(function() {
    if ($('#session').val()!=='anonymousUser') {
        let userId = $('#session').val()
        let eventSource = new EventSource(subscribeUrl + "?userId=" + userId);
        console.log("eventSource : " + eventSource);
        console.log("userId : " + userId);
        eventSource.addEventListener("event", function(event) {
            var myToastEl = document.getElementById('liveToast')
            var myToast = bootstrap.Toast.getOrCreateInstance(myToastEl)
            if(myToast._isShown){
                myToast.hide();
            }
            let message = event.data;
            $('.toast-body').text(message);
            myToast.show();
        })
        eventSource.addEventListener("notificationId", function(event) {
            $('.toast-link').attr('data-id', event.data);
        })
        eventSource.addEventListener("error", function(event) {
            eventSource.close()
        })
    }
})
let emailCheck = false;
let nicknameCheck = false;
let passwordBooleanCheck = false;

function changeProfileIcon(number) {
    if (confirm("아이콘을 변경하시겠습니까?")) {
        const iconName = "icon_char_" + number + ".png";
        $.ajax({
            url: "/users?profileIcon=" + iconName,
            type: "PUT",
            data: {
                iconNumber: number
            },
            success: function (data) {

                alert("아이콘 변경이 완료되었습니다.");
                location.reload();
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    } else {
        return;
    }
}

// Mobile Navigation
if ($('#nav-menu-container').length) {
    var $mobile_nav = $('#nav-menu-container').clone().prop({
        id: 'mobile-nav'
    });
    $mobile_nav.find('> ul').attr({
        'class': '',
        'id': ''
    });
    $('body').append($mobile_nav);
    $('body').prepend('<button type="button" id="mobile-nav-toggle"><i class="fa fa-bars"></i></button>');
    $('body').append('<div id="mobile-body-overly"></div>');
    $('#mobile-nav').find('.menu-has-children').prepend('<i class="fa fa-chevron-down"></i>');

    $(document).on('click', '.menu-has-children i', function (e) {
        $(this).next().toggleClass('menu-item-active');
        $(this).nextAll('ul').eq(0).slideToggle();
        $(this).toggleClass("fa-chevron-up fa-chevron-down");
    });

    $(document).on('click', '#mobile-nav-toggle', function (e) {
        $('body').toggleClass('mobile-nav-active');
        $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
        $('#mobile-body-overly').toggle();
    });

    $(document).click(function (e) {
        var container = $("#mobile-nav, #mobile-nav-toggle");
        if (!container.is(e.target) && container.has(e.target).length === 0) {
            if ($('body').hasClass('mobile-nav-active')) {
                $('body').removeClass('mobile-nav-active');
                $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
                $('#mobile-body-overly').fadeOut();
            }
        }
    });
} else if ($("#mobile-nav, #mobile-nav-toggle").length) {
    $("#mobile-nav, #mobile-nav-toggle").hide();
}

// Smooth scroll for the menu and links with .scrollto classes
$('.nav-menu a, #mobile-nav a, .scrollto').on('click', function () {
    if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
        var target = $(this.hash);
        if (target.length) {
            var top_space = 0;

            if ($('#header').length) {
                top_space = $('#header').outerHeight();

                if (!$('#header').hasClass('header-fixed')) {
                    top_space = top_space - 20;
                }
            }

            $('html, body').animate({
                scrollTop: target.offset().top - top_space
            }, 1500, 'easeInOutExpo');

            if ($(this).parents('.nav-menu').length) {
                $('.nav-menu .menu-active').removeClass('menu-active');
                $(this).closest('li').addClass('menu-active');
            }

            if ($('body').hasClass('mobile-nav-active')) {
                $('body').removeClass('mobile-nav-active');
                $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
                $('#mobile-body-overly').fadeOut();
            }
            return false;
        }
    }
});

$('#nicknameUpdate').blur(function () {
    const nickname = $('#nicknameUpdate').val();
    if(nickname.length < 2 || nickname.length > 10){
        nicknameCheck = false;
        $('#nicknameUpdateForm').removeClass('was-validated');
        $('#nicknameUpdate').removeClass("is-valid");
        $('#nicknameUpdate').addClass("is-invalid");
        $('#nicknameUpdateVal').hide();
        $('#nicknameUpdateNonVal').text("닉네임은 2자 이상 10자 이하로 입력해주세요.");
        $('#nicknameUpdateNonVal').show();
        return;
    }
    $.ajax({
        url: "/users/check?nickname=" + nickname,
        type: "GET",
        success: function (data) {
            if (data === "true") {
                $('#nicknameUpdateForm').addClass('was-validated');
                $('#nicknameUpdate').removeClass("is-invalid");
                $('#nicknameUpdate').addClass("is-valid");
                $('#nicknameUpdateNonVal').hide();
                $('#nicknameUpdateVal').show();
                nicknameCheck = true;
            } else if (data === "false") {
                $('#nicknameUpdateForm').removeClass('was-validated');
                $('#nicknameUpdate').removeClass("is-valid");
                $('#nicknameUpdate').addClass("is-invalid");
                $('#nicknameUpdateVal').hide();
                $('#nicknameUpdateNonVal').text("이미 사용중인 닉네임입니다.");
                $('#nicknameUpdateNonVal').show();

                nicknameCheck = false;
            }

        }, error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
});

$('#emailUpdate').blur(function () {
    const email = $('#emailUpdate').val();
    emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if(email === ""){
        $('#emailUpdateForm').removeClass('was-validated');
        $('#emailUpdate').removeClass("is-valid");
        $('#emailUpdate').addClass("is-invalid");
        $('#emailUpdateVal').hide();
        $('#emailUpdateNonVal').text("이메일을 입력해주세요.");
        $('#emailUpdateNonVal').show();
        emailCheck = false;
        return;
    }
    if(email.match(emailRegExp) === null){
        $('#emailUpdateForm').removeClass('was-validated');
        $('#emailUpdate').removeClass("is-valid");
        $('#emailUpdate').addClass("is-invalid");
        $('#emailUpdateVal').hide();
        $('#emailUpdateNonVal').text("이메일 형식이 올바르지 않습니다.");
        $('#emailUpdateNonVal').show();
        emailCheck = false;
        return;
    }
    $.ajax({
        url: "/users/check?email=" + email,
        type: "GET",
        success: function (data) {
            if (data === "true") {
                $('#emailUpdateForm').addClass('was-validated');
                $('#emailUpdate').removeClass("is-invalid");
                $('#emailUpdate').addClass("is-valid");
                $('#emailUpdateNonVal').hide();
                $('#emailUpdateVal').show();
                emailCheck = true;
            } else if (data === "false") {
                $('#emailUpdateForm').removeClass('was-validated');
                $('#emailUpdate').removeClass("is-valid");
                $('#emailUpdate').addClass("is-invalid");
                $('#emailUpdateVal').hide();
                $('#emailUpdateNonVal').text("이미 사용중인 이메일입니다.");
                $('#emailUpdateNonVal').show();
                emailCheck = false;
            }

        }, error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }
    });
});

$('#passwordCheckUpdate').blur(function () {
    const passwordRegEx = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
    const password = $('#passwordUpdate').val();
    let passwordCheck = $('#passwordCheckUpdate').val();
    if(password.match(passwordRegEx) === null){
        $('#passwordCheckUpdateForm').removeClass('was-validated');
        $('#passwordCheckUpdate').removeClass("is-valid");
        $('#passwordCheckUpdate').addClass("is-invalid");
        $('#passwordCheckUpdateVal').hide();
        $('#passwordUpdateNonVal').text("비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.");
        $('#passwordUpdateNonVal').show();
        passwordBooleanCheck = false;
        return;
    }
    if (password !== passwordCheck) {
        $('#passwordCheckUpdateForm').removeClass('was-validated');
        $('#passwordCheckUpdate').removeClass("is-valid");
        $('#passwordCheckUpdate').addClass("is-invalid");
        $('#passwordCheckUpdateVal').hide();
        $('#passwordUpdateNonVal').text("비밀번호가 일치하지 않습니다.");
        $('#passwordCheckUpdateNonVal').show();
        $('#passwordUpdate').removeClass("is-valid");
        $('#passwordUpdate').addClass("is-invalid");
        passwordBooleanCheck = false;
    } else {
        $('#passwordCheckUpdateForm').addClass('was-validated');
        $('#passwordCheckUpdate').addClass("is-valid");
        $('#passwordCheckUpdate').removeClass("is-invalid");
        $('#passwordUpdate').addClass("is-valid");
        $('#passwordUpdate').removeClass("is-invalid");
        $('#passwordCheckUpdateVal').show();
        $('#passwordCheckUpdateNonVal').hide();
        passwordBooleanCheck = true;
    }
});

$('#passwordUpdate').blur(function () {
    const password = $('#passwordUpdate').val();
    const passwordCheck = $('#passwordCheckUpdate').val();
    const passwordRegEx = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;


    if(password.match(passwordRegEx) === null){
        $('#passwordUpdateForm').removeClass('was-validated');
        $('#passwordUpdate').removeClass("is-valid");
        $('#passwordUpdate').addClass("is-invalid");
        $('#passwordUpdateVal').hide();
        $('#passwordUpdateNonVal').text("비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상 20자 이하로 입력해주세요.");
        $('#passwordUpdateNonVal').show();
        passwordBooleanCheck = false;
        return;
    }

    if (password !== passwordCheck) {
        $('#passwordCheckUpdateForm').removeClass('was-validated');
        $('#passwordCheckUpdate').removeClass("is-valid");
        $('#passwordCheckUpdate').addClass("is-invalid");
        $('#passwordCheckUpdateVal').hide();
        $('#passwordUpdateNonVal').text("비밀번호가 일치하지 않습니다.");
        $('#passwordCheckUpdateNonVal').show();
        $('#passwordUpdate').removeClass("is-valid");
        $('#passwordUpdate').addClass("is-invalid");
        passwordBooleanCheck = false;
    } else {
        $('#passwordCheckUpdateForm').addClass('was-validated');
        $('#passwordCheckUpdate').addClass("is-valid");
        $('#passwordCheckUpdate').removeClass("is-invalid");
        $('#passwordUpdate').addClass("is-valid");
        $('#passwordUpdate').removeClass("is-invalid");
        $('#passwordUpdateVal').show();
        $('#passwordUpdateNonVal').hide();
        passwordBooleanCheck = true;
    }
});

function updatePassword() {
    const password = $('#passwordUpdate').val();
    if (!passwordBooleanCheck) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    } else if (confirm("비밀번호를 변경하시겠습니까?")) {
        $.ajax({
            url: "/users?password=" + password,
            type: "PUT",
            success: function (data) {
                alert("비밀번호가 변경되었습니다.");
                $('#passwordUpdate').val("");
                $('#passwordCheckUpdate').val("");
                $('#passwordCheckUpdateForm').removeClass('was-validated');
                $('#passwordCheckUpdate').removeClass("is-valid");
                passwordBooleanCheck = false;
                location.reload();
            }, error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }

}

function updateEmail() {
    const email = $('#emailUpdate').val();
    if (!emailCheck) {
        alert("중복된 이메일입니다.");
        return;
    } else if (confirm("이메일을 변경하시겠습니까?")) {
        $.ajax({
            url: "/users?email=" + email,
            type: "PUT",
            success: function (data) {
                alert("이메일이 변경되었습니다.");
                $('#emailUpdate').val("");
                $('#emailUpdateForm').removeClass('was-validated');
                $('#emailUpdate').removeClass("is-valid");
                emailCheck = false;
                location.reload();

            }, error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }

}

function updateNickname() {
    const nickname = $('#nicknameUpdate').val();
    if (!nicknameCheck) {
        alert("중복된 닉네임입니다.");
        return;
    } else if (confirm("닉네임을 변경하시겠습니까?")) {
        $.ajax({
            url: "/users?nickname=" + nickname,
            type: "PUT",
            success: function (data) {
                alert("닉네임이 변경되었습니다.");
                $('#nicknameUpdate').val("");
                $('#nicknameUpdateForm').removeClass('was-validated');
                $('#nicknameUpdate').removeClass("is-valid");
                nicknameCheck = false;
                location.reload();

            }, error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }

}

$('#searchModal').on('show.bs.modal', function (e) {
    search_modal_type = $(e.relatedTarget).attr('data-type');
});

function getServerName(serverId) {
    switch (serverId) {
        case "cain":
            return "카인";
            break;
        case "diregie":
            return "디레지에";
            break;
        case "siroco":
            return "시로코";
            break;
        case "prey":
            return "프레이";
            break;
        case "casillas":
            return "카시야스";
            break;
        case "hilder":
            return "힐더";
            break;
        case "anton":
            return "안톤";
            break;
        case "bakal":
            return "바칼";
            break;
    }
}

function searchCharacter() {
    const characterName = $('#characterName').val();
    const serverId = $('#serverId').val();
    $.ajax({
        url: "/users/characters/",
        data: {
            characterName: characterName,
            serverId: serverId,
            page : 0
        },
        type: "GET",
        success: function (data) {
            if (data == null) {
                alert("캐릭터를 찾을 수 없습니다.");
                return;
            } else {
                setSearchModalResult(data);
            }
        }, error: function (request, status, error) {
        }
    });
}

function searchCharacterForAddAdventure() {
    const characterName = $('#characterNameAdventure').val();
    const serverId = $('#serverIdAdventure').val();
    $.ajax({
        url: "/users/characters/",
        data: {
            characterName: characterName,
            serverId: serverId,
            page : 0
        },
        type: "GET",
        success: function (data) {
            if (data == null) {
                alert("캐릭터를 찾을 수 없습니다.");
                return;
            } else {
                setSearchModalResultForAddAdventure(data);
            }
        }, error: function (request, status, error) {
        }
    });
}

function setSearchModalResultForAddAdventure(list) {
    let html = "";
    if (list.length > 0) {
        html = '<table class="table table-striped table-hover" style="text-align: center;">';
        html += '<thead style="font-weight: bold;"><tr><td>서버</td><td>닉네임</td>';
        if (list[0].level != undefined) {
            html += '<td>레벨</td>';
        }
        html += '<td>직업</td></tr></thead>';
        html += '<tbody>';
        for (var i = 0; i < list.length; i++) {
            html += ' <tr class="search-item" data-server="' + list[i].serverId + '" data-id="' + list[i].characterId + '" data-name="' + list[i].characterName + '" onclick="setCharacterForAdventure(  \'' + list[i].serverId + '\', \'' + list[i].characterId + '\' ,\'' + list[i].characterName + '\', \'' + list[i].adventureName + '\')">';
            html += '<td>' + getServerName(list[i].serverId) + "</td>";
            html += '<td>' + list[i].characterName + "</td>";
            if (list[i].level != undefined) {
                html += '<td>' + list[i].level + "</td>";
            }
            html += '<td>' + list[i].jobGrowName + "</td>";
            html += '</tr>';
        }
        html += '</tbody>';
        html += "</table>";
        $('#searchBodyForAdventure').html(html);
    } else {
        html = '<h3 style="text-align: center;">검색 결과가 없습니다.</h3>';
        $('#searchBodyForAdventure').html(html);
    }
}




function setSearchModalResult(list) {
    let html = "";
    if (list.length > 0) {
        html = '<table class="table table-striped table-hover" style="text-align: center;">';
        html += '<thead style="font-weight: bold;"><tr><td>서버</td><td>닉네임</td>';
        if (list[0].level != undefined) {
            html += '<td>레벨</td>';
        }
        html += '<td>직업</td></tr></thead>';
        html += '<tbody>';
        for (var i = 0; i < list.length; i++) {
            html += ' <tr class="search-item" data-server="' + list[i].serverId + '" data-id="' + list[i].characterId + '" data-name="' + list[i].characterName + '" onclick="setCharacter( \'' + list[i].serverId + '\', \'' + list[i].characterId + '\')">';
            html += '<td>' + getServerName(list[i].serverId) + "</td>";
            html += '<td>' + list[i].characterName + "</td>";
            if (list[i].level != undefined) {
                html += '<td>' + list[i].level + "</td>";
            }
            html += '<td>' + list[i].jobGrowName + "</td>";
            html += '</tr>';
        }
        html += '</tbody>';
        html += "</table>";
        $('#searchBody').html(html);
    } else {
        html = '                                <div style="text-align: center; margin-top: 50px; margin-bottom: 50px">' +
            '                                            <i class="fa-solid fa-skull-crossbones" style="font-size: 60px"></i>' +
            '                                            <div style="font-size: 20px; font-weight: 500; color: #999999; margin-top: 20px">캐릭터가 존재하지 않습니다.</div>' +
            '                                        </div>';
        $('#searchBody').html(html);
    }
}

function setCharacter(serverId, characterId) {

    if (confirm("캐릭터를 추가하시겠습니까?")) {
        $.ajax({
            url: "/users/characters",
            data: {
                serverId: serverId,
                characterId: characterId
            },
            type: "POST",
            success: function (data) {
                alert("캐릭터가 추가되었습니다.");
                $('#searchModal').modal('hide');
                location.reload();
            }, error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}

function setCharacterForAdventure(serverId, characterId,characterName,adventureName) {
    if(adventureName===null){alert('해당 캐릭터가 100레벨 이상이 아닙니다. 다시 시도해주세요.')
        $('#charSearchModalForAdventure').modal('hide');
        return;
    }
    if (confirm(adventureName+"모험단의 "+characterName+"캐릭터를 대표 캐릭터로 등록하시겠습니까? 인증 절차가 필요합니다.")) {
        $.ajax({
            url: "/users/adventure/validate",
            type: "GET",
            success: function (data) {
                html = '<h3 style="text-align: center;">'+adventureName+' 모험단</h3>';
                html += '<h3 style="text-align: center;">'+characterName+' 캐릭터가 등록된 서버에</h3>';
                html += '<h3 style="text-align: center;">'+data.randomJobName+' 직업으로</h3>';
                html += '<h3 style="text-align: center;">'+data.randomString+' 닉네임을 가진 캐릭터를 생성 후 확인을 눌러주세요. </h3>';
                html += '<h3 style="text-align: center;">인증이 완료되면 대표 캐릭터로 등록됩니다.</h3>';
                html += '<h3 style="text-align: center;">인증이 완료되지 않으면 대표 캐릭터로 등록되지 않습니다.</h3>';
                html += '<button type="button" class="btn btn-primary" onclick="validateCharacterForAdventure(\''+serverId+'\',\''+characterId+'\',\''+adventureName+'\',\''+data.randomJobName+'\',\''+data.randomString+'\')">확인</button>';
                $('#searchBodyForAdventure').html(html);
            }, error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}

function validateCharacterForAdventure(serverId, characterId,adventureName,randomJobName,randomString) {
    const data = {
        serverId: serverId,
        representCharacterId: characterId,
        adventureName: adventureName,
        randomJobName: randomJobName,
        randomString: randomString
    };
    $.ajax({
        url: "/users/adventure",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        type: "POST",
        success: function (data) {
            alert("모험단이 등록되었습니다.");
            $('#charSearchModalForAdventure').modal('hide');
            location.reload();
        }, error: function (request, status, error) {
            alert("인증에 실패하였습니다. 다시 시도해주세요.");
            $('#charSearchModalForAdventure').modal('hide');
            location.reload();            }
    });
}

let logPage = 0;
let logSortBy = ";"

function getUserLogs(page) {
    if (logPage !== 0 && page === -1 && page !== null) {
        logPage--;
    } else if (page != null && page === 1) {
        logPage++;
    } else if (page === null) {
        logPage = 0;
    }
    $.ajax({
        url: "/users/logs/",
        type: "GET",
        data: {
            page: logPage,
            type: "log",
        },
        success: function (data) {
            console.log(page);
            const next = document.getElementById('nextUserLogBtn');
            const prev = document.getElementById('prevUserLogBtn');
            next.disabled = false;
            prev.disabled = false;
            $('#notification').html("");
            let html = "";
            if (data.content.length > 0) {
                $(data.content).each(function () {
                    html += '<div data-id="' + this.id + '" style="margin:5px">';
                    html += '<a style="text-decoration-line: none; color: #535353;" href="/boards/' + this.boardId + '">';
                    html += '<span class="boardTitle">'+this.logContent+'</span>'
                    html += '<span style="color:grey; font-size: 11px; float:right; display:inline-flex;" >' + this.createdDate + '</span>';
                    html += '</a>';
                    html += '</div>';
                    html += '</div>';
                    html += ' <div class="bar" style="width: 100%; height: 2px; background-color: #e6e6e6;"></div>';
                });
                $('#notification').html(html);
            } else {
                html = '                                <div style="text-align: center; margin-top: 50px; margin-bottom: 50px">' +
                    '                                            <i class="fa-solid fa-bell-slash" style="font-size: 60px"></i>' +
                    '                                            <div style="font-size: 20px; font-weight: 500; color: #999999; margin-top: 20px">알림이 존재하지 않습니다.</div>' +
                    '                                        </div>';
                $('#notification').html(html);
            }
            if (data.last === true) {
                $("#nextUserLogBtn").attr('onclick', '');
                $('#nextUserLogBtn').addClass('text-secondary');
                $('#nextUserLogBtn').removeClass('text-primary');
            } else {
                $("#nextUserLogBtn").attr('onclick', 'getUserLog(1)');
                $('#nextUserLogBtn').removeClass('text-secondary');
            }
            if (data.number === 0) {
                $("#prevUserLogBtn").attr('onclick', '');
                $('#prevUserLogBtn').addClass('text-secondary');
            } else {
                $("#prevUserLogBtn").attr('onclick', 'getUserLog(-1)');
                $('#prevUserLogBtn').removeClass('text-secondary');
            }
            $('#userLogPage').text(logPage + 1);
        }, error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

        }
    });
}


function getUserCommentLogs(page) {
    if (commentPage !== 0 && page === -1 && page !== null) {
        commentPage--;
    } else if (page != null && page === 1) {
        commentPage++;
    } else if (page === null) {
        commentPage = 0;
    }
    if (page === 'like') {
        commentPage = 0;
        commentSortBy = "like";
        $('#sortByComment').text(" 좋아요순");
        $('#sortByComment').removeClass('fa fa-calendar-o');
        $('#sortByComment').addClass('fa fa-heart-o');
    } else if (page === 'date') {
        commentPage = 0;
        commentSortBy = "";
        $('#sortByComment').text(" 최신순");
        $('#sortByComment').removeClass('fa fa-heart-o');
        $('#sortByComment').addClass('fa fa-calendar-o');
    }
    $.ajax({
        url: "/users/logs/",
        type: "GET",
        data: {
            page: commentPage,
            type: "comment",
            sortBy: commentSortBy
        },
        success: function (data) {
            console.log(page);
            const next = document.getElementById('nextCommentsBtn');
            const prev = document.getElementById('prevCommentsBtn');
            next.disabled = false;
            prev.disabled = false;
            $('#commentLog').html("");
            let html = "";
            if (data.content.length > 0) {
                $(data.content).each(function () {
                    if (this.deleted === false) {
                        html += '<div data-id="' + this.id + '" style="margin:5px">';
                        html += '<a style="text-decoration-line: none; color: #535353;" href="/boards/' + this.boardId + '">';
                        if (this.commentContent.length < 35) {
                            html += '<span class="boardTitle">' + this.commentContent + '</span>'
                        } else {
                            html += '<span class="commentOverflow" > ' + this.commentContent + '</span>'
                        }
                        html +=
                            '                                   <div style="display:inline-block; float:right;">' +
                            '                                       <i class="fa fa-heart-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.commentLikeCount + '</span>' +
                            '                                       <i class="fa fa-comments-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.childrenCommentsSize + '</span>'
                        html += '<span style="color:grey; font-size: 11px;" >' + this.createdAt + '</span>';
                        html += '</a>';
                        html += '</div>';
                        html += '</div>';
                        html += ' <div class="bar" style="width: 100%; height: 2px; background-color: #e6e6e6;"></div>';
                    } else {
                        html += '<div data-id="' + this.id + '" style="margin:5px">';
                        html += '<i class="fa fa-exclamation" aria-hidden="true" style="color:darkred"></i>';
                        if (this.commentContent.length < 35) {
                            html += '<span class="boardTitle" style="color:darkred">' + this.commentContent + '</span>'
                        } else {
                            html += '<span class="commentOverflow"  style="color:darkred"> ' + this.commentContent + '</span>'
                        }
                        html +=
                            '                                   <div style="display:inline-block; float:right;">' +
                            '                                       <i class="fa fa-heart-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.commentLikeCount + '</span>' +
                            '                                       <i class="fa fa-comments-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.childrenCommentsSize + '</span>'
                        html += '<span style="color:grey; font-size: 11px;" >' + this.createdAt + '</span>';
                        html += '</div>';
                        html += '</div>';
                        html += ' <div class="bar" style="width: 100%; height: 2px; background-color: #e6e6e6;"></div>';
                    }
                });
                $('#commentLog').html(html);
            } else {
                html = '                                <div style="text-align: center; margin-top: 50px; margin-bottom: 50px">' +
                    '                                            <i class="fa-solid fa-comment-slash" style="font-size: 60px"></i>' +
                    '                                            <div style="font-size: 20px; font-weight: 500; color: #999999; margin-top: 20px">댓글이 존재하지 않습니다.</div>' +
                    '                                        </div>';
                $('#commentLog').html(html);
            }
            if (data.last === true) {
                $("#nextCommentsBtn").attr('onclick', '');
                $('#nextCommentsBtn').addClass('text-secondary');
                $('#nextCommentsBtn').removeClass('text-primary');
            } else {
                $("#nextCommentsBtn").attr('onclick', 'getUserCommentLogs(1)');
                $('#nextCommentsBtn').removeClass('text-secondary');
            }
            if (data.number === 0) {
                $("#prevCommentsBtn").attr('onclick', '');
                $('#prevCommentsBtn').addClass('text-secondary');
            } else {
                $("#prevCommentsBtn").attr('onclick', 'getUserCommentLogs(-1)');
                $('#prevCommentsBtn').removeClass('text-secondary');
            }
            $('#commentPage').text(commentPage + 1);
        }, error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);

        }
    });
}

let boardPage = 0;
let commentPage = 0;
let boardSortBy = "";
let commentSortBy = "";

function getUserBoardLogs(page) {
    if (boardPage !== 0 && page === -1 && page !== null) {
        boardPage = boardPage - 1;
    } else if (page != null && page === 1) {
        boardPage = boardPage + 1;
    } else if (page === null) {
        boardPage = 0;
    }
    if (page === 'like') {
        boardSortBy = "like";
        boardPage = 0;
        $('#sortByBoard').text(' 좋아요순');
        $('#sortByBoard').removeClass('fa fa-calendar-o');
        $('#sortByBoard').removeClass('fa fa-eye');
        $('#sortByBoard').removeClass('fa fa-comments-o');
        $('#sortByBoard').addClass('fa fa-heart-o');

    } else if (page === 'commentCount') {
        boardSortBy = "commentCount";
        boardPage = 0;
        $('#sortByBoard').text(' 코멘트순');
        $('#sortByBoard').removeClass('fa fa-calendar-o');
        $('#sortByBoard').removeClass('fa fa-eye');
        $('#sortByBoard').removeClass('fa fa-heart-o');
        $('#sortByBoard').addClass('fa fa-comments-o');

    } else if (page === 'view') {
        boardSortBy = "view";
        boardPage = 0;
        $('#sortByBoard').text(' 조회수순');
        $('#sortByBoard').removeClass('fa fa-calendar-o');
        $('#sortByBoard').removeClass('fa fa-heart-o');
        $('#sortByBoard').removeClass('fa fa-comments-o');
        $('#sortByBoard').addClass('fa fa-eye');
    } else if (page === 'date') {
        boardSortBy = "";
        boardPage = 0;
        $('#sortByBoard').text(' 최신순');
        $('#sortByBoard').removeClass('fa fa-heart-o');
        $('#sortByBoard').removeClass('fa fa-eye');
        $('#sortByBoard').removeClass('fa fa-comments-o');
        $('#sortByBoard').addClass('fa fa-calendar-o');
    }
    $.ajax({
        url: "/users/logs/",
        type: "GET",
        data: {
            page: boardPage,
            type: "board",
            sortBy: boardSortBy
        },
        success: function (data) {
            const next = document.getElementById('nextBoardsBtn');
            const prev = document.getElementById('prevBoardssBtn');
            next.disabled = false;
            prev.disabled = false;
            let html = "";
            $('#boardLogs').html("");
            if (data.content.length > 0) {
                $(data.content).each(function () {
                    if (this.deleted === false) {
                        html += '<tr data-id="' + this.id + '" style="margin:5px">';
                        html += '<a style="text-decoration-line: none; color: #535353;" href="/boards/' + this.id +'">';
                        if (this.character != null) {
                            html += '<td class="td-header" style="padding-right:0"><div><div class="myPage-character">'
                            html += '<img class="text-dark ' + this.character.imgStyleClassName + '"  src="' + this.character.characterImageUrl + '"/></div>';
                            html += '<i class="fa fa-plug" style="margin-left:2px"> </i>';
                            html += '<span class="badge bg-transparent text-dark" >' + this.character.characterName + '</span></div></td>';
                        } else {
                            html += '<td class="td-header" style="padding-right:0"><div><div class="myPage-character">';
                            html += '</div>';
                            html += '<i class="fa fa-plug" style="color: black; margin-left:2px"> </i>';
                            html += '<span class="badge bg-transparent text-dark" >캐릭터없음</span></div></td>';
                        }
                        if (this.boardTitle.length < 25) {
                            html += '<td class="td-body" style="padding-left:0; padding-right:0"><div><span class="boardTitle">' + this.boardTitle + '</span></div></td>';
                        } else {
                            html += '<td class="td-body" style="padding-left:0; padding-right:0"><div><span class="boardTitleOverflow" > ' + this.boardTitle + '</span></div></td>';
                        }
                        html +=
                            '                                   <td class="td-footer" style="padding-left:0"><div>' +
                            '                                       <i class="fa fa-heart-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.boardLikeCount + '</span>' +
                            '                                       <i class="fa fa-comment-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.commentCount + '</span>'
                        html += '<span style="color:grey; font-size: 11px;" >' + this.createdAt + '</span>';
                        html += '</div></a>';
                        html += '</td>';
                        html += '</tr>';
                    } else {
                        html += '<tr data-id="' + this.id + '" style="margin:5px">';
                        if (this.character != null) {
                            html += '<td class="td-header" style=" padding-right:0"><div><div class="myPage-character">'
                            html += '<img class="text-dark ' + this.character.imgStyleClassName + '"  src="' + this.character.characterImageUrl + '"/></div>';
                            html += '<i class="fa fa-exclamation" style="margin-left:5%; color:darkred"> </i>';
                            html += '<span class="badge bg-transparent"  style="color:darkred">삭제됨</span></div></td>';
                        } else {
                            html += '<td class="td-header" style=" padding-right:0"><div><div class="myPage-character">';
                            html += '</div>';
                            html += '<i class="fa fa-exclamation" style="color: darkred; margin-left:2px"> </i>';
                            html += '<span class="badge bg-transparent" style="color:darkred" >삭제됨</span></div></td>';
                        }
                        if (this.boardTitle.length < 25) {
                            html += '<td class="td-body" style="padding-left:0; padding-right:0"><div><span class="boardTitle" style="color:darkred">' + this.boardTitle + '</span></div></td>';
                        } else {
                            html += '<td class="td-body" style="padding-left:0; padding-right:0"><div><span class="boardTitleOverflow" style="color:darkred" > ' + this.boardTitle + '</span></div></td>';
                        }
                        html +=
                            '                                   <td class="td-footer"  style="padding-left:0"><div>' +
                            '                                       <i class="fa fa-heart-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.boardLikeCount + '</span>' +
                            '                                       <i class="fa fa-comment-o" style=""> </i>' +
                            '                                       <span class="badge bg-transparent text-black"' +
                            '                                             >' + this.commentCount + '</span>'
                        html += '<span style="color:grey; font-size: 11px;" >' + this.createdAt + '</span>';
                        html += '</div></td>';
                        html += '</tr>';
                    }
                });

                $('#boardLog').html(html);
            } else {
                html = '                                <div style="text-align: center; margin-top: 50px; margin-bottom: 50px">' +
                    '                                            <i class="fa-solid fa-paperclip" style="font-size: 60px"></i>' +
                    '                                            <div style="font-size: 20px; font-weight: 500; color: #999999; margin-top: 20px">게시글이 존재하지 않습니다.</div>' +
                    '                                        </div>';
                $('#boardLog').html(html);
            }
            if (data.last === true) {
                $("#nextBoardsBtn").attr('onclick', '');
                $('#nextBoardsBtn').addClass('text-secondary');
                $('#nextBoardsBtn').removeClass('text-primary');
            } else {
                $("#nextBoardsBtn").attr('onclick', 'getUserBoardLogs(1)');
                $('#nextBoardsBtn').removeClass('text-secondary');
            }
            if (data.number === 0) {
                $("#prevBoardssBtn").attr('onclick', '');
                $('#prevBoardssBtn').addClass('text-secondary');
            }else{
                $("#prevBoardssBtn").attr('onclick', 'getUserBoardLogs(-1)');
                $('#prevBoardssBtn').removeClass('text-secondary');
            }
            $('#boardPage').text(boardPage + 1);
        }, error: function (request, status, error) {
            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
        }, done: function (data) {

        }
    });
}


function deleteCharacter(serverId, characterId) {
    if (confirm("캐릭터를 삭제하시겠습니까?")) {
        $.ajax({
            url: "/users/characters",
            data : {
                serverId : serverId,
                characterId : characterId
            },
            type: "DELETE",
            success: function (data) {
                alert("캐릭터가 삭제되었습니다.");
                location.reload();
            }, error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }
        });
    }
}

$(document).ready(function () {
    $('#charServerId').change(function () {
        const serverId = $(this).val();
        $('#charServerId').val(getServerName(serverId));
    });
})

function pic() {
    var bgm = ['/images/icon_char/bg_char.jpeg', '/images/icon_char/bg_char_02.png', '/images/icon_char/bg_char_03.png'];
    $('.characterCard').css({
        'background': 'url(' + bgm[Math.floor(Math.random() * bgm.length)] + ') no-repeat',
        'background-position': 'center',
        'background-size': 'cover'
    });
}

pic();
$(function () {
    $('#characterName').keypress(function (e) {
        if (e.keyCode == 13) {
            searchCharacter();

        }
    });


});

function logoutAlert() {
    $.ajax({
        url: "/users/logout",
        type: "POST",
        success: function (data) {
            console.log(data);
            alert("로그아웃 되었습니다.");
            location.reload();
        }
    });
}