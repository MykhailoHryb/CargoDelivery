<%@ include file="header.jsp" %>
<div class="product-page__top personal-block">
    <div class="info-block">
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="product-block">
                    <div class="contact-block">
                        <div class="inner">
                            <form id="authorization_form">
                                <div class="result_block"></div>
                                <div class="name"><fmt:message key="user.auth.title"/></div>
                                <div class="desc"><fmt:message key="user.registration.email"/></div>
                                <input type="email" name="email" class="phone" value=""/>
                                <div class="desc"><fmt:message key="user.registration.password"/></div>
                                <input type="password" class="phone" name="password" value="">
                                <button type="submit" class="order"><fmt:message key="nav.login"/></button>
                                <div class="desc registration"><a href="/registration"><fmt:message key="user.registration"/></a></div>
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div><fmt:message key="user.auth"/></div>
                <br>
                <div><a href="/logout"><fmt:message key="user.logout"/></a></div>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<%@ include file="footer.jsp" %>

