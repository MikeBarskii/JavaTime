var stompClient = null;

$(document).ready(function () {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/transfer', function (usercode) {
            showCode(JSON.parse(usercode.body).code);
        });
        stompClient.subscribe('/topic/checkcode', function (resultcode) {
            var result = JSON.parse(resultcode.body);
            for (var i = 0; i < result.length; i++) {
                var testID = 'test_' + (i + 1);
                if (result[i]) $('#' + testID).css('background-color', '#6bde6b');
                else $('#' + testID).css('background-color', '#e44a49');
            }
        });
    });
});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

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

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    window.setInterval(function(){
        sendCode();
    }, 30000);
    $("#send").click(function () {
        sendCode();
    });
    $("#resultCode").click(function () {
        sendResult();
    });
});

