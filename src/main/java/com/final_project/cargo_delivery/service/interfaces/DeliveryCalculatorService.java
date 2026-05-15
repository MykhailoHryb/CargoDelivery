package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.CityViewDto;

import java.time.LocalDate;

/**
 * DeliveryCalculatorService
 *
 * @author Mykhailo Hryb
 */
public interface DeliveryCalculatorService {

    /**
     * Calculates price for delivery
     *
     * @param cityFrom
     * @param cityTo
     * @param weight
     * @param volume
     * @param localeApplication
     * @return
     */
    int calculatePriceDelivery(CityViewDto cityFrom, CityViewDto cityTo, int weight, int volume, LocaleApplication localeApplication);

    /**
     * Gets time to delivery
     *
     * @param cityFrom
     * @param cityTo
     * @param localeApplication
     * @return
     */
    LocalDate getTimeToDelivery(CityViewDto cityFrom, CityViewDto cityTo, LocaleApplication localeApplication);
}
