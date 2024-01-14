
$(document).ready(function() {
    $("#btn-save").click(function() {

        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var tempData =
        {
            "title" : $("#title").val(),
            "content" : $("#content").val()
        }

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

        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");

        var writer = $("#writer").val();
        var nowUser = $("#nowId").val();
        var boardId = $("#boardId").val();

        if (writer === nowUser) {
            $.ajax({
                type : "get",
                url : "/board/delete?bid=" + boardId,
                contentType : "json",
                success : function () {
                    alert("게시글이 삭제되었습니다.");
                    window.location.href = "/board";
                }
            });

        } else {
            alert("현재 접속중인 아이디가 작성자와 다릅니다.");
        }
    })
});
