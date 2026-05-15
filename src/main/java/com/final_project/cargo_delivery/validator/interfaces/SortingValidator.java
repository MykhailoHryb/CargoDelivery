package com.final_project.cargo_delivery.validator.interfaces;

import com.final_project.cargo_delivery.entity.TypeSorting;

/**
 * SortingValidator
 *
 * @author Mykhailo Hryb
 */
public interface SortingValidator {

    /**
     * Validates parameters sort by and sets default value if not acceptable
     *
     * @param sortBy
     * @param defaultValue
     * @return
     */
    String validateSortBy(String sortBy, String defaultValue);

    /**
     * Validates type of sorting (ASC or DESC)
     *
     * @param typeSort
     * @return
     */
    TypeSorting validateTypeSorting(String typeSort);
}
