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

    })

});

