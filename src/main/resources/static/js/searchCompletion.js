
function strToJson(str){
    var json = (new Function("return"+str))();
    return json;
}

$(document).ready(function(){

$("#searchbar").autocomplete({

    source:
    // "searchThis?keyword="+$("#searchbar").val(),
        function (request,response){

            //console.log("123");


        $.ajax({
            type    :"GET",
            url     :"/catalog/searchThis?keyword="+$("#searchbar").val(),

            success :function (data){

                     //console.log(strToJson(data));
                    response(strToJson(data));

            }
        })

    },

    minLength: 1,
    // select: function( event, ui ) {
    //     log( "Selected: " + ui.item.value + " aka " + ui.item.id );
    // }

})

})


