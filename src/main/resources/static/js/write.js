var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            alert($('#title').val());
            _this.save();
        });
    },

    save : function() {

       $.ajax({
            type : 'POST',
            url : '/board/write',
            data : {
                title : $('#title').val(),
                writer : $('#writer').val(),
                content : $('#content').val()
            },
            contentType : "application/json; charset=UTF-8",
            success : function(data) {
                alert("success");
            },
            error : function() {
                alert("error");
            }
        });
    }
};

main.init();