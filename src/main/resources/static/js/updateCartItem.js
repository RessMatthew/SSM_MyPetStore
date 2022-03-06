var xhr;
function updateItem(item) {
    xhr = new XMLHttpRequest();
    xhr.onreadystatechange = update;
    xhr.open("POST", "/cart/updateCartItem?itemId="+item.name+"&quantity="+item.value, true);
    xhr.send(null);
}

function update() {
    if(xhr.readyState === 4){
        if(xhr.status === 200){
            var result = xhr.responseText;
            var newItem = eval("("+result+")");
            var isRemoved = newItem.isRemoved;
            var m = document.getElementById("m");

            if(isRemoved == "false"){
                var item = document.getElementsByName(newItem.itemId);
                item.innerText = newItem.quantity;
                document.getElementById("itemtotalcost"+newItem.itemId).innerHTML=newItem.html;
            }
            else if(isRemoved == "true"){
                var row = document.getElementById(newItem.itemId);
                document.getElementById("carttable").deleteRow(row.rowIndex);
            }
        }
    }
}