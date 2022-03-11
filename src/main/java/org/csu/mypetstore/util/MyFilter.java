////过滤器测试
//
//package org.csu.mypetstore.util;
//
//import org.csu.mypetstore.domain.User;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @Author RessMatthew
// * @Description //测试了过滤器
// * @Date 6:28 下午 2022/3/11
// * @Param
// * @return
// **/
//@WebFilter(urlPatterns = "/account/*", filterName = "accountFilter")
//public class MyFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("创建过滤器");
//    }
//
//    @Override
//    public void destroy() {
//        System.out.println("摧毁过滤器");
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
//        User user = (User) httpServletRequest.getSession().getAttribute("user");
//        if(user!=null) {
//            if (httpServletRequest.getRequestURI().equals("/account/signout")) {
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//                Date date = new Date(System.currentTimeMillis());
//                System.out.println(formatter.format(date)+user.getUsername() + "退出");
//            }
//        }
//
//        //使得请求响应继续
//        filterChain.doFilter(request,response);
//    }
//}
