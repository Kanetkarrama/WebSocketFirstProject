package developerworks.ajax.store;

import java.math.BigDecimal;
import java.util.*;

/**
 * A very simple shopping Cart
 */
public class Cart {

    private HashMap<Item, Integer> contents;

    /**
     * Creates a new Cart instance
     */
    public Cart() {
        contents = new HashMap<Item, Integer>();
    }

    /**
     * Adds a named item to the cart
     *
     * @param itemName The name of the item to add to the cart
     */
    public void addItem(String itemCode) {

        Catalog catalog = new Catalog();

        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            int newQuantity = 1;
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                newQuantity += currentQuantity.intValue();
            }

            contents.put(item, new Integer(newQuantity));
        }
    }

    /**
     * Removes the named item from the cart if the quantity of the item in the cart is 1
     * Counts down the named item from the cart if the quantity is larger than 1
     * Does nothing if the item is not in the cart
     *
     * @param itemName Name of item to remove
     */
    public void removeItems(String itemCode) {
        
        Catalog catalog = new Catalog();
        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            int newQuantity = 1;
            
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                if(currentQuantity.intValue()>1){
                newQuantity = currentQuantity.intValue()-newQuantity;
                contents.put(item, new Integer(newQuantity));
                }else{
                contents.remove(new Catalog().getItem(itemCode));
                }
            }

        }
    }

    /**
     * @return JSON representation of cart contents
     */
   
    public String toJSON() {
        StringBuffer json = new StringBuffer();
        json.append("{");
// Time tag to capture the latest updates on the page
        json.append("\"generated\":\"" + System.currentTimeMillis() + "\"," + "\"Total\":\"" + getCartTotal() + "\"," + "\"Items\":[");

        int sizeContents = contents.size();
       
        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            sizeContents--;
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();
            
            json.append("{" + "\"Code\":\"" + item.getCode() + "\"," + "\"Name\":\"" + item.getName() + "\"," + "\"Quantity\":\"" + itemQuantity + "\"");

            if (sizeContents > 0) {
                json.append("},\n");
            } else {
                json.append("}\n");
            }
        }

        json.append("]}");
        return json.toString();
    }

    private String getCartTotal() {
        int total = 0;

        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();

            total += (item.getPrice() * itemQuantity);
        }

        return "$" + new BigDecimal(total).movePointLeft(2);
    }
}
