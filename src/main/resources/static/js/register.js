// var xhr;
// function checkUsername() {
//     var username = document.getElementById("username").value;
//     xhr = new XMLHttpRequest();
//     xhr.onreadystatechange = process;
//     xhr.open("GET", "usernameIsExist?username="+username, true);
//     xhr.send(null);
// }
//
// function process() {
//     if(xhr.readyState === 4){
//         if(xhr.status === 200){
//             var responseInfo = xhr.responseText;
//             var msg = document.getElementById("isExistInfo");
//             if(responseInfo === "Exist"){
//                 msg.classList.add("errormsg");
//                 msg.innerText = '用户名不可用';
//             }
//             else if(responseInfo === "Not Exist"){
//                 msg.classList.remove("errormsg");
//                 msg.classList.add("okmsg");
//                 msg.innerText = '用户名可用';
//             }
//         }
//     }
// }

$(document).ready(function(){

    $('#iEI').hide();

    $('#username').on('blur',function (){

        $.ajax({
            type    :"GET",
            url     :"/account/usernameIsExist?username="+$('#username').val(),
            success :function(data){

                 // console.alert(data);

                $('#iEI').show();

                if(data === "Empty"){
                    $('#iEI').hide();
                }else if(data === "Not Exist"){
                    // $('#isExistInfo').classList.remove("errormsg");
                    // $('#isExistInfo').classList.add("okmsg");

                    //$('#isExistInfo').text('用户名可用');
                    $('#isExistInfo').attr("class","oktips").text('用户名可用');
                }else if(data === "Exist"){
                    // $('#isExistInfo').classList.add("errormsg");

                    //$('#isExistInfo').text('用户名不可用');
                    $('#isExistInfo').attr("class","errortips").text('用户名不可用');

                }
            },
            error :function (){
                window.alert('data');
            }
        });

    });

    $('#phoneCode').off("click"); //解除绑定点击事件

    $('#phoneCode').unbind("click");//移除绑定点击事件

    $('#phoneCode').unbind(); //移除所有绑定事件

    $('#phoneCode').on("click",function (){

        var p = $('#phoneNumber').val();
        var data = "phoneNumber="+ p;
        console.log(data);
        $.ajax({

            type    :"POST",
            url     :"/account/phoneVCode",
            data    :data,

            success :function (data){
                console.log("success Phone VCode");



            },
            error :function (data){
                window.alert(data);
            }


        });
    });


});

