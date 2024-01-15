
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
});
