package com.final_project.cargo_delivery.validator.interfaces;

/**
 * PaginationValidator
 *
 * @author Mykhailo Hryb
 */
public interface PaginationValidator {

    /**
     * Validates pagination parameters (acceptable for step and item)
     *
     * @param defaultValue
     * @param paginationStep
     * @param countElements
     * @return
     */
    int validatePagination(int defaultValue, String paginationStep, int countElements);
}
