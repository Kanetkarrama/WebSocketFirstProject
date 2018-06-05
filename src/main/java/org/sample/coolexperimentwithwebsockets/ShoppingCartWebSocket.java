/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.coolexperimentwithwebsockets;

import developerworks.ajax.store.Cart;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * This is the websocketendpoint file. The whiteboard websocketendpoint is used as a reference 
 * @author Rama
 */
@ServerEndpoint(value="/ShoppingCartendpoint")
public class ShoppingCartWebSocket {
     private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
     // gloabal variable for cart so that the cart content can be passed across the peers
     private static Cart myCart = new Cart();
     
    @OnMessage //reveive msg vis sendText method from websocket.js
    public void broadcast(String obj, Session session) throws IOException, EncodeException {
        
        // Object input comes like "action=add&item=<itemcode>". This is set in cart.js and passed through sendText in websocket.js
        String itemCode="";
        String actionType = obj.substring(0,obj.indexOf("&"));
        String item = obj.substring(obj.indexOf("&")+1);
        // extract action and item code value from the message event input string
        String action = actionType.substring(actionType.indexOf("=")+1);
        itemCode = item.substring(item.indexOf("=")+1);
        // check if event requested by the user is add or remove
        if(action.equals("add")){
            myCart.addItem(itemCode);
        
        }else if(action.equals("remove")){
            myCart.removeItems(itemCode);
        }
        
        // update all peers with cart content using the json for cart.
        String cartJson = myCart.toJSON();
        for (Session peer : peers) {
            peer.getBasicRemote().sendObject(cartJson);
        }
    }
     @OnOpen
    public void onOpen (Session peer) throws IOException, EncodeException {
        // updates cart for a new peer if cart already has some content from already existing peer
        peers.add(peer);
        String cartJson = myCart.toJSON();
        peer.getBasicRemote().sendObject(cartJson);
    }

    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }
}