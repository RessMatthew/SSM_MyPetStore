package org.csu.mypetstore.domain;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private String username;
    private String itemId;
    private boolean instock;
    private int quantity;
    private BigDecimal totalCost;
    private boolean pay;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemid() {
        return itemId;
    }

    public void setItemid(String itemid) {
        this.itemId = itemid;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalcost() {
        return totalCost;
    }

    public void setTotalcost(BigDecimal totalcost) {
        this.totalCost = totalcost;
    }

    public boolean getPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public Map<String, CartItem> getItemMap() {
        return itemMap;
    }

    public List<CartItem> getItemList() {
        return itemList;
    }

    private final Map<String,CartItem> itemMap = Collections.synchronizedMap(new HashMap<String ,CartItem>());
    private final List<CartItem> itemList = new ArrayList<CartItem>();

    public Iterator<CartItem> getCartItems(){
        return itemList.iterator();
    }

    public List<CartItem> getCartItemList() {
        return itemList;
    }

    public int getNumberOfItems(){
        return itemList.size();
    }

    public Iterator<CartItem> getAllCartItems() {
        return itemList.iterator();
    }

    public boolean containItemId(String itemId){
        return itemMap.containsKey(itemId);
    }

    public void addItem(Item item, boolean isInStock){
        CartItem cartItem = (CartItem) itemMap.get(item.getItemId());
        if(cartItem == null){
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemId(),cartItem);
            itemList.add(cartItem);
        }
        cartItem.incrementQuantity();
    }

    public Item removeItemById(String itemId){
        CartItem cartItem = (CartItem) itemMap.remove(itemId);
        if(cartItem == null){
            return null;
        }
        else{
            itemList.remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemId(String itemId){
        CartItem cartItem = (CartItem) itemMap.get(itemId);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemId(String itemId, int quantity){
        CartItem cartItem = (CartItem) itemMap.get(itemId);
        cartItem.setQuantity(quantity);
    }

    public BigDecimal getSubTotal(){
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItem> items = getAllCartItems();
        while(items.hasNext()){
            CartItem cartItem = (CartItem) items.next();
            Item item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }
}
