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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
            session.setAttribute("cart", cart);
            model.addAttribute("cart", cart);
            return "/cart/Cart";
        }
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(String workingItemId,HttpServletRequest request,Model model){
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
            CartItem cartItem = cartService.getCartItemByUsernameAndItemId(username, workingItemId);
            if (cartItem == null) {
                String msg="Attempted to remove all null CartItem from Cart.";
                model.addAttribute("msg",msg);
                return "common/Error";
            } else {
                cartService.removeCartItemByUsernameAndItemId(username, workingItemId);
                List<CartItem> cart = cartService.selectItemByUsername(username);
                session.setAttribute("cart", cart);
                model.addAttribute("cart",cart);
                return "/cart/Cart";
            }
        }
    }
}
