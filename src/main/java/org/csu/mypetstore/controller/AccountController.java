package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.service.UserService;
import org.csu.mypetstore.util.AuthCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;


    @GetMapping("/viewSignin")
    public String viewSignin(){
        return "account/Signin";
    }

    @GetMapping("/authCode")
    public String authCode(HttpServletRequest request, HttpServletResponse response,Integer number) throws IOException {
        AuthCodeUtil authCodeUtil=new AuthCodeUtil();
        BufferedImage image = new BufferedImage(authCodeUtil.WIDTH,authCodeUtil.HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        String authCode = "";
        authCodeUtil.setBackground(g);
        authCodeUtil.setBorder(g);
        authCodeUtil.setRandomLine(g);
        authCode = authCodeUtil.setWriteDate(g);
        System.out.println("当前验证码："+authCode);

        request.getSession().setAttribute("authCode",authCode);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-control","no-cache");
        response.setIntHeader("Expires",-1);
        g.dispose();
        ImageIO.write(image,"JPEG",response.getOutputStream());
        return null;
    }

    //刷新有问题
    @PostMapping("/signin")
    public String singin(HttpServletRequest request,String inputCode,User user,Model model){
        String msg = null;
        String authCode = (String)request.getSession().getAttribute("authCode");

        User loginResult = userService.signin(user);
        if(!authCode.equals(inputCode)){
            msg = "验证码有误！";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }else if(loginResult == null){
            msg = "用户名或密码不正确";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }
        else{
            HttpSession session = request.getSession();
            session.setAttribute("user",loginResult);
            model.addAttribute("user",loginResult);
            return "/catalog/Main";
        }
    }

    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
        if(request.getSession().getAttribute("user") != null) {
            request.getSession().removeAttribute("user");
        }
        return "/catalog/Main";
    }

    @GetMapping("/viewMyAccount")
    public String viewMyAccount(HttpServletRequest request,Model model){
        if(request.getSession().getAttribute("user") == null) {
            String msg = "请先登录后再查看MyAccount";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }
        User user=(User)request.getSession().getAttribute("user");

        model.addAttribute("user",user);
        return "/account/MyAccount";
    }

    @GetMapping("/viewRegister")
    public String viewRegister(){
        return "/account/Register";
    }

    @PostMapping("/register")
    public String register(String password,String repeatpwd,HttpServletRequest request,User user,String inputCode,Model model){
        String reminder= null;
        int result = 0;

        if(!password.equals(repeatpwd)){
            reminder = "两次输入密码不一致";
            model.addAttribute("reminder",reminder);
            return "/account/MyAccount";
        }

        String authCode = (String)request.getSession().getAttribute("authCode");
        result = userService.register(user);
        if(!authCode.equals((inputCode))){
            reminder = "验证码有误，请重新输入！";
            model.addAttribute("reminder", reminder);
            return "/account/Register";
        }
        else if (result == 1) {
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "/catalog/Main";

        } else {
            reminder = "该用户名已注册，请重新输入！";
            model.addAttribute("reminder", reminder);
            return "/account/Register";
        }
    }

    @PostMapping("/saveAccount")
    public String saveAccount(String password,String repeatpwd,Model model,HttpServletRequest request,User user){
        String message = null;
        int result = userService.updateUserByUsername(user);
        if(result == 1){
            request.getSession().setAttribute("user",user);
            return "/catalog/Main";
        }
        else{
            message = "申请失败，请重新输入！";
            request.getSession().setAttribute("message",message);
            return "/account/MyAccount";
        }
    }
}
