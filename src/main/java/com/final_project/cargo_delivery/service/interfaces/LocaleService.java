package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;

import javax.servlet.http.Cookie;

/**
 * LocaleService
 *
 * @author Mykhailo Hryb
 */
public interface LocaleService {

    /**
     * Gets locale from cookie or set by default
     *
     * @param arrCookies
     * @return
     */
    LocaleApplication getLocaleByCookieOrDefault(Cookie[] arrCookies);
}
