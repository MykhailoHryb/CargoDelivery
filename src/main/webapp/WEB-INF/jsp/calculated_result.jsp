<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.final_project.cargo_delivery.entity.City" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<c:choose>
    <c:when test="${cookie.lang.value=='ru_RU'}">
        <fmt:setBundle basename="messages_ru_RU"/>
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="messages_en_EN"/>
    </c:otherwise>
</c:choose>
<div class="result_calculating_block_price">
    <div class="result_calculating_title">
        <fmt:message key="main.calculation.price"/>:
    </div>
    <div class="result_calculating_price">${ model.calculated_price }
    </div>
    <div class="result_calculating_currency"><fmt:message key="main.calculation.currency"/></div>
</div>

<div class="all_width_block">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <div class="order-block">
                <div class="address_block">
                    <div class="item_calc">
                        <div class="desc"><fmt:message key="main.calculation.type_cargo"/>:</div>
                        <select name="type_cargo">
                            <option></option>
                            <c:forEach var="typCargo" items="${model.typeCargos}">
                                <option value="${typCargo.id}">${typCargo.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="entire_address_title"><fmt:message key="main.delivery.address_title"/>:</div>
                    <div class="order_city_to_item"><fmt:message key="main.delivery.city_short"/>
                        <span></span>,
                    </div>
                    <div class="order_address_to_item">
                        <fmt:message key="main.delivery.address_placeholder"/> <input type="text"
                                                                                      class="address_to_delivery"
                                                                                      name="address_to_delivery">
                    </div>
                    <div class="date_deliver_block">
                        <fmt:message key="main.delivery.date_receiving"/>: ${ model.date_deliver }
                    </div>
                </div>
                <button type="submit" class="main_calc_btn make_order"><fmt:message
                        key="main.calculation.order"/></button>
                <div class="info_order_add">
                    *<fmt:message key="main.delivery.order_info"/>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <a class="login_make_order" href="/login-page"><fmt:message
                    key="main.calculation.login_to_order"/></a>
        </c:otherwise>
    </c:choose>
</div>


