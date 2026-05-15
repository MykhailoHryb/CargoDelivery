package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.CityViewDto;

import java.util.List;

/**
 * CityService
 *
 * @author Mykhailo Hryb
 */
public interface CityService {

    /**
     * Gets all cities
     *
     * @param localeApplication
     * @param orderBy
     * @return
     */
    List<CityViewDto> getAllCities(LocaleApplication localeApplication, String orderBy);

    /**
     * Gets all cities with filter is city in Ukraine or is foreign
     *
     * @param localeApplication
     * @param orderBy
     * @return
     */
    List<CityViewDto> getAllCitiesWithFilterIsForeign(LocaleApplication localeApplication, String orderBy, int isForeign);

    /**
     * Get city by id
     *
     * @param localeApplication
     * @param cityId
     * @return
     */
    CityViewDto getCityById(LocaleApplication localeApplication, int cityId);
}
