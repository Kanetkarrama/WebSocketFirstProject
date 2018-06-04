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
 *
 * @author Rama
 */
@ServerEndpoint(value="/ShoppingCartendpoint")
public class ShoppingCart {
     private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
     private static Cart myCart = new Cart();
     
    @OnMessage //reveive msg
    public void broadcast(String obj, Session session) throws IOException, EncodeException {

        String itemCode="";
        String actionType = obj.substring(0,obj.indexOf("&"));
        String item = obj.substring(obj.indexOf("&")+1);
        
        String action = actionType.substring(actionType.indexOf("=")+1);
        itemCode = item.substring(item.indexOf("=")+1);
        
        if(action.equals("add")){
            myCart.addItem(itemCode);
        
        }else if(action.equals("remove")){
            myCart.removeItems(itemCode);
        }
        
        
        String cartJson = myCart.toJSON();
        for (Session peer : peers) {
            peer.getBasicRemote().sendObject(cartJson);
        }
    }
     @OnOpen
    public void onOpen (Session peer) throws IOException, EncodeException {
        peers.add(peer);
        String cartJson = myCart.toJSON();
        peer.getBasicRemote().sendObject(cartJson);
    }

    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }
}