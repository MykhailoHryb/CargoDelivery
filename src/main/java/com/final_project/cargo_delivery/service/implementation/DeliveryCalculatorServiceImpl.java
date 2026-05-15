package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.DeliveryCalculatorService;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * DeliveryCalculatorService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class DeliveryCalculatorServiceImpl implements DeliveryCalculatorService {

    private final double WEIGHT_COEFFICIENT = 0.1;

    private final double VOLUME_COEFFICIENT = 0.1;

    private final double DISTANCE_COEFFICIENT_PER_KILOMETER = 0.5;

    private final int AMOUNT_KM_PER_DAY_COEFFICIENT = 500;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryCalculatorServiceImpl.class);

    @Override
    public int calculatePriceDelivery(CityViewDto cityFrom, CityViewDto cityTo, int weight, int volume, LocaleApplication localeApplication) {
        int resultPrice = (int) ((WEIGHT_COEFFICIENT * weight + VOLUME_COEFFICIENT * volume)
                * DISTANCE_COEFFICIENT_PER_KILOMETER
                * getDistance(cityFrom.getLatitude(), cityFrom.getLongitude(), cityTo.getLatitude(), cityTo.getLongitude()));

        return Math.max(resultPrice, 50);
    }

    /**
     * Get the distance in kilometers between two coordinates.
     *
     * @param latitude1  Latitude of the first coordinate, in degrees
     * @param longitude1 Longitude of the first coordinate, in degrees
     * @param latitude2  Latitude of the second coordinate, in degrees
     * @param longitude2 Longitude of the second coordinate, in degrees
     * @return Distance in kilometers
     */
    public double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        // Approximate radius of the earth in kilometers
        double earthRadius = 6371;
        double distance = Math.acos(Math.sin(latitude2 * Math.PI / 180.0) * Math.sin(latitude1 * Math.PI / 180.0) +
                Math.cos(latitude2 * Math.PI / 180.0) * Math.cos(latitude1 * Math.PI / 180.0) *
                        Math.cos((longitude1 - longitude2) * Math.PI / 180.0)) * earthRadius;
        return distance;
    }

    @Override
    public LocalDate getTimeToDelivery(CityViewDto cityFrom, CityViewDto cityTo, LocaleApplication localeApplication) {
        double distance = getDistance(cityFrom.getLatitude(), cityFrom.getLongitude(), cityTo.getLatitude(), cityTo.getLongitude());

        LOGGER.info("distance = {}", distance);
        int countDays = (int) Math.ceil(distance / AMOUNT_KM_PER_DAY_COEFFICIENT);
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(countDays);

        return localDate;

    }
}
