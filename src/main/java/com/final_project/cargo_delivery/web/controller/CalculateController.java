package com.final_project.cargo_delivery.web.controller;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.implementation.DeliveryCalculatorServiceImpl;
import com.final_project.cargo_delivery.service.implementation.LocaleServiceImpl;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.service.interfaces.DeliveryCalculatorService;
import com.final_project.cargo_delivery.service.interfaces.LocaleService;
import com.final_project.cargo_delivery.service.interfaces.TypeCargoService;
import com.final_project.cargo_delivery.validator.implementation.CostCalculatorValidatorImpl;
import com.final_project.cargo_delivery.validator.interfaces.CostCalculatorValidator;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import com.final_project.cargo_delivery.web.dto.TypeCargoViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CalculateController calculates price for delivery and calculates time for delivery
 *
 * @author Mykhailo Hryb
 */
@Controller
public class CalculateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculateController.class);

    private CityService cityService;
    private TypeCargoService typeCargoService;

    @Autowired
    CalculateController(CityService cityService, TypeCargoService typeCargoService) {
        this.cityService = cityService;
        this.typeCargoService = typeCargoService;
    }

    @PostMapping("/calculate-distance")
    public ModelAndView calculate(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();

        LocaleApplication localeApplication;
        LocaleService localeService = new LocaleServiceImpl();
        Cookie[] arrCookies = request.getCookies();
        localeApplication = localeService.getLocaleByCookieOrDefault(arrCookies);
        DeliveryCalculatorService deliveryCalculatorService = new DeliveryCalculatorServiceImpl();

        CostCalculatorValidator costCalculatorValidator = new CostCalculatorValidatorImpl();

        String cityFromIdStr = request.getParameter("cityFromId");
        String cityToIdStr = request.getParameter("cityToId");
        String weightStr = request.getParameter("weight");
        String volumeStr = request.getParameter("volume");

        costCalculatorValidator.validateCitiesParameters(localeApplication, cityFromIdStr, cityToIdStr);
        costCalculatorValidator.validateCostParameters(localeApplication, weightStr, volumeStr);

        int cityFromId = Integer.parseInt(cityFromIdStr);
        int cityToId = Integer.parseInt(cityToIdStr);
        int weight = Integer.parseInt(weightStr);
        int volume = Integer.parseInt(volumeStr);

        LOGGER.info("cityFromId = {}", cityFromIdStr);
        LOGGER.info("cityToId = {}", cityToIdStr);
        LOGGER.info("weight = {}", weight);
        LOGGER.info("volume = {}", volume);

        CityViewDto cityFrom = cityService.getCityById(localeApplication, cityFromId);
        CityViewDto cityTo = cityService.getCityById(localeApplication, cityToId);
        costCalculatorValidator.validateSameCities(localeApplication, cityFrom, cityTo);

        int price = deliveryCalculatorService.calculatePriceDelivery(cityFrom, cityTo, weight, volume, localeApplication);
        model.put("calculated_price", price);

        LocalDate dateToDeliver = deliveryCalculatorService.getTimeToDelivery(cityFrom, cityTo, localeApplication);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateToShowDeliver = dateFormatter.format(dateToDeliver);
        model.put("date_deliver", dateToShowDeliver);

        List<TypeCargoViewDto> typeCargoViewDtoList = typeCargoService.getAllTypesCargos(localeApplication);
        model.put("typeCargos", typeCargoViewDtoList);

        return new ModelAndView("calculated_result","model", model);
    }

}
