
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
                    window.location.href="/board";
                }
        });
    })
});
