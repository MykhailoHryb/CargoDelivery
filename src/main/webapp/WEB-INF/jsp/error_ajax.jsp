<%@ page isErrorPage="true" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<c:choose>
    <c:when test="${cookie.lang.value=='ru_RU'}">
        <fmt:setBundle basename="messages_ru_RU"/>
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="messages_en_EN"/>
    </c:otherwise>
</c:choose>
<%--<%@ include file="/WEB-INF/jsp/header.jsp" %>--%>
<table id="main-container">

    <tr >
        <td class="content">
            <%-- CONTENT --%>

            <h2 class="error">
                <fmt:message key="exception.error.title"/>:
            </h2>

            <%-- this way we get the error information (error 404)--%>
            <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"/>
            <c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>

            <%-- this way we get the exception --%>
            <c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>

<%--            <c:if test="${not empty code}">--%>
<%--                <h3>Error code: ${code}</h3>--%>
<%--            </c:if>--%>

            <c:if test="${not empty message}">
                <h3>${message}</h3>
            </c:if>

            <%-- if get this page using forward --%>
            <c:if test="${not empty errorMessage and empty exception and empty code}">
                <h3>${errorMessage}</h3>
            </c:if>

            <%-- this way we print exception stack trace --%>
<%--            <c:if test="${not empty exception}">--%>
<%--                <hr/>--%>
<%--                <h3>Stack trace:</h3>--%>
<%--                <c:forEach var="stackTraceElement" items="${exception.stackTrace}">--%>
<%--                    ${stackTraceElement}--%>
<%--                </c:forEach>--%>
<%--            </c:if>--%>

            <%-- CONTENT --%>
        </td>
    </tr>

</table>
<%--<jsp:include page="WEB-INF/jsp/footer.jsp" flush="true"/>--%>