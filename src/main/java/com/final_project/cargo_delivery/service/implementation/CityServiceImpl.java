package com.final_project.cargo_delivery.service.implementation;

import com.final_project.cargo_delivery.converter.CityConverter;
import com.final_project.cargo_delivery.dao.implementations.CityDaoImpl;
import com.final_project.cargo_delivery.dao.interfaces.CityDao;
import com.final_project.cargo_delivery.entity.City;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.service.interfaces.CityService;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CityService implementation
 *
 * @author Mykhailo Hryb
 */
@Service
public class CityServiceImpl implements CityService {

    private CityDao cityDao;
    private CityConverter cityConverter;

    @Autowired
    CityServiceImpl(CityDao cityDao, CityConverter cityConverter) {
        this.cityDao = cityDao;
        this.cityConverter = cityConverter;
    }

    @Override
    public List<CityViewDto> getAllCities(LocaleApplication localeApplication, String orderBy) {
        List<City> city = cityDao.getAllCities(localeApplication, orderBy);
        return city.stream().map(
                cityItem -> cityConverter.convertCityToCityViewDto(cityItem)
        ).collect(Collectors.toList());
    }

    @Override
    public List<CityViewDto> getAllCitiesWithFilterIsForeign(LocaleApplication localeApplication, String orderBy,
                                                             int isForeign) {
        List<City> city = cityDao.getAllCitiesWithFilterIsForeign(localeApplication, orderBy, isForeign);
        return city.stream().map(
                cityItem -> cityConverter.convertCityToCityViewDto(cityItem)
        ).collect(Collectors.toList());
    }

    @Override
    public CityViewDto getCityById(LocaleApplication localeApplication, int cityId) {
        City city = cityDao.getCityById(cityId, localeApplication);
        return cityConverter.convertCityToCityViewDto(city);
    }
}
