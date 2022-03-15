$(function () {
    $("#SendVCode").click(function () {
        if ($("#email").val()) {
            $.ajax({
                type: "POST",
                url: "/account/sendVCode",
                data: {
                    email: $("#email").val(),
                },
                success: function (data) {
                    alert("验证码发送成功，请注意查收。");
                },
            })
        } else {
            alert("邮箱发送失败");
        }
    });
});

