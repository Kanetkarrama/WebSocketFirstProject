/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var wsUri = "ws://" + document.location.host + document.location.pathname + "ShoppingCartendpoint";
// ShoppingCartendpoint shoudl be same as @ServerEndpoint value
var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) { onError(evt) };
//calls onError function if an error is encountered
websocket.onmessage = function(evt) { onMessage(evt) };
// calls onmessage method on an event and passes the evt value

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
// End test functions
websocket.onmessage = function(itemCode) { onMessage(itemCode) };

function sendText(json) {
    console.log(json);// testing the json sting
    websocket.send(json);
}
                
function onMessage(evt) {
    //console.log(evt);
    updateCart(evt.data);
}

