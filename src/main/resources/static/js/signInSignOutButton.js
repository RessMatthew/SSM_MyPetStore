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


                    //----------------------
                    $('#fast_signin').hide();
                    //----------------------

                }else if(data==="Not Exist"){
                    // window.alert(data.msg);
                    $('#signout').hide();
                    $('#signin').show();

                    //----------------------
                    $('#fast_signin').show();
                    //----------------------

                }
            }
        });

            // var user = '<%= session.getAttribute("user")%>';
            //
            // if(user!=null){
            //     $('#signout').show();
            //     $('#signin').hide();
            // }else{
            //     $('#signout').hide();
            //     $('#signin').show();
            // }
})