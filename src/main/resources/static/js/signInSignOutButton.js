/*
$(document).ready(function(){

    $("#signout").hide();

        $.ajax({
            type    :"GET",
            url     :"/account/signinsignout",
            success :function(data){
                // alert(data);
                if(data==="Exist"){
                    // window.alert(data.msg);
                    $('#signout').show();
                    $('#signin').hide();
                }else if(data==="Not Exist"){
                    // window.alert(data.msg);
                    $('#signout').hide();
                    $('#signin').show();
                }
            }
        });

})*/
