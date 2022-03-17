//忘记密码

$(document).ready(function() {


    $('#FGTPSW').hide();
    $("#login-box").hide();
    $("#shadow").hide();

    $('#signinpsw').on("focus", function (){
        $('#FGTPSW').show();
    })
    $('#signinpsw').on("blur",function (){
        $('#FGTPSW').hide();
    })

    $("#FGTPSW").mousedown(function () {
        $("#login-box").show();
        $("#shadow").show();
    });
//关闭登录框，点击关闭按钮关闭
    $("#closeLoginBox").click(function () {
        $("#login-box").hide();
        $("#shadow").hide();
    });
    $('#closeLoginBoxMAIL').click(function (){
        $('#login-box-email').hide();
        $("#shadow").hide();
    })

    $('#throughEmail').click(function (){
        $('#login-box').hide();
        $('#login-box-email').show();
    })

    $('#throughPhone').click(function (){
        $('#login-box').show();
        $('#login-box-email').hide();
    })



    $('#confirmFGTPSWMAIL').click(function (){

        var serializeArray = $("#FGTPSWformMAIL").serializeArray();

        $.ajax(
            {

                url     :"/account/passwordMSGMAIL",
                type    :"post",
                data    :serializeArray,
                success :function (data){
                    alert(data);

                    if(data==="新密码已经发送至邮箱，请注意查收"){
                        console.log(data);
                        $("#login-box-email").hide();
                        $("#shadow").hide();
                    }
                },
                clearForm:false,
                resetForm:false

            }
        );

    });

    $('#confirmFGTPSW').click(function (){

        var serializeArray = $("#FGTPSWform").serializeArray();

        $.ajax(
            {

                url     :"/account/passwordMSG",
                type    :"post",
                data    :serializeArray,
                success :function (data){
                    alert(data);

                    if(data==="新密码已经发送至手机，请注意查收"){
                        console.log(data);
                        $("#login-box").hide();
                        $("#shadow").hide();
                    }
                },
                clearForm:false,
                resetForm:false

            }
        );

    });



})