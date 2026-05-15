package com.final_project.cargo_delivery.validator.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.CityViewDto;

/**
 * CostCalculatorValidator
 *
 * @author Mykhailo Hryb
 */
public interface CostCalculatorValidator {

    /**
     * Validates id of cities
     *
     * @param localeApplication
     * @param cityFromId
     * @param cityToId
     */
    void validateCitiesParameters(LocaleApplication localeApplication, String cityFromId, String cityToId);

    /**
     * Validates weight and volume
     *
     * @param localeApplication
     * @param weight
     * @param volume
     */
    void validateCostParameters(LocaleApplication localeApplication, String weight, String volume);


    /**
     * Validates if cities the same
     *
     * @param localeApplication
     * @param cityFrom
     * @param cityTo
     */
    void validateSameCities(LocaleApplication localeApplication, CityViewDto cityFrom, CityViewDto cityTo);
}
