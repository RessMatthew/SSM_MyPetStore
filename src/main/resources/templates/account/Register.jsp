<%--
  Created by IntelliJ IDEA.
  User: 57741
  Date: 2021/11/5
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>

<%@ include file="../common/IncludeTop.jsp" %>
<%@ page session="false"%>

<style>
    .okTips{
        color : green;
    }
    .errorTips{
        color : red;
    }
</style>

<div id="BackLink">
    <a href="main">Return to Main Menu</a>
</div>
<form action="register" method="post">
    <h1 align="center">User Information</h1>
    <table align="center">

        <tr>
            <td>Username:</td>

            <td>
                    <%--<input type="text" id="username" name = "username" onblur="checkUsername();" required>--%>
                    <input type="text" id="username" name = "username"  required>
            </td>

            <td id = "iEI"> <span id="isExistInfo"></span></td>
        </tr>


        <tr>
            <td> New Password:  </td>
            <td><input type="password" name="password" required>
            </td>
        </tr>

        <tr>
            <td> Verification Code:</td>
            <td><input type="text" name="authCode" required></td>
        </tr>
        <tr>
            <td> <img id="myImage" name="myImage" src="authCode"></td>
            <td><a href="javascript:void(0)" onclick="myReload()">uncleary!change the image</a></td>
        </tr>


        <tr>
            <td > <font color="red">${requestScope.reminder}</font></td>
        </tr>
        <tr>
            <td><input type="submit" value="Save"></td>
        </tr>
    </table>
</form>

<script type = "text/javascript" src = "js/register.js"></script>

<%@ include file="../common/IncludeBottom.jsp"%>
