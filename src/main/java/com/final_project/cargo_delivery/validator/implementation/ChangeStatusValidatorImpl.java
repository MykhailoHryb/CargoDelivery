package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.OrderStatusEnum;
import com.final_project.cargo_delivery.exception.ValidationException;
import com.final_project.cargo_delivery.validator.interfaces.ChangeStatusValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ChangeStatusValidator implementation
 *
 * @author Mykhailo Hryb
 */
@Component
public class ChangeStatusValidatorImpl implements ChangeStatusValidator {

    @Override
    public void validateOrderId(LocaleApplication localeApplication, String orderIdStr) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (StringUtils.isEmpty(orderIdStr) || !StringUtils.isNumeric(orderIdStr)) {
            throw new ValidationException(messages.getString("exception.error.cannot_find_order"));
        }
    }

    @Override
    public void validateStatusToPay(LocaleApplication localeApplication, int currentStatus, String pdfFilePath) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (currentStatus != OrderStatusEnum.NOT_PAYED.getID()) {
            throw new ValidationException(messages.getString("exception.error.status_cannot_pay"));
        }
        if (StringUtils.isEmpty(pdfFilePath)) {
            throw new ValidationException(messages.getString("exception.error.status_pay_without_receipt"));
        }
    }

    @Override
    public void validateStatusToCancel(LocaleApplication localeApplication, int currentStatus) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (currentStatus == OrderStatusEnum.CANCELED.getID()) {
            throw new ValidationException(messages.getString("exception.error.status_cancel_canceled"));
        }
        if (currentStatus == OrderStatusEnum.PAYED.getID()) {
            throw new ValidationException(messages.getString("exception.error.status_cancel_payed"));
        }
        if (currentStatus == OrderStatusEnum.DELIVERED.getID()) {
            throw new ValidationException(messages.getString("exception.error.status_cancel_delivered"));
        }
    }

    @Override
    public void validateTypeOfChangingStatus(LocaleApplication localeApplication, String type) {
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        if (!type.equals("cancel") && !type.equals("pay")) {
            throw new ValidationException(messages.getString("exception.error.status_type"));
        }
    }
}
