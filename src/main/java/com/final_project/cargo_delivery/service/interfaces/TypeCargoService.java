package com.final_project.cargo_delivery.service.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.web.dto.TypeCargoViewDto;

import java.util.List;

/**
 * TypeCargoService
 *
 * @author Mykhailo Hryb
 */
public interface TypeCargoService {

    /**
     * Gets all type of cargos
     *
     * @param localeApplication
     * @return TypeCargoViewDto
     */
    List<TypeCargoViewDto> getAllTypesCargos(LocaleApplication localeApplication);

    /**
     * Gets type of cargo by id
     *
     * @param localeApplication
     * @param typeCargoId
     * @return
     */
    TypeCargoViewDto getTypeCargoById(LocaleApplication localeApplication, int typeCargoId);
}
