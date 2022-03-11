package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.persistence.CartMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private CatalogService catalogService;

    public CartService(){

    }

    public void addItemByUsernameAndItemId(String username, Item item,boolean isInStock){
        CartItem cartItem = new CartItem();
        cartItem.setUsername(username);
        cartItem.setItem(item);
        cartItem.setQuantity(0);
        cartItem.setInStock(isInStock);
        cartItem.incrementQuantity();
        int result = cartMapper.insertItemByUsernameAndItemId(username,item.getItemId(),cartItem.isInStock(),cartItem.getQuantity(),cartItem.getTotal());
    }

    public void incrementItemByUsernameAndItemId(String username,String itemId){
        CartItem result = cartMapper.selectItemByUsernameAndItemId(username,itemId);
        result.setItem(itemMapper.getItem(itemId));
        result.incrementQuantity();
        cartMapper.updateItemByUsernameAndItemId(username,itemId, result.getQuantity(), result.getTotal());
    }

    public CartItem getCartItemByUsernameAndItemId(String username, String itemId){
        CartItem result = cartMapper.selectItemByUsernameAndItemId(username,itemId);
        //result.setItem(itemMapper.getItem(itemId));
        return cartMapper.selectItemByUsernameAndItemId(username,itemId);
    }

    public void removeCartItemByUsernameAndItemId(String username, String itemId){
        cartMapper.removeItemByUsernameAndItemId(username,itemId);
    }

    public void updateItemByItemIdAndQuantity(String username, String itemId, int quantity){
        CartItem result = cartMapper.selectItemByUsernameAndItemId(username,itemId);
        result.setItem(itemMapper.getItem(itemId));
        if(result != null) {
            result.updateQuantity(quantity);
            cartMapper.updateItemByUsernameAndItemId(username, itemId, result.getQuantity(), result.getTotal());
        }
    }

    public List<CartItem> selectItemByUsername(String username){
        List<CartItem> cartItemList=new ArrayList<>();

        List<Cart> cartList=cartMapper.selectItemByUsername(username);
        Iterator cartListIterato=cartList.iterator();



        for(int i=0;cartListIterato.hasNext()&&i<cartList.size();i++) {
            CartItem result = new CartItem();
            String itemId = cartList.get(i).getItemid();
            boolean isInStock = cartList.get(i).isInstock();
            int quantity = cartList.get(i).getQuantity();
            BigDecimal totalCost = cartList.get(i).getTotalcost();
            boolean pay = cartList.get(i).getPay();
            if(!pay) {
                result.setItem(catalogService.getItem(itemId));
                result.setInStock(isInStock);
                result.setQuantity(quantity);
                result.setTotal(totalCost);
                cartItemList.add(result);
            }
        }
//        while (cartListIterato.hasNext()) {
//            CartItem result = new CartItem();
//            Cart cartTemp = (Cart)cartListIterato.next();
//            String itemId = cartTemp.getItemid();
//            boolean isInStock = cartTemp.isInstock();
//            int quantity = cartTemp.getQuantity();
//            BigDecimal totalCost = cartTemp.getTotalcost();
//            boolean pay = cartTemp.getPay();
//            if(!pay) {
//                result.setItem(catalogService.getItem(itemId));
//                result.setInStock(isInStock);
//                result.setQuantity(quantity);
//                result.setTotal(totalCost);
//                cartItemList.add(result);
//            }
//        }
        return cartItemList;
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
        cartMapper.updateItemByItemIdAndPay(username,itemId,pay);
    }
}
