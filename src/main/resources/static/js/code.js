var stompClient = null;

$(document).ready(function () {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/error', function (user_error) {
            setErrorInDiv(user_error);
        });
        stompClient.subscribe('/topic/transfer', function (user_code) {
            showCode(JSON.parse(user_code.body).code);
        });
        stompClient.subscribe('/topic/checkcode', function (result_code) {
            var result = JSON.parse(result_code.body);
            setTestsResults(result);
        });
    });
});

function sendCode() {
    var code = editor.getValue();
    stompClient.send("/app/send", {}, JSON.stringify({'code': code}));
}

function sendResult() {
    $('#resultgroup').css('visibility', 'visible');
    var code = editor.getValue();
    stompClient.send("/app/result", {}, JSON.stringify({'code': code}));
}

function showCode(message) {
    editor.setValue(message);
}

function setErrorInDiv(user_error) {
    document.getElementById('user_error').innerHTML = user_error.body;
}

function setTestsResults(result) {
    for (var i = 0; i < result.length; i++) {
        var testID = 'test_' + (i + 1);
        if (result[i]) $('#' + testID).css('background-color', '#6bde6b');
        else $('#' + testID).css('background-color', '#e44a49');
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    // window.setInterval(function(){
    //     sendCode();
    // }, 30000);
    $("#send").click(function () {
        sendCode();
    });
    $("#resultCode").click(function () {
        sendResult();
    });
});

$(window).unload(function () {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
});