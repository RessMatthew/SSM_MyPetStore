package org.csu.mypetstore.domain;

import java.math.BigDecimal;

public class CartItem {
    private String username;
    private Item item;
    private int quantity;
    private boolean instock;
    private BigDecimal totalCost;
    private boolean pay;
    public BigDecimal getTotal() {
        return totalCost;
    }

    public void setTotal(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public boolean isInStock() {
        return instock;
    }

    public void setInStock(boolean inStock) {
        this.instock = inStock;
    }

    private void calculateTotal(){
        if(item != null && item.getListPrice() != null){
            totalCost = item.getListPrice().multiply(new BigDecimal(quantity));
        }
        else{
            totalCost = null;
        }
    }

    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }

    public void updateQuantity(int quantity){
        this.quantity = quantity;
        calculateTotal();
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
