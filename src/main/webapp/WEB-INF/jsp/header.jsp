<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.final_project.cargo_delivery.entity.Role" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/title.tld" prefix="custom" %>
<c:set var="lang" value="en_EN"/>

<c:choose>
    <c:when test="${cookie.lang.value=='ru_RU'}">
        <fmt:setBundle basename="messages_ru_RU"/>
        <c:set var="lang" value="ru_RU"/>
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="messages_en_EN"/>
    </c:otherwise>
</c:choose>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:f="http://xmlns.jcp.org/jsf/core">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title><custom:title locale="${lang}"/></title>
    <style>
        body {
            overflow-x: hidden;
        }

        html {
            background-color: #fff;
        }
    </style>
</head>

<body>
<header class="main-header">
    <div class="top-line">
        <div class="container">
            <div class="row">
                <div class="col-xs-8">
                    <div class="select-city">
                        <select name="" id="" class="new_select">
                            <c:choose>
                                <c:when test="${cookie.lang.value=='ru_RU'}">
                                    <option value="en_EN">En</option>
                                    <option selected value="ru_RU">Ru</option>
                                </c:when>
                                <c:otherwise>
                                    <option selected value="en_EN">En</option>
                                    <option value="ru_RU">Ru</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>
                    <ul class="top-menu">
                        <li><a href="/deliveries-page"><fmt:message key="nav.deliveries"/></a></li>
                    </ul>
                </div>
                <div class="col-xs-4">
                    <div class="top-info">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <c:set var="user" value="${sessionScope.user}"/>
                                <c:set var="managerId" value="<%=Role.MANAGER.getID()%>" />
                                <c:if test="${user.roleId == managerId}">
                                    <a href="/manager-page" class="personal"><fmt:message key="nav.personal"/></a>
                                </c:if>
                                <a href="/profile-page" class="personal"><fmt:message key="nav.profile"/></a>
                                <a href="/logout" class="personal"><fmt:message key="nav.logout"/></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/login-page" class="personal"><fmt:message key="nav.login"/></a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="bottom-line">
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <a href="/" class="logo">
                        <img src="../../img/logo.png" alt=""/>
                    </a>
                </div>
                <div class="col-md-7">
                    <div class="search-phone">
                        <div class="phone-block">
                            <div class="phone-inner">
                                <a href="" class="phone"><span>380 11</span> 111-11-11 </a>
                                <a href="" class="phone"><span>380 22</span> 222-22-22 </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>

