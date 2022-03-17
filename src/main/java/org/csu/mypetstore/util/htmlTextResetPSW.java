package org.csu.mypetstore.util;

public class htmlTextResetPSW {
    public static String htmlTextResetPSW(String code){
        String html = "密码重置验证<br/>" +
                "这封邮件是由JPetStore发送的。<br/>" +
                "您的密码已经重置！</br>" +
                "这是您的新密码：<h3 style = 'color:red;'>" + code + "</h3></br>"+
                "请尽快前往JpetStore的MyAccount处进行密码的修改<br>";
        return html;
    }
}
