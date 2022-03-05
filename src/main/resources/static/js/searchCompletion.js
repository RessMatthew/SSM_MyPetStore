

// $(document).ready(function (){
//     $('#searchbar').on('change',function (){
//         $.ajax({
//
//             type    :'GET',
//             url     :'searchThis?#keyword='+this.value,
//             success :function(data){
//                 $("#searchbar").autocomplete({
//                     source      :data,
//                     minLength   :1
//                 });
//
//             }
//         });
//     })
// })

// $(document).ready(function (){
//


function strToJson(str){
    var json = (new Function("return"+str))();
    return json;
}



$("#searchbar").autocomplete({

    source:
    // "searchThis?keyword="+$("#searchbar").val(),
        function (request,response){



        $.ajax({
            type    :"GET",
            url     :"searchThis?keyword="+$("#searchbar").val(),

            success :function (data){

                    // console.log(strToJson(data));
                    response(strToJson(data));

            }
        })

    },

    minLength: 1,
    // select: function( event, ui ) {
    //     log( "Selected: " + ui.item.value + " aka " + ui.item.id );
    // }

})

//
// })

// $("#searchbar").autocomplete({
//     source: function( request, response ) {
//         $.ajax({
//             type    :"post",
//             url     : "searchThis"+this.value,
//             success : function( data ) {
//                 response( $.map( data, function( item ) {
//                     return {   //lable为下拉列表显示数据源。value为选中放入到文本框的值，这种方式可以自定义显示
//                         lable:item.userName,
//                         value: item.userName
//                     }
//                 }));
//             }
//         });
//     },
//     minLength: 1,
//     select: function( event, ui ) { //移动键盘上下键，自动将下拉列表的数据放入到文本框，不过不写这个配置也是可以的，默认就行，具体这个还不知道是做什么
//         $("#userName").val(ui.item.userName);
//     }
// });


// $(function(){
//     $("#searchbar").autocomplete({
//         source      :"searchThis"+this.value,
//         minLength   :1
//     });
// });


