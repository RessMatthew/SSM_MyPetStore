package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartDAO;
import org.csu.mypetstore.persistence.impl.CartDAOImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class CartService {
    private CartDAO cartDAO;

    public CartService(){
        cartDAO = new CartDAOImpl();
    }

    public void addItemByUsernameAndItemId(String username, Item item,boolean isInStock){
        CartItem cartItem = new CartItem();
        cartItem.setUsername(username);
        cartItem.setItem(item);
        cartItem.setQuantity(0);
        cartItem.setInStock(isInStock);
        cartItem.incrementQuantity();
        int result = cartDAO.insertItemByUsernameAndItemId(username,item.getItemId(),cartItem.isInStock(),cartItem.getQuantity(),cartItem.getTotal());
    }

    public void incrementItemByUsernameAndItemId(String username,String itemId){
        CartItem result = cartDAO.selectItemByUsernameAndItemId(username,itemId);
        result.incrementQuantity();
        cartDAO.updateItemByUsernameAndItemId(username,itemId, result.getQuantity(), result.getTotal());
    }

    public CartItem getCartItemByUsernameAndItemId(String username, String itemId){
        return cartDAO.selectItemByUsernameAndItemId(username,itemId);
    }

    public void removeCartItemByUsernameAndItemId(String username, String itemId){
        cartDAO.removeItemByUsernameAndItemId(username,itemId);
    }

    public void updateItemByItemIdAndQuantity(String username, String itemId, int quantity){
        CartItem result = cartDAO.selectItemByUsernameAndItemId(username,itemId);
        if(result != null) {
            result.updateQuantity(quantity);
            cartDAO.updateItemByUsernameAndItemId(username, itemId, result.getQuantity(), result.getTotal());
        }
    }

    public List<CartItem> selectItemByUsername(String username){
        return cartDAO.selectItemByUsername(username);
    }

    public BigDecimal getSubTotal(List<CartItem> cartItemList){
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItem> items = cartItemList.iterator();
        while(items.hasNext()){
            CartItem cartItem = (CartItem) items.next();
            Item item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }

    public void updateItemByItemIdAndPay(String username, String itemId, boolean pay){
        cartDAO.updateItemByItemIdAndPay(username,itemId,pay);
    }
}
