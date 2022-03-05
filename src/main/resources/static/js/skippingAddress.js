function shippingShow(shippingAddressRequired) {
    // var Shipping = document.getElementById("Shipping");
    //
    //
    // if(this.checked){
    // Shipping.innerHTML = "<table id='table-3'>\n" +
    // "\t<tr>\n" +
    // "\t\t<th colspan=2>Shipping Address</th>\n" +
    // "\t</tr>\n" +
    // "\t<tr>\n" +
    // "\t\t<td>First name:</td>\n" +
    // "\t\t<td>\n" +
    // "\t\t\t<input type=\"text\" name=\"order.shipToFirstName\" />\n"
    // }
    // else {
    // Shipping.innerHTML = null;
    // }

    // var check = document.getElementsByTagName('input')[0];
    // console.log(check.checked);//false


    //console.log(0);
    var Shipping = document.getElementById("Shipping");
    if(shippingAddressRequired.checked){
        //console.log(1);
        var ship_ToFirstName = document.getElementById("billToFirstName").value;
        var ship_ToLastName = document.getElementById("billToLastName").value;
        var ship_Address1 = document.getElementById("billAddress1").value;
        var ship_Address2 = document.getElementById("billAddress2").value;
        var ship_City = document.getElementById("billCity").value;
        var ship_State = document.getElementById("billState").value;
        var ship_Zip = document.getElementById("billZip").value;
        var ship_Country = document.getElementById("billCountry").value;
        Shipping.innerHTML = "<table id='table-3'>\n" +
            "\t<tr>\n" +
            "\t\t<th colspan=2>Shipping Address</th>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>First name:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipToFirstName\" value="+ship_ToFirstName+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>Last name:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipToLastName\" value="+ship_ToLastName+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>Address 1:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipAddress1\" value="+ship_Address1+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>Address 2:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipAddress2\" value="+ship_Address2+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>City:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipCity\" value="+ship_City+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>State:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipState\" value="+ship_State+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>Zip:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipZip\" value="+ship_Zip+">\n"+

            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td>Country:</td>\n" +
            "\t\t<td>\n" +
            "\t\t\t<input type=\"text\" name=\"shipCountry\" value="+ship_Country+">\<n></n>"


    }
    else{

        Shipping.innerHTML = null;
    }
}
