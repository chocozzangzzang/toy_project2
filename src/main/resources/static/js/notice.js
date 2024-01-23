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

    $("#btn-notice-delete").click(function () {

        var nowUser = $("#nowId").val();
        var noticeWriter = $("#noticeWriter").val();
        var noticeId = $("#nid").val();

        if (nowUser === noticeWriter) {
            if (confirm("삭제하시겠습니까?")) {
                $.ajax({
                    type : "delete",
                    url : "/notice/delete?nid=" + noticeId,
                    beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
                    contentType : "json",
                    success : function() {
                        alert("삭제되었습니다!!");
                        window.location.href = "/notice";
                    }
                })
            }
        } else {
            alert("작성자와 현재 유저가 다릅니다!!");
        }

    })

    $("#btn-notice-modify").click(function () {

        var nowUser = $("#nowId").val();
        var noticeWriter = $("#noticeWriter").val();
        var noticeId = $("#nid").val();

        if (nowUser === noticeWriter) {
            if (confirm("수정하시겠습니까?")) {
                window.location.href = "/notice/modifyPage?nid=" + noticeId;
            }
        } else {
            alert("작성자와 현재 유저가 다릅니다!!");
        }
    })

    $("#btn-notice-modifyConfirm").click(function () {

        var nowUser = $("#nowUser").val();
        var noticeWriter = $("#noticeWriter").val();
        var noticeModify = {
            "noticeId" : $("#nid").val(),
            "noticeWriter" : noticeWriter,
            "noticeModifyTitle" : $("#noticeTitle").val(),
            "noticeModifyContent" : $("#noticeContent").val()
        }

        if (confirm("해당 내용으로 수정하시겠습니까?")) {
            $.ajax({
                type : "put",
                url : "/notice/modify",
                data : JSON.stringify(noticeModify),
                beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
                contentType : "application/json; charset=UTF-8",
                success : function() {
                    alert("수정완료되었습니다!!");
                    window.location.href="/notice/" + $("#nid").val();
                }
            })
        }
    })

})