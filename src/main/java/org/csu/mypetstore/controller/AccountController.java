package org.csu.mypetstore.controller;

import com.zhenzi.sms.ZhenziSmsClient;
import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.service.UserService;
import org.csu.mypetstore.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
        System.out.println("*******************************************");
        System.out.println("当前验证码："+authCode);
        System.out.println("*******************************************");


        request.getSession().setAttribute("authCode",authCode);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-control","no-cache");
        response.setIntHeader("Expires",-1);
        g.dispose();
        ImageIO.write(image,"JPEG",response.getOutputStream());
        return null;
    }

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

    @PostMapping("/signinPhone")
    public String signinPhone(HttpServletRequest request,String phoneNumber,String username,String inputVCode,Model model){
        String msg = null;
        String vCode = (String)request.getSession().getAttribute("vCode");

        User user=userService.findUserByUsername(username);
        if(user==null){
            msg = "查无此人";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }else if(!phoneNumber.equals(user.getPhone())){
            System.out.println(phoneNumber);
            System.out.println(user.getPhone());
            msg = "用户名与手机号不匹配！";
            model.addAttribute("msg",msg);
            return "/account/Signin";
        }else if (!inputVCode.equals(vCode)){
            msg = "手机验证码有误，请重新输入！";
            model.addAttribute("msg", msg);
            return "/account/Signin";
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            model.addAttribute("user",user);
            return "/catalog/Main";
        }


    }

    @GetMapping("/fastSignin")
    public String fastSignin(HttpServletRequest request,HttpSession session,Model model){
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");

        User loginResult = userService.signin(user);
        System.out.println("当前使用123登陆");
        //HttpSession session = request.getSession();
        session.setAttribute("user",loginResult);
        model.addAttribute("user",loginResult);
        return "/catalog/Main";
    }

    @GetMapping("/refresh")
    public String refresh(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)request.getSession().getAttribute("user");
        session.setAttribute("user",user);
        return "redirect:/catalog/main";
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
    public String register(String password,String repeatpwd,HttpServletRequest request,User user,String inputCode,String inputVCode,Model model){
        String reminder= null;
        boolean result = false;
        System.out.println(inputVCode);

        if(!password.equals(repeatpwd)){
            reminder = "两次输入密码不一致";
            model.addAttribute("reminder",reminder);
            return "/account/MyAccount";
        }

        String authCode = (String)request.getSession().getAttribute("authCode");
        String vCode = (String)request.getSession().getAttribute("vCode");
        System.out.println(vCode);
        result = userService.register(user);
        if(!authCode.equals((inputCode))){
            reminder = "验证码有误，请重新输入！";
            model.addAttribute("reminder", reminder);
            return "/account/Register";
        }
        else if(!inputVCode.equals(vCode)){
            reminder = "手机验证码有误，请重新输入！";
            model.addAttribute("reminder", reminder);
            return "/account/Register";
        }
        else if (result == true) {
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
        boolean result = userService.updateUserByUsername(user);
        if(result == true){
            request.getSession().setAttribute("user",user);
            return "/catalog/Main";
        }
        else{
            message = "申请失败，请重新输入！";
            request.getSession().setAttribute("message",message);
            return "/account/MyAccount";
        }
    }

    @GetMapping("/signinsignout")
    public void signInSignOut(HttpServletRequest request,HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String msg = null;

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if(user != null){
            //System.out.println(user.getUsername());
            out.print("Exist");
        }
        else {
            out.print("Not Exist");
//            System.out.println("NE");
        }

        out.flush();
        out.close();

    }

    @GetMapping("/usernameIsExist")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        User user = userService.findUserByUsername(username);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if(username==""){
            out.print("Empty");
//            System.out.println("empty");
        }
        else if(user != null){
            out.print("Exist");
//            System.out.println('E');
        }
        else {
            out.print("Not Exist");
//            System.out.println("NE");
        }

        out.flush();
        out.close();


    }

    @PostMapping("/sendVCode")
    public void sendVCode(HttpServletRequest request){

        String reminder = null;
        String vCode = null;
        String email = null;
        try{
            //获取email的值
            email = request.getParameter("email");
            JavaMailUtil.receiveMailAccount = email;

            Properties pops = new Properties();
            pops.setProperty("mail.debug","true");
            pops.setProperty("mail.smtp.auth","true");
            pops.setProperty("mail.host",JavaMailUtil.emailSMTPHost);
            pops.setProperty("mail.transport.protocol","smtp");
            Session session = Session.getInstance(pops);
            session.setDebug(true);
            vCode = RandomNumberUtil.getRandomNumber();
            System.out.println("邮箱验证码" + vCode);
            String html = htmlText.html(vCode);
            MimeMessage message = JavaMailUtil.creatMimeMessage(session, JavaMailUtil.emailAccount,
                    JavaMailUtil.receiveMailAccount,html);
            Transport transport = session.getTransport();
            transport.connect(JavaMailUtil.emailAccount,JavaMailUtil.emailPassword);
            transport.sendMessage(message,message.getAllRecipients());
            transport.close();

            reminder = "验证码发送成功";
            request.setAttribute("reminder",reminder);
            request.getSession().setAttribute("vCode",vCode);
        }
        catch (MessagingException | IOException m){
            m.printStackTrace();
            request.getSession().setAttribute("error","邮件发送失败");
        }

    }

    @PostMapping("/phoneVCode")
    @ResponseBody
    public void phoneCode(HttpServletRequest request,String phoneNumber){

        System.out.println("1");
        System.out.println(phoneNumber);

        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId  = "111103";
        String appSecret = "761719c1-e3cc-41dc-9074-01744465caad";
        String reminder = null;
        String vCode = null;

        try{
            vCode = RandomNumberUtil.getRandomNumber();

            ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phoneNumber);
            params.put("templateId", "8485");
            String[] templateParams = new String[1];
            templateParams[0] = vCode;
            System.out.println(vCode);
            params.put("templateParams", templateParams);
            String result = client.send(params);

            reminder = "验证码发送成功";
            request.setAttribute("reminder",reminder);
            request.getSession().setAttribute("vCode",vCode);

            System.out.println(result);
            }catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error","验证码发送失败");
        }
    }

    @PostMapping("/passwordMSG")
    @ResponseBody
    public String passwordMSG(HttpServletRequest request,String phoneNumber,String username,Model model){



        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId  = "111103";
        String appSecret = "761719c1-e3cc-41dc-9074-01744465caad";
        String newPassword = null;


        User user = userService.findUserByUsername(username);

        if(user==null){
            return "用户名不存在！";
        }else if(!user.getPhone().equals(phoneNumber)){
            return "用户名与手机号不匹配！";
        }else{
            try{
                newPassword = RandomNumberUtil.getRandomNumber();

                ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("number", phoneNumber);
                params.put("templateId", "8515");
                String[] templateParams = new String[1];
                templateParams[0] = newPassword;


                params.put("templateParams", templateParams);
                String result = client.send(params);

                System.out.println(result);

                user.setPassword(newPassword);
                userService.updateUserByUsername(user);

                return "新密码已经发送至手机，请注意查收";
            }catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error","验证码发送失败");
                return "出问题了-_-";
            }
        }
    }

    @PostMapping("/passwordMSGMAIL")
    @ResponseBody
    public String passwordMSGMAIL(HttpServletRequest request,String email,String username,Model model){

        String apiUrl = "https://sms_developer.zhenzikj.com";
        String appId  = "111103";
        String appSecret = "761719c1-e3cc-41dc-9074-01744465caad";
        String newPassword = null;

        User user = userService.findUserByUsername(username);

        if(user==null){
            return "用户名不存在！";
        }else if(!user.getEmail().equals(email)){
            return "用户名与邮箱不匹配！";
        }else{

            try{
                newPassword = RandomNumberUtil.getRandomNumber();
                newPassword += RandomNumberUtil.getRandomNumber();

                JavaMailUtil.receiveMailAccount = email;

                Properties pops = new Properties();
                pops.setProperty("mail.debug","true");
                pops.setProperty("mail.smtp.auth","true");
                pops.setProperty("mail.host",JavaMailUtil.emailSMTPHost);
                pops.setProperty("mail.transport.protocol","smtp");
                Session session = Session.getInstance(pops);
                session.setDebug(true);
                String html = htmlTextResetPSW.htmlTextResetPSW(newPassword);
                MimeMessage message = JavaMailUtil.creatMimeMessage(session, JavaMailUtil.emailAccount,
                        JavaMailUtil.receiveMailAccount,html);
                Transport transport = session.getTransport();
                transport.connect(JavaMailUtil.emailAccount,JavaMailUtil.emailPassword);
                transport.sendMessage(message,message.getAllRecipients());
                transport.close();

                user.setPassword(newPassword);
                userService.updateUserByUsername(user);

                return "新密码已经发送至邮箱，请注意查收";
            }catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error","验证码发送失败");
                return "出问题了-_-";
            }
        }
    }




}
