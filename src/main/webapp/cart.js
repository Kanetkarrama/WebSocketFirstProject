/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *  This cide is from mccarthy article.
 */


// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 * The function serves as a Onclick Handler Function responsible for updating the 
 * state of the cart through an Ajax call. 
 */
function addToCart(itemCode) {
    sendText("action=add&item=" + itemCode);
    // function in websocket.js
}

function removeFromCart(itemCode) {
    sendText("action=remove&item=" + itemCode);
}

/* The parameter of updateCart function is json object 
 * Update the the shopping cart presented in the web page by parsing the json object
 */


function updateCart(cartJSON){
    var cart = JSON.parse(cartJSON);
   
    var generated =cart.generated;
    
    if(generated > lastCartUpdate){
        lastCartUpdate = generated;
        // Clear the HTML list used to display the cart contents
        var contents = document.getElementById("contents");
        
        contents.innerHTML = "";
      
         var item = cart.Items;
         
         for (var i=0;i<item.length;i++){
             var name = item[i].Name;
             var quantity = item[i].Quantity;
             
             // Create and add a list item HTML element for this cart item
            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(name + " x " + quantity));
            contents.appendChild(listItem);
         }
        
    }
    
    // Update the cart's total using the value from the cart document
    document.getElementById("total").innerHTML = cart.Total;  
}


