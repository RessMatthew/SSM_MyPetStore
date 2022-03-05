<%--
  Created by IntelliJ IDEA.
  User: 57741
  Date: 2021/11/5
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>

<%@ include file="../common/IncludeTop.jsp"%>

<div id="Catalog">

    <form action="confirmOrder" method="post">

        <table>
            <tr>
                <th colspan=2>Payment Details</th>
            </tr>
            <tr>
                <td>Card Type:</td>
                <td>
                    <select name="cardType">
                        <c:forEach items="${sessionScope.creditCardTypes}" var="cardType">
                            <option value="${cardType}">
                                    ${cardType}
                            </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Card Number:</td>
                <td>
                    <input type="text" name="creditCard" value="${sessionScope.order.creditCard}">
                    * Use a fake number!
                </td>
            </tr>
            <tr>
                <td>Expiry Date (MM/YYYY):</td>
                <td>
                    <input type="text" name="expiryDate" value="${sessionScope.order.expiryDate}">
                </td>
            </tr>



            <tr>
                <th colspan=2>Billing Address</th>
            </tr>

            <tr>
                <td>First name:</td>
                <td>
                    <input type="text" name="firstName" id="billToFirstName" value="${sessionScope.order.billToFirstName}" readonly>
<%--                    <input type="text" name="shipToFirstName" id="shipToFirstName" value="${sessionScope.order.shipToFirstName}"/>--%>
                </td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td>
                    <input type="text" name="lastName" id="billToLastName" value="${sessionScope.order.billToLastName}" readonly>
<%--                    <input type="text" name="shipToLastName" id="shipToLastName" value="${sessionScope.order.shipToLastName}"/>--%>
                </td>
            </tr>
            <tr>
                <td>Address 1:</td>
                <td>
                    <input type="text" name="address1" id="billAddress1" value="${sessionScope.order.billAddress1}" readonly>
<%--                    <input type="text" size="40" name="shipAddress1" id="shipAddress1" value="${sessionScope.order.shipAddress1}"/>--%>
                </td>
            </tr>
            <tr>
                <td>Address 2:</td>
                <td>
                    <input type="text" name="address2" id="billAddress2" value="${sessionScope.order.billAddress2}" readonly>
<%--                    <input type="text" size="40" name="shipAddress2" id="shipAddress2" value="${sessionScope.order.shipAddress2}"/>--%>
                </td>
            </tr>
            <tr>
                <td>City:</td>
                <td>
                    <input type="text" name="city" id="billCity" value="${sessionScope.order.billCity}" readonly>
<%--                    <input type="text" name="shipCity" id="shipCity" value="${sessionScope.order.shipCity}"/>--%>
                </td>
            </tr>
            <tr>
                <td>State:</td>
                <td>
                    <input type="text" name="state"  id="billState" value="${sessionScope.order.billState}" readonly>
<%--                    <input type="text" size="4" name="shipState" id="shipState" value="${sessionScope.order.shipState}"/>--%>
                </td>
            </tr>
            <tr>
                <td>Zip:</td>
                <td>
                    <input type="text" name="zip" id="billZip" value="${sessionScope.order.billZip}" readonly>
<%--                    <input type="text" size="10" name="shipZip" id="shipZip" value="${sessionScope.order.shipZip}"/>--%>
                </td>
            </tr>
            <tr>
                <td>Country:</td>
                <td>
                    <input type="text" name="country" id="billCountry" value="${sessionScope.order.billCountry}" readonly>
<%--                    <input type="text" size="15" name="shipCountry" id="shipCountry" value="${sessionScope.order.shipCountry}"/>--%>
                </td>
            </tr>

            <tr>
                <td colspan=2>
<%--                    <input type="checkbox" name="shippingAddressRequired" value="shippingAddressRequired" onblur="shippingShow();">--%>
<%--                    <input type="checkbox" id="checkbox" name="shippingAddressRequired" value="shippingAddressRequired"  onclick="shippingShow();">--%>
<%--                        <input type="checkBox" name="shippingAddressRequired" value="shippingAddressRequired" onclick="if(this.checked){shippingShow()}">--%>
                         <input type="checkBox" name="shippingAddressRequired" value="shippingAddressRequired" onclick="shippingShow(shippingAddressRequired)">
                        Ship to different address...
                </td>
            </tr>

            <table id="Shipping">

            </table>

<%--            --%>
<%--            <tr>--%>
<%--                <th colspan=2>Shipping Address</th>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>First name:</td>--%>
<%--                <td><input type="text" name="shipToFirstName" value="${sessionScope.order.shipToFirstName}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Last name:</td>--%>
<%--                <td><input type="text" name="shipToLastName" value="${sessionScope.order.shipToLastName}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Address 1:</td>--%>
<%--                <td><input type="text" size="40" name="shipAddress1" value="${sessionScope.order.shipAddress1}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Address 2:</td>--%>
<%--                <td><input type="text" size="40" name="shipAddress2" value="${sessionScope.order.shipAddress2}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>City:</td>--%>
<%--                <td><input type="text" name="shipCity" value="${sessionScope.order.shipCity}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>State:</td>--%>
<%--                <td><input type="text" size="4" name="shipState" value="${sessionScope.order.shipState}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Zip:</td>--%>
<%--                <td><input type="text" size="10" name="shipZip" value="${sessionScope.order.shipZip}"/></td>--%>
<%--            </tr>--%>
<%--            <tr>--%>
<%--                <td>Country:</td>--%>
<%--                <td><input type="text" size="15" name="shipCountry" value="${sessionScope.order.shipCountry}"/></td>--%>
<%--            </tr>--%>




        </table>

        <input type="submit" value="Continue">

    </form>
</div>


<script type = "text/javascript" src = "js/skippingAddress.js"></script>

<%@ include file="../common/IncludeBottom.jsp"%>