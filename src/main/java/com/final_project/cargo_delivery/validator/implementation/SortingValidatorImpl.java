package com.final_project.cargo_delivery.validator.implementation;

import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.validator.interfaces.SortingValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * SortingValidator implementation
 *
 * @author Mykhailo Hryb
 */
@Component
public class SortingValidatorImpl implements SortingValidator {

    @Override
    public String validateSortBy(String sortBy, String defaultValue) {
        if (StringUtils.isEmpty(sortBy)) {
            sortBy = defaultValue;
        }
        return sortBy;
    }

    @Override
    public TypeSorting validateTypeSorting(String typeSort) {
        TypeSorting typeSorting;
        if (!StringUtils.isEmpty(typeSort) && typeSort.equals("DESC")) {
            typeSorting = TypeSorting.DESC;
        } else {
            typeSorting = TypeSorting.ASC;
        }
        return typeSorting;
    }
}
