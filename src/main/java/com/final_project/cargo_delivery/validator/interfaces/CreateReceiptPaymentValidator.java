package com.final_project.cargo_delivery.validator.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;

/**
 * CreateReceiptPaymentValidator
 *
 * @author Mykhailo Hryb
 */
public interface CreateReceiptPaymentValidator {

    /**
     * Validates order status before creating receipt
     *
     * @param localeApplication
     * @param currentOrderStatusId
     */
    void validateOrderStatusBeforeCreatingReceipt(LocaleApplication localeApplication, int currentOrderStatusId);
}
