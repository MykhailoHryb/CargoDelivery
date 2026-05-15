package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import com.final_project.cargo_delivery.web.util.Sorting;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * DeliveriesPageController sets cities to deliver from/to.
 *
 * @author Mykhailo Hryb
 */
@Controller
public class DeliveriesPageController {

    private LocaleService localeService;
    private CityService cityService;

    @Autowired
    DeliveriesPageController(LocaleService localeService, CityService cityService) {
        this.localeService = localeService;
        this.cityService = cityService;
    }

    @GetMapping("/deliveries-page")
    public ModelAndView getDeliveriesPage(HttpServletRequest request) {
        LocaleApplication localeApplication;
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);
        List<CityViewDto> cityViewDtoList;
        Sorting sorting = new Sorting(request, "id");

        String isForeignStr = request.getParameter("is_foreign");
        if (!StringUtils.isEmpty(isForeignStr)) {
            int isForeign = Integer.parseInt(isForeignStr);
            cityViewDtoList = cityService.getAllCitiesWithFilterIsForeign(localeApplication, sorting.getSorting(), isForeign);
        } else {
            cityViewDtoList = cityService.getAllCities(localeApplication, sorting.getSorting());
        }

        return new ModelAndView("deliveries_page", "cities", cityViewDtoList);
    }

}
