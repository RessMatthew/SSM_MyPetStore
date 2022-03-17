$(document).ready(function() {

    $('#signInPhoneForm').hide();

    $('#loginThroughPhone').click(function (){

        $('#signInPhoneForm').show();
        $('#signInPasswordForm').hide();

    })

    $('#loginThroughPassword').click(function (){

        $('#signInPasswordForm').show();
        $('#signInPhoneForm').hide();

    })



})