package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.CreateReceiptPaymentValidator;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * CreateReceiptPaymentValidator implementation
 *
 * @author Mykhailo Hryb
 */
@Component
public class CreateReceiptPaymentValidatorImpl implements CreateReceiptPaymentValidator {

    @Override
    public void validateOrderStatusBeforeCreatingReceipt(LocaleApplication localeApplication, int currentOrderStatusId) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (currentOrderStatusId == OrderStatusEnum.CANCELED.getID()) {
            throw new ValidationException(messages.getString("exception.error.creating_receipt_status"));
        }
    }
}
