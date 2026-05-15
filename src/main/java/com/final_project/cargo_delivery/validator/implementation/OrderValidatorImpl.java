package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.OrderValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * OrderValidator implementation
 *
 * @author Mykhailo Hryb
 */
@Component
public class OrderValidatorImpl implements OrderValidator {

    @Override
    public void validateOrderParameters(LocaleApplication localeApplication, String orderTypeCargo, String address) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (StringUtils.isEmpty(orderTypeCargo)) {
            throw new ValidationException(messages.getString("exceptions.calculating.typeCargo"));
        }
        if (StringUtils.isEmpty(address)) {
            throw new ValidationException(messages.getString("exceptions.calculating.address"));
        }
    }
}
