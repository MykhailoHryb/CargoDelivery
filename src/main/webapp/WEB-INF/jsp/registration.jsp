<%@ include file="header.jsp" %>
<div class="product-page__top personal-block">
    <div class="info-block">
        <div class="product-block">
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <div class="contact-block">
                        <div class="inner">
                            <form id="registration">
                                <div class="result_block"></div>
                                <div class="name"><fmt:message key="user.registration"/></div>
                                <div class="desc"><fmt:message key="user.registration.first_name"/></div>
                                <input type="text" name="firstName" class="phone"/>
                                <div class="desc"><fmt:message key="user.registration.last_name"/></div>
                                <input type="text" name="lastName" class="phone"/>
                                <div class="desc"><fmt:message key="user.registration.email"/></div>
                                <input type="email" name="email" class="phone"/>
                                <div class="desc"><fmt:message key="user.registration.password"/></div>
                                <input type="password" name="password" class="phone"/>
                                <button type="submit" class="order"><fmt:message
                                        key="user.registration.register"/></button>
                                <div class="desc registration"><a href="/login-page"><fmt:message
                                        key="user.registration.already_register"/></a></div>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div><fmt:message key="user.registration.you_are_auth"/></div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
