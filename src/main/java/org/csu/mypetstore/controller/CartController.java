package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/viewCart")
    public String viewCart(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user != null) {
            String username = user.getUsername();
            List<CartItem> cart = cartService.selectItemByUsername(username);


            if (cart == null) {
                cart = new ArrayList<CartItem>();
            }

            //用于传购物车Item每个的库存量  暂未实现 双条件 th:each
            Iterator cartIterator = cart.iterator();
            List<Integer> qtyList = new ArrayList<>();
            while(cartIterator.hasNext()){
                CartItem cartItem = (CartItem) cartIterator.next();
                Item item = cartItem.getItem();
                int qty=catalogService.getInventoryQuantity(item.getItemId());
                qtyList.add(qty);
            }
            Iterator qtyListIterator = qtyList.iterator();
            model.addAttribute("qtyListIterator",qtyListIterator);

            session.setAttribute("cart",cart);
            model.addAttribute("cart", cart);
            model.addAttribute("user",user);
            return "/cart/Cart";
        }
        else{
            String msg="请先登录后再查看购物车";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }
    }

    @GetMapping("/addItemToCart")
    public String addItemToCart(String workingItemId,HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){model.addAttribute("user",user);}

        if(user == null){
            String msg = "请先登录再加入购物车！";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }
        else {
            String username = user.getUsername();
            CartItem cartItem = cartService.getCartItemByUsernameAndItemId(username, workingItemId);

            if (cartItem != null) {
                if(!cartItem.isPay()) {
                    cartService.incrementItemByUsernameAndItemId(username, workingItemId);
                }
                else {
                    cartService.updateItemByItemIdAndPay(username, workingItemId, false);
                    cartService.updateItemByItemIdAndQuantity(username, workingItemId, 1);
                }
            } else {
                boolean isInStock = catalogService.isItemInStock(workingItemId);
                Item item = catalogService.getItem(workingItemId);
                cartService.addItemByUsernameAndItemId(username, item, isInStock);
            }
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<CartItem>();
            }
            cart = cartService.selectItemByUsername(username);
            Iterator cartIterator = cart.iterator();
            List<Integer> qtyList = new ArrayList<>();
            while(cartIterator.hasNext()){
                CartItem cartItem2 = (CartItem) cartIterator.next();
                Item item = cartItem2.getItem();
                int qty=catalogService.getInventoryQuantity(item.getItemId());
                qtyList.add(qty);
            }
            Iterator qtyListIterator = qtyList.iterator();
            model.addAttribute("qtyListIterator",qtyListIterator);
            session.setAttribute("cart", cart);
            model.addAttribute("cart", cart);
            return "/cart/Cart";
        }
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String itemId,HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){model.addAttribute("user",user);}
        if(user == null){
            String msg = "Please sign in first.！";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }
        else {
            String username = user.getUsername();
            CartItem cartItem = cartService.getCartItemByUsernameAndItemId(username, itemId);
            if (cartItem == null) {
                String msg="Attempted to remove all null CartItem from Cart.";
                model.addAttribute("msg",msg);
                return "common/Error";
            } else {
                cartService.removeCartItemByUsernameAndItemId(username, itemId);
                List<CartItem> cart = cartService.selectItemByUsername(username);
                Iterator cartIterator = cart.iterator();
                List<Integer> qtyList = new ArrayList<>();
                while(cartIterator.hasNext()){
                    CartItem cartItem2 = (CartItem) cartIterator.next();
                    Item item = cartItem2.getItem();
                    int qty=catalogService.getInventoryQuantity(item.getItemId());
                    qtyList.add(qty);
                }
                Iterator qtyListIterator = qtyList.iterator();
                model.addAttribute("qtyListIterator",qtyListIterator);
                session.setAttribute("cart", cart);
                model.addAttribute("cart",cart);
                return "/cart/Cart";
            }
        }
    }

    @PostMapping("/updateCartItem")
    public void updateCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user;

        HttpSession session = request.getSession();
        user = (User) session.getAttribute("user");
        String username = user.getUsername();
        String itemId = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");
        int quantity = 0;


        System.out.println();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(quantityStr == "" || quantityStr.equals(null)){
            cartService.removeCartItemByUsernameAndItemId(username, itemId);
            out.write("{\"isRemoved\":\""+true+"{\"itemId\":\"" + itemId + "\"}");
        }
        else {
            quantity = Integer.parseInt(quantityStr);
            System.out.println(itemId+"数量"+quantity);
            if(quantity == 0){
                cartService.removeCartItemByUsernameAndItemId(username, itemId);
                out.write("{\"isRemoved\":\"" + true + "\",\"itemId\":\"" + itemId + "\"}");
            }
            else {
                cartService.updateItemByItemIdAndQuantity(username, itemId, quantity);
                CartItem item = cartService.getCartItemByUsernameAndItemId(username, itemId);
                String html = "<fmt:formatNumber type='number' pattern='$#,##0.00'>$" + item.getTotal() + "</fmt:formatNumber>";
                //System.out.println("html"+html);
                out.write("{\"isRemoved\":\"" + false + "\",\"itemId\":\"" + itemId + "\",\"quantity\":\"" + quantity +
                        "\",\"totalcost\":\"" + item.getTotal() + "\",\"html\":\"" + html + "\"}");
            }
        }

        List<CartItem> cart = cartService.selectItemByUsername(username);
        session.setAttribute("cart", cart);
        out.flush();
        out.close();

    }


}
