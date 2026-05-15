package com.final_project.cargo_delivery.validator.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;

/**
 * OrderValidator
 *
 * @author Mykhailo Hryb
 */
public interface OrderValidator {

    /**
     * Validates parameters for order that weren't validated when calculating price
     *
     * @param localeApplication
     * @param orderTypeCargo
     * @param address
     */
    void validateOrderParameters(LocaleApplication localeApplication, String orderTypeCargo, String address);
}
