package developerworks.ajax.servlet;

import developerworks.ajax.store.Cart;
import javax.servlet.http.*;

import java.util.Enumeration;

public class CartServlet extends HttpServlet {

    /** 
     * Servlet Request Handling
     * Responds to an XMLHttpRequest
     * Updates Cart, and outputs XML representation of contents
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        //To get the header of the HttpServletRequest
        Enumeration headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String header = (String) headers.nextElement();
            System.out.println(header + ": " + req.getHeader(header));
        }

        
        Cart cart = getCartFromSession(req);
        //Function getParameter() is to get the POST request's body.
        String action = req.getParameter("action");
        String item = req.getParameter("item");

        if ((action != null) && (item != null)) {
            //The below if clauses are to add or remove item from the cart 
            if ("add".equals(action)) {
                cart.addItem(item);

            } else if ("remove".equals(action)) {
                cart.removeItems(item);

            }
        }
        //To serilize the Cart state to XML
        //String cartXml = cart.toXml();
        
        String cartJson = cart.toJSON();
      
        res.setContentType("application/json");
             
        //To write JSON to response
        res.getWriter().write(cartJson);
        
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        // Bounce to post, for debugging use
        // Hit this servlet directly from the browser to see XML
        doPost(req, res);
    }

    // return a Cart object of the curent session. If the HttpServletRequest doesn't have a cart, 
    //create a new Cart object to return
    private Cart getCartFromSession(HttpServletRequest req) {

        HttpSession session = req.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }
}
