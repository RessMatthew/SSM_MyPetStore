package org.csu.mypetstore.controller;


import org.csu.mypetstore.domain.CartItem;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.service.CartService;
import org.csu.mypetstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderCoontroller {



    @GetMapping("/viewOrderForm")
    public String viewOrderForm(Model model ,HttpServletRequest request){


        List<CartItem> cart;
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("user");
        model.addAttribute("user",account);
        // 获得购物车
        cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null){
            cart = new ArrayList<>();
            model.addAttribute("cart",cart);
        }

        if(account == null){
            // 跳转到登录界面
            return("account/Signin");
        }else {
            // 跳转到 订单 页面
            Order order = new Order();
            // 生成订单
            order.initOrder(account,cart);

            model.addAttribute("creditCardTypes",order.getCardType());
            model.addAttribute("order",order);

            session.setAttribute("creditCardTypes",order.getCardType());
            session.setAttribute("order",order);

            return("order/OrderForm");
        }

    }

    @PostMapping("/confirmOrder")
    public String confirmOrder(HttpServletRequest request,Model model){

        HttpSession session = request.getSession();


        String cardType = request.getParameter("cardType");
        String creditCard = request.getParameter("creditCard");
        String expiryDate = request.getParameter("expiryDate");


        User account = (User)session.getAttribute("user");
        Order order = (Order)session.getAttribute("order");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");




        order.setCardType(cardType);
        order.setCreditCard(creditCard);
        order.setExpiryDate(expiryDate);
        order.setShipToFirstName(account.getFirstname());
        order.setShipToLastName(account.getLastname());
        order.setBillAddress1(address1);
        order.setBillAddress2(address2);
        order.setShipAddress1(account.getAddress1());
        order.setShipAddress2(account.getAddress2());
        order.setBillCity(city);
        order.setShipCity(account.getCity());
        order.setBillState(state);
        order.setShipState(account.getState());
        order.setBillZip(zip);
        order.setShipZip(account.getZip());
        order.setBillCountry(country);
        order.setShipCountry(account.getCountry());




        session.setAttribute("order",order);
        model.addAttribute("order",order);

        session.setAttribute("user",account);
        model.addAttribute("user",account);



        if(request.getParameter("shippingAddressRequired") != null){
            return "/order/ShippingForm";
        }else{
            return "/order/ConfirmOrder";
        }

    }



    @PostMapping("/confirmShip")
    public String confirmShip(HttpServletRequest request,Model model){

        String shipToFirstName = request.getParameter("shipToFirstName");
        String shipToLastName = request.getParameter("shipToLastName");
        String shipAddress1 = request.getParameter("shipAddress1");
        String shipAddress2 = request.getParameter("shipAddress2");
        String shipCity = request.getParameter("shipCity");
        String shipState = request.getParameter("shipState");
        String shipZip = request.getParameter("shipZip");
        String shipCountry = request.getParameter("shipCountry");


        HttpSession session = request.getSession();
        Order order = (Order)session.getAttribute("order");

        order.setShipToFirstName(shipToFirstName);
        order.setShipToLastName(shipToLastName);
        order.setShipAddress1(shipAddress1);
        order.setShipAddress2(shipAddress2);
        order.setShipCity(shipCity);
        order.setShipState(shipState);
        order.setShipZip(shipZip);
        order.setShipCountry(shipCountry);
        //覆盖原来的order


        session.setAttribute("order",order);
        model.addAttribute("order",order);

        


        return "/order/ConfirmOrder";

    }



    @GetMapping("/viewOrder")
    public String viewOrder(HttpServletRequest request,Model model){

        HttpSession session = request.getSession();

        Order order = (Order) session.getAttribute("order");
        session.setAttribute("lineItems",order.getLineItems());

        OrderService orderService = new OrderService();
        orderService.insertOrder(order);

        // 重置购物车
        CartService cartService = new CartService();
        User user = (User) session.getAttribute("user");
        String username = user.getUsername();
        List<CartItem> cart = (List<CartItem>)session.getAttribute("cart");
        for (int i = 0; i < cart.size(); i++){
            cartService.updateItemByItemIdAndPay(username, cart.get(i).getItem().getItemId(),true);
        }
        session.removeAttribute("cart");

        model.addAttribute("user",user);
        model.addAttribute("order",order);
        model.addAttribute("lineItems",order.getLineItems());

        return "order/ViewOrder";

    }







}
