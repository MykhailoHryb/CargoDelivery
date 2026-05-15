package com.final_project.cargo_delivery.web.util;

import com.final_project.cargo_delivery.validator.implementation.PaginationValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.PaginationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Pagination.
 *
 * @author Mykhailo Hryb
 */
public class Pagination {

    private final int step;
    private final int paginationItem;

    private static final Logger LOGGER = LoggerFactory.getLogger(Pagination.class);

    public Pagination(HttpServletRequest request, int countElements) {
        PaginationValidator paginationValidator = new PaginationValidatorImpl();
        String stepStr = request.getParameter("pagination_step");
        String paginationItemStr = request.getParameter("pagination_item");

        this.step = paginationValidator.validatePagination(PaginationValidatorImpl.DEFAULT_PAGINATION_STEP,
                stepStr, countElements);
        int countPaginationElements = (int) Math.ceil((double) countElements / step);
        this.paginationItem = paginationValidator.validatePagination(PaginationValidatorImpl.DEFAULT_PAGINATION_STEP_ITEM,
                paginationItemStr, countPaginationElements);

        request.setAttribute("stepPagen", step);
        request.setAttribute("paginationItem", paginationItem);
        request.setAttribute("countPaginationElements", countPaginationElements);

        LOGGER.info("countElements = {}", countElements);
        LOGGER.info("step = {}", step);
        LOGGER.info("paginationItem = {}", paginationItem);
        LOGGER.info("countPaginationElements = {}", countPaginationElements);
    }

    public int getStep() {
        return this.step;
    }

    public int getPaginationItem() {
        return this.paginationItem;
    }
}
