package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.CostCalculatorValidator;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * CostCalculatorValidator implementation
 *
 * @author Mykhailo Hryb
 */
@Component
public class CostCalculatorValidatorImpl implements CostCalculatorValidator {

    @Override
    public void validateCitiesParameters(LocaleApplication localeApplication, String cityFromId, String cityToId) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (StringUtils.isEmpty(cityFromId)) {
            throw new ValidationException(messages.getString("exceptions.calculating.city_from"));
        }
        if (!StringUtils.isNumeric(cityFromId)) {
            throw new ValidationException(messages.getString("exceptions.calculating.city_from_numeric"));
        }
        if (StringUtils.isEmpty(cityToId)) {
            throw new ValidationException(messages.getString("exceptions.calculating.city_to"));
        }
        if (!StringUtils.isNumeric(cityToId)) {
            throw new ValidationException(messages.getString("exceptions.calculating.city_to_numeric"));
        }
    }

    @Override
    public void validateCostParameters(LocaleApplication localeApplication, String weight, String volume) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (StringUtils.isEmpty(weight)) {
            throw new ValidationException(messages.getString("exceptions.calculating.weight"));
        }

        if (!StringUtils.isNumeric(weight)) {
            throw new ValidationException(messages.getString("exceptions.calculating.weight_numeric"));
        }
        if (StringUtils.isEmpty(volume)) {
            throw new ValidationException(messages.getString("exceptions.calculating.volume"));
        }
        if (!StringUtils.isNumeric(volume)) {
            throw new ValidationException(messages.getString("exceptions.calculating.volume_numeric"));
        }
        if (Integer.parseInt(weight) == 0) {
            throw new ValidationException(messages.getString("exceptions.calculating.weight_zero"));
        }
        if (Integer.parseInt(volume) == 0) {
            throw new ValidationException(messages.getString("exceptions.calculating.volume_zero"));
        }
    }

    @Override
    public void validateSameCities(LocaleApplication localeApplication, CityViewDto cityFrom, CityViewDto cityTo) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (cityFrom.equals(cityTo)) {
            throw new ValidationException(messages.getString("exceptions.calculating.same_city"));
        }
    }
}
