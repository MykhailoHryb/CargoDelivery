package com.final_project.cargo_delivery.dao.implementations;

import com.final_project.cargo_delivery.config.DBCPDataSource;
import com.final_project.cargo_delivery.dao.interfaces.LocaleDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * LocaleDao implementation
 *
 * @author Mykhailo Hryb
 */
@Repository
public class LocaleDaoImpl implements LocaleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleDaoImpl.class);
    private static final String SQL_GET_LOCALE_BY_SHORT_NAME = "SELECT * FROM locales AS locale  WHERE locale.short_name = (?)";

    @Override
    public LocaleApplication getLocaleByShortName(String shortName) {
        final LocaleApplication resultLocaleApplication = new LocaleApplication();
        resultLocaleApplication.setIdLocale(-1);
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_LOCALE_BY_SHORT_NAME)) {
            statement.setString(1, shortName);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultLocaleApplication.setIdLocale(Integer.parseInt(rs.getString("id")));
                resultLocaleApplication.setFullName(rs.getString("full_name"));
                resultLocaleApplication.setShortName((rs.getString("short_name")));
            }
        } catch (SQLException e) {
            LOGGER.error("Error while getting Locale element", e);
            throw new ApplicationException("Error while getting Locale element", e);
        }
        return resultLocaleApplication;
    }
}
