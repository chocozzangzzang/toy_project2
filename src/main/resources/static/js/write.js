
$(document).ready(function() {
    $("#btn-save").click(function() {

        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var tempData =
        {
            "title" : $("#title").val(),
            "content" : $("#content").val()
        };

        $.ajax({
                type : "post",
                url : "/board/write",
                data : JSON.stringify(tempData),
                beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
                contentType : "application/json; charset=UTF-8",
                success : function() {
                    window.location.href = "/board";
                }
        });
    })

    $("#btn-delete").click(function() {
        var writer = $("#writer").val();
        var nowUser = $("#nowId").val();
        var boardId = $("#boardId").val();

        if (writer === nowUser) {
            if (confirm("삭제하시겠습니까?")) {
                $.ajax({
                        type : "get",
                        url : "/board/delete?bid=" + boardId,
                        contentType : "json",
                        success : function () {
                            alert("게시글이 삭제되었습니다.");
                            window.location.href = "/board";
                       }
                });
            }
        } else {
            alert("현재 접속중인 아이디가 작성자와 다릅니다.");
        }
    })

    $("#btn-modify").click(function() {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var writer = $("#writer").val();
        var nowUser = $("#nowId").val();
        var boardId = $("#boardId").val();

        if (writer === nowUser) {
            if (confirm("수정하시겠습니까?")) {
                $.ajax({
                    type : "post",
                    url : "/board/modify",
                    data : JSON.stringify({"boardId" : boardId}),
                    beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
                    contentType : "application/json; charset=UTF-8",
                    success : function() {
                        window.location.href="/board/modifyPage?bid=" + boardId;
                    }
                });
            }
        } else {
             alert("현재 접속중인 아이디가 작성자와 다릅니다.");
        }
    })

    $("#btn-modify-confirm").click(function() {
       alert("here!!!!");
    })

    $("#btn-modify-send").click(function() {

        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var boardId = $("#boardId").val();
        var writer = $("#writer").val();
        var title = $("#title").val();
        var content = $("#content").val();
        var regTime = $("#regTime").val();
        var modTime = $("#modTime").val();

        var tempData = {
            "boardId" : boardId,
            "writer" : writer,
            "title" : title,
            "content" : content,
            "regTime" : regTime,
            "modTime" : modTime
        };

        if(confirm("수정하시겠습니까?")) {
            $.ajax({
                type : "post",
                url : "/board/modifyPage",
                data : JSON.stringify(tempData),
                beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
                contentType : "application/json; charset=UTF-8",
                success : function() {
                    alert("수정완료");
                    window.location.href="/board";
                }
            });
        }
    })

    $("#btn-comment-write").click(function() {

        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var nowUser = $("#nowUser").val();
        var comment = $("#comment").val();

        if (confirm("댓글을 작성하시겠습니까?")) {
            $.ajax({
                type : "post",
                url : "/comment/write",
                beforeSend : function(xhr) {xhr.setRequestHeader(header, token);},
                data : JSON.stringify({
                    "boardId" : $("#boardId").val(),
                    "commentWriter" : $("#nowUser").val(),
                    "comment" : $("#comment").val()
                }),
                contentType : "application/json; charset=UTF-8",
                success : function() {
                    alert("댓글이 작성되었습니다.");
                    window.location.href="/board/" + $("#boardId").val();
                }
            })
        }
    })

    var commentDeleteButtons = document.querySelectorAll(".btn-comment-delete");
    var commentIds = document.querySelectorAll(".commentId");
    var commentWriters = document.querySelectorAll(".commentWriter");
    var nowUser = $("#nowUser").val();
    var commentModifyButtons = document.querySelectorAll(".btn-comment-modify");

    for(let i = 0; i < commentDeleteButtons.length; i++) {

        commentDeleteButtons[i].addEventListener("click", function() {
            var commentWriter = commentWriters[i].value;
            var commentId = commentIds[i].value;

            if (nowUser === commentWriter) {
                if (confirm("댓글을 삭제하시겠습니까?")) {
                    $.ajax({
                        type : "get",
                        url : "/comment/delete?cid=" + commentId,
                        contentType : "json",
                        success : function() {
                            alert("삭제했습니다!!");
                            window.location.href = "/board/" + $("#boardId").val();
                        }
                    });
                }
            } else {alert("댓글 작성자가 아닙니다.");}

        });
    }

    for (let j = 0; j < commentModifyButtons.length; j++) {
        commentModifyButtons[j].addEventListener("click", function() {

            var commentWriter = commentWriters[j].value;
            var commentId = commentIds[j].value;

            if (nowUser === commentWriter) {
                if (confirm("댓글을 수정하시겠습니까?")) {
                    alert($("#comm_" + commentId).val());
                }
            } else {alert("댓글 작성자가 아닙니다.");}
        })

    }
});
