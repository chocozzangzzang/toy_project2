$(document).ready(function() {

    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");

    $("#btn-notice-save").click(function () {

        var notice =
        {
            "noticeTitle" : $("#noticeTitle").val(),
            "noticeContent" : $("#noticeContent").val()
        }

        $.ajax({
            type : "post",
            url : "/notice/write",
            data : JSON.stringify(notice),
            beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
            contentType : "application/json; charset=UTF-8",
            success : function() {
                console.log("success");
                window.location.href = "/notice";
            },
            fail : function() {
                console.long("fail");
            }
        })

    })

})