package com.final_project.cargo_delivery.dao.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.TypeCargo;

import java.util.List;

/**
 * Data access object for TypeCargo entity
 *
 * @author Mykhailo Hryb
 */
public interface TypeCargoDao {

    /**
     * Gets all type of cargos
     *
     * @param localeApplication
     * @return TypeCargo
     */
    List<TypeCargo> getAllTypesCargos(LocaleApplication localeApplication);

    /**
     * Gets cargo by id
     *
     * @param localeApplication
     * @param cargoId
     * @return TypeCargo
     */
    TypeCargo getCargoById(LocaleApplication localeApplication, int cargoId);
}
