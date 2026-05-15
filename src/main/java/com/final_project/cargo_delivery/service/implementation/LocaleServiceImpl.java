package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.dao.implementations.LocaleDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.LocaleDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Arrays;

/**
 * LocaleService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class LocaleServiceImpl implements LocaleService {

    private LocaleDao localeDao = new LocaleDaoImpl();

    @Override
    public LocaleApplication getLocaleByCookieOrDefault(Cookie[] arrCookies) {
        String shortNameLocale = "en_EN";
        if (arrCookies != null) {
            Cookie langCookie = Arrays.stream(arrCookies)
                    .filter(cookie -> cookie.getName()
                            .equals("lang")).findFirst().orElse(null);
            if (langCookie != null) {
                shortNameLocale = langCookie.getValue();
            }
        }
        return localeDao.getLocaleByShortName(shortNameLocale);
    }
}
