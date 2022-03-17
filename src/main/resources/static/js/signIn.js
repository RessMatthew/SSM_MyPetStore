$(document).ready(function() {

    $('#loginThroughPhone').click(function (){

        $('#signInPasswordForm').hide();
        $('#signInPhoneForm').show();

    })

    $('#loginThroughPassword').click(function (){

        $('#signInPasswordForm').show();
        $('#signInPhoneForm').hide();

    })



})