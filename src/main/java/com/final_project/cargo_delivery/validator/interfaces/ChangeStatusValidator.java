package com.final_project.cargo_delivery.validator.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;

/**
 * ChangeStatusValidator
 *
 * @author Mykhailo Hryb
 */
public interface ChangeStatusValidator {

    /**
     * Validates order id of order status to change
     *
     * @param localeApplication
     * @param orderIdStr
     */
    void validateOrderId(LocaleApplication localeApplication, String orderIdStr);

    /**
     * Validates current status, is it possible to pay
     *
     * @param localeApplication
     * @param currentStatus
     * @param pdfFilePath
     */
    void validateStatusToPay(LocaleApplication localeApplication, int currentStatus, String pdfFilePath);

    /**
     * Validates current status, is it possible to cancel
     *
     * @param localeApplication
     * @param currentStatus
     */
    void validateStatusToCancel(LocaleApplication localeApplication, int currentStatus);

    /**
     * Validates if type of changing status is acceptable
     *
     * @param localeApplication
     * @param type
     */
    void validateTypeOfChangingStatus(LocaleApplication localeApplication, String type);
}
