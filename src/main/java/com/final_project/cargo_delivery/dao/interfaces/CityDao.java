package com.final_project.cargo_delivery.dao.interfaces;

import com.final_project.cargo_delivery.entity.City;
import com.final_project.cargo_delivery.entity.LocaleApplication;

import java.util.List;

/**
 * Data access object for City entity
 *
 * @author Mykhailo Hryb
 */
public interface CityDao {

    /**
     * Gets city by id From database
     *
     * @param cityId
     * @param localeApplication
     * @return
     */
    City getCityById(int cityId, LocaleApplication localeApplication);

    /**
     * Gets all cities from database
     *
     * @param localeApplication
     * @param orderBy
     * @return
     */
    List<City> getAllCities(LocaleApplication localeApplication, String orderBy);

    /**
     * Gets all cities with filter if cities are from Ukraine or are foreign
     *
     * @param localeApplication
     * @param orderBy
     * @param isForeign
     * @return
     */
    List<City> getAllCitiesWithFilterIsForeign(LocaleApplication localeApplication, String orderBy, int isForeign);

}
