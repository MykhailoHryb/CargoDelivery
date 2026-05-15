package com.final_project.cargo_delivery.dao.interfaces;

import com.final_project.cargo_delivery.entity.LocaleApplication;

/**
 * Data access object for Locale entity
 *
 * @author Mykhailo Hryb
 */
public interface LocaleDao {

    /**
     * Gets locale from database by short name of locale
     *
     * @param shortName
     * @return
     */
    LocaleApplication getLocaleByShortName(String shortName);
}
