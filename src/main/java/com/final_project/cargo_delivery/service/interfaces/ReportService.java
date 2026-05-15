package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.OrderViewDto;

import java.util.List;

/**
 * ReportService
 *
 * @author Mykhailo Hryb
 */
public interface ReportService {

    /**
     * Makes report of orders by city to deliver from
     *
     * @param localeApplication
     * @param orderViewDtoList
     * @return
     */
    String makeReportByCity(LocaleApplication localeApplication, List<OrderViewDto> orderViewDtoList);

    /**
     * Makes report of orders by date of delivery
     *
     * @param localeApplication
     * @param orderViewDtoList
     * @return
     */
    String makeReportByDate(LocaleApplication localeApplication, List<OrderViewDto> orderViewDtoList);
}
