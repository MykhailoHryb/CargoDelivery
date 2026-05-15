<%@ page import="com.final_project.cargo_delivery.entity.OrderStatusEnum" %>
<%@ include file="../header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="status_canceled" value="<%=OrderStatusEnum.CANCELED.getID()%>"/>
<c:set var="status_payed" value="<%=OrderStatusEnum.PAYED.getID()%>"/>
<c:set var="status_delivered" value="<%=OrderStatusEnum.DELIVERED.getID()%>"/>
<h1 class="title-line"><fmt:message key="admin_page.title"/></h1>
<div class="result_block"></div>

<c:choose>
    <c:when test="${fn:length(orderList) == 0}"><fmt:message key="order.no_orders"/></c:when>

    <c:otherwise>
        <div class="alert_result"><fmt:message key="msg.success"/></div>
        <div class="report_block">
            <a href="/make-report&typeReport=report_by_city">
                <button data-type-report="report_by_city" class="btn btn-green make_report">
                    <fmt:message key="pdf.make_report.by_city"/>
                </button>
            </a>
            <a href="/make-report&typeReport=report_by_dates">
                <button data-type-report="report_by_dates" class="btn btn-green make_report">
                    <fmt:message key="pdf.make_report.by_date"/>
                </button>
            </a>
        </div>
        <form class="sorting">
            <div class="pagination_block">
                <div class="text_pagination">
                    <fmt:message key="pagination_text"/> :
                </div>
                <div class="input_pagination_block">
                    <input type="number" name="pagination_step" value="${stepPagen}">
                    <input type="hidden" name="pagination_item" value="${paginationItem}">
                </div>
            </div>
            <input type="hidden" name="orderBy" value="${sortBy}"/>
            <input type="hidden" name="typeSort" value="${typeSorting}"/>
        </form>
        <div class="basket-table">
            <div class="basket-row head">
                <div data-sort="id" class="basket-cell">id</div>
                <div data-sort="city_form_id" class="basket-cell"><fmt:message
                        key="main.calculation.cities_form"/></div>
                <div data-sort="city_to_id" class="basket-cell"><fmt:message key="main.calculation.cities_to"/></div>
                <div data-sort="weight" class="basket-cell"><fmt:message key="main.calculation.weight"/></div>
                <div data-sort="volume" class="basket-cell"><fmt:message key="main.calculation.volume"/></div>
                <div data-sort="price" class="basket-cell"><fmt:message key="order.price"/></div>
                <div data-sort="type_cargo_id" class="basket-cell"><fmt:message
                        key="main.calculation.type_cargo"/></div>
                <div data-sort="date_created" class="basket-cell"><fmt:message
                        key="main.calculation.date_created"/></div>
                <div data-sort="date_delivered" class="basket-cell"><fmt:message
                        key="main.delivery.date_receiving"/></div>
                <div class="basket-cell"><fmt:message key="order.address"/></div>
                <div data-sort="order_status_id" class="basket-cell"><fmt:message key="order.order_status"/></div>
                <div data-sort="user_id" class="basket-cell"><fmt:message key="order.user"/></div>
                <div class="basket-cell"><fmt:message key="order.receipt_path"/></div>

                <div class="basket-cell"></div>
            </div>
            <c:forEach var="bean" items="${orderList}">
                <div class="basket-row" data-order-id="${bean.id}">
                    <div class="basket-cell">
                        <div class="name">${bean.id}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.cityFromViewDto.name}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.cityToViewDto.name}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.weight}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.volume}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="price">${bean.price} <fmt:message key="main.calculation.currency"/></div>
                    </div>
                    <div class="basket-cell">
                        <div class="image-cell">
                            <div class="name">${bean.typeCargoViewDto.name}</div>
                            <img class="img_profile_order" src="${bean.typeCargoViewDto.imagePath}" alt="">
                        </div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.dateCreated}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.dateDelivery}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.address}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="image-cell">
                            <div class="name">${bean.orderStatusViewDto.name}</div>
                            <img class="img_profile_order order_status_img" src="${bean.orderStatusViewDto.imagePath}"
                                 alt="">
                        </div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">${bean.user.firstName} ${bean.user.lastName}</div>
                    </div>
                    <div class="basket-cell">
                        <div class="name">
                            <c:choose>
                                <c:when test="${fn:length(bean.receiptPath) != 0}">
                                    <a href="${bean.receiptPath}" target="_blank"><fmt:message
                                            key="order.receipt_path.link"/></a>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                    <div class="basket-cell">
                        <c:if test="${bean.orderStatusViewDto.id !=status_canceled
                        && bean.orderStatusViewDto.id !=status_payed
                        && bean.orderStatusViewDto.id != status_delivered}">
                            <button class="del"></button>
                        </c:if>
                    </div>
                    <div class="basket-cell">
                        <c:if test="${bean.orderStatusViewDto.id !=status_canceled
                        && bean.orderStatusViewDto.id !=status_payed
                        && bean.orderStatusViewDto.id != status_delivered}">
                            <button class="btn btn-create-receipt-payment btn-green">

                                <c:choose>
                                    <c:when test="${fn:length(bean.receiptPath) == 0}">
                                        <fmt:message key="order.make.receipt"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="order.update.receipt"/>
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </c:if>
                    </div>
                </div>

            </c:forEach>
        </div>
        <div class="dev-pagination">
            <div class="dev-pagination-container">
                <ul>
                    <c:forEach var="i" begin="1" end="${countPaginationElements}">

                        <li
                                <c:if test="${i == paginationItem}">
                                    class="dev-active"
                                </c:if>
                        ><a data-step="${i}"><span>${i}</span></a></li>
                    </c:forEach>

                </ul>
                <div style="clear:both"></div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<jsp:include page="../footer.jsp" flush="true"/>