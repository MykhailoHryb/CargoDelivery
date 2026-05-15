package com.final_project.cargo_delivery.converter;

import com.final_project.cargo_delivery.entity.City;
import com.final_project.cargo_delivery.web.dto.CityViewDto;
import org.springframework.stereotype.Component;

/**
 * CityConverter
 *
 * @author Mykhailo Hryb
 */
@Component
public class CityConverter {

    /**
     * Converts city to CityViewDto
     *
     * @param city
     * @return
     */
    public CityViewDto convertCityToCityViewDto(City city) {
        CityViewDto cityViewDto = new CityViewDto();
        cityViewDto.setId(city.getId());
        cityViewDto.setName(city.getName());
        cityViewDto.setImgPath(city.getImgPath());
        cityViewDto.setIsForeign(city.getIsForeign());
        cityViewDto.setLatitude(city.getLatitude());
        cityViewDto.setLongitude(city.getLongitude());
        return cityViewDto;
    }
}
