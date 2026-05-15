package com.final_project.cargo_delivery.web.util;

import com.final_project.cargo_delivery.entity.TypeSorting;
import com.final_project.cargo_delivery.validator.implementation.SortingValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.SortingValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Sorting.
 *
 * @author Mykhailo Hryb
 */
public class Sorting {

    private final String sorting;
    private final TypeSorting typeSorting;

    private static final Logger LOGGER = LoggerFactory.getLogger(Sorting.class);

    public Sorting(HttpServletRequest request, String defaultSortBy) {
        SortingValidator sortingValidator = new SortingValidatorImpl();
        String sortBy = request.getParameter("orderBy");
        this.sorting = sortingValidator.validateSortBy(sortBy, defaultSortBy);
        String typeSortStr = request.getParameter("typeSort");
        this.typeSorting = sortingValidator.validateTypeSorting(typeSortStr);

        LOGGER.info("sort by after = {}", sortBy);
        LOGGER.info("typeSorting = {}", typeSorting);

        request.setAttribute("sortBy", sortBy);
        request.setAttribute("typeSorting", typeSorting.name());
    }

    public String getSorting() {
        return this.sorting;

    }

    public TypeSorting getTypeSorting() {
        return this.typeSorting;
    }
}
