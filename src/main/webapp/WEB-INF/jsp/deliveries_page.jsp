<%@ include file="header.jsp" %>
<h1 class="main_title"><fmt:message key="main.title_delivery"/></h1>
<div class="info_about_prices">*<fmt:message key="main.delivery.info_prices"/></div>
<div class="main_container">

    <div class="col-md-12">
        <div class="row">
            <div class="col-md-3">
                <div class="left-menu sort-menu">
                    <div class="sort-title"><fmt:message key="main.delivery.sort_by"/>:</div>
                    <a class="left-menu__link" href="/deliveries-page&orderBy=name"><fmt:message
                            key="main.delivery.name"/></a>
                    <a class="left-menu__link" href="/deliveries-page&orderBy=is_foreign"><fmt:message
                            key="main.delivery.where"/></a>
                </div>
                <div class="left-menu filter-menu">
                    <div class="sort-title"><fmt:message key="main.delivery.filter"/>:</div>
                    <form class="city-filter">
                        <span class="filter-name"><fmt:message key="main.delivery.where"/></span>
                        <select class="filter-select" name="is_foreign">
                            <option selected value=""><fmt:message key="main.delivery.all_cities"/></option>
                            <option value="0"><fmt:message key="main.delivery.ukraine"/></option>
                            <option value="1"><fmt:message key="main.delivery.other_countries"/></option>
                        </select>
                        <div class="filter_block_btn">
                            <button class="btn-red filter-btn" type="submit"><fmt:message
                                    key="main.delivery.to_filter"/></button>
                            <%--                            <button class="btn-red reset-btn" type="reset"><fmt:message--%>
                            <%--                                    key="main.delivery.reset"/></button>--%>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-md-9">
                <div class="catalog-list">
                    <c:forEach var="city" items="${cities}">
                        <div class="item" data-id="${city.id}">
                            <div class="item-inner">
                                <div class="img-block">
                                    <div class="hidden-block">
                                        <div class="name">${city.name}
                                        </div>
                                    </div>
                                    <div class="img-inner">
                                        <img src="${city.imgPath}" alt="">
                                    </div>
                                </div>
                                <div class="price-info">
                                    <div class="info-product">
                                        <div class="price-block">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <div class="description_main"><fmt:message key="main.calculate.title"/>:</div>
    <div class="calculating-block">
        <form id="calculation_form">
            <div class="result_block"></div>
            <div class="cal_choice_block">
                <div class="item_calc">
                    <div class="desc"><fmt:message key="main.calculation.cities_form"/>:</div>
                    <select name="city_from">
                        <option></option>
                        <c:forEach var="city" items="${cities}">
                            <option value="${city.id}">${city.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="item_calc">
                    <div class="desc"><fmt:message key="main.calculation.cities_to"/>:</div>
                    <select name="city_to">
                        <option></option>
                        <c:forEach var="city" items="${cities}">
                            <option value="${city.id}">${city.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="item_calc">
                    <div class="desc"><fmt:message key="main.calculation.weight"/>:</div>
                    <input type="number" class="weight" name="weight" value="">
                </div>
                <div class="item_calc">
                    <div class="desc"><fmt:message key="main.calculation.volume"/>:</div>
                    <input type="number" class="volume" name="volume" value="">
                </div>

            </div>
            <div class="block_submit">
                <button type="submit" class="main_calc_btn calculate"><fmt:message
                        key="main.calculation.calc_btn"/></button>
            </div>
        </form>
        <div class="result_calculating_block">
        </div>
    </div>
</div>
<div class="city_choice" data-chosen-city="">
    <div class="city_choice_inner">
        <div class="close_btn"></div>
        <div class="name"><fmt:message key="main.delivery.popup.title"/>:</div>
        <div class="buttons_city_block">
            <button type="submit" class="city_from"><fmt:message key="main.delivery.popup.city_from"/></button>
            <button type="submit" class="city_to"><fmt:message key="main.delivery.popup.city_to"/></button>
        </div>
    </div>
</div>
<div class="order_result">
    <fmt:message key="main.delivery.order_success"/>
</div>


<jsp:include page="footer.jsp" flush="true"/>
