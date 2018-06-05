<%-- 
    Document   : index
    Created on : 2 Jun, 2018, 3:29:00 AM
    Author     : Rama
--%>
<%@ page import="java.util.*" %>
<%@ page import="developerworks.ajax.store.*" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>CoolExperimentWithWebSockets</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <!-- onload tag calls addToCart method from cart with 0 as parameter. This makes sure that the cart has updated value if browser is refreshed-->
    <body onload="addToCart(0)">
        <h1>Collaborative Shopping Cart App</h1>
    <!--    <div id="output"></div> - for testing -->
       <div style="float: left; width: 500px">
<h2>Catalog</h2>
<table border="1">
  <thead><th>Name</th><th>Description</th><th>Price</th><th></th></thead>
  <tbody>
  <%
    for (Iterator<Item> I = new Catalog().getAllItems().iterator() ; I.hasNext() ; ) {
      Item item = I.next();
  %>
    <tr><td><%= item.getName() %></td><td><%= item.getDescription() %></td><td><%= item.getFormattedPrice() %></td><td><button onclick="addToCart('<%= item.getCode() %>')">Add to Cart</button></td><td><button onclick="removeFromCart('<%= item.getCode() %>')">Remove from Cart</button></td></tr>
    <% } %>
  </tbody>
</table>
<!-- specifies location of the cart and shows total = $0.00 by default-->
<div style="position: absolute; top: 0px; right: 0px; width: 250px">
<h2>Cart Contents</h2>
<ul id="contents">
</ul>
Total cost: <span id="total">$0.00</span>
</div>
</div>
  <!-- Have the browser fetch two javascript files. -->
    <script type="text/javascript" src="websocket.js"></script>
    <script type="text/javascript" language="javascript" src="cart.js"></script>
    </body>

</html>
