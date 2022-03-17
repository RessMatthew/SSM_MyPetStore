//登录，点击登录显示登录框

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



    $('#confirmFGTPSW').click(function (){

        var serializeArray = $("#FGTPSWform").serializeArray();
        console.log("asd");

        $.ajax(
            {

                url     :"/account/passwordMSG",
                type    :"post",
                data    :serializeArray,
                success :function (data){
                    alert(data);
                },
                clearForm:false,
                resetForm:false

            }
        );

    });



})