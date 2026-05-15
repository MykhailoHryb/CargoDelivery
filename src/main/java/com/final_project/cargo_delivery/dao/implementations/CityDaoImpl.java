package com.final_project.cargo_delivery.dao.implementations;

import com.final_project.cargo_delivery.config.DBCPDataSource;
import com.final_project.cargo_delivery.dao.interfaces.CityDao;
import com.final_project.cargo_delivery.entity.City;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * CityDao implementation
 *
 * @author Mykhailo Hryb
 */
@Repository
public class CityDaoImpl implements CityDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityDaoImpl.class);

    private static final String SQL_GET_CITY_BY_ID = "SELECT cities.*, cities_l10n.name " +
            "FROM cities INNER JOIN cities_l10n ON cities.id = cities_l10n.city_id " +
            "WHERE cities.id = ? AND locale_id = ?";

    private static final String SQL_GET_ALL_CITIES = "SELECT cities.*, cities_l10n.name " +
            "FROM cities INNER JOIN cities_l10n ON cities.id = cities_l10n.city_id " +
            "WHERE locale_id = ? ORDER BY #order_column#";

    private static final String SQL_GET_ALL_CITIES_WITH_FILTER_IS_FOREIGN = "SELECT cities.*, cities_l10n.name " +
            "FROM cities INNER JOIN cities_l10n ON cities.id = cities_l10n.city_id " +
            "WHERE locale_id = ? AND is_foreign = ? ORDER BY #order_column#";

    private static final List<String> COLUMNS_TO_ORDER = Arrays.asList("id", "is_foreign", "name");


    @Override
    public City getCityById(int cityId, LocaleApplication localeApplication) {
        City city = new City();
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_CITY_BY_ID)) {
            preparedStatement.setInt(1, cityId);
            preparedStatement.setInt(2, localeApplication.getLocaleId());
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                city = setCityFromResultSet(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error while getting city {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.error_getting_city"));
        }
        return city;
    }


    @Override
    public List<City> getAllCities(LocaleApplication localeApplication, String orderBy) {
        List<City> cities = new ArrayList<>();
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (!COLUMNS_TO_ORDER.contains(orderBy)) {
            throw new ApplicationException(messages.getString("exception.error.error_sorting"));
        }

        String sqlWithSort = SQL_GET_ALL_CITIES.replace("#order_column#", orderBy);

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlWithSort)) {
            preparedStatement.setInt(1, localeApplication.getLocaleId());
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                City city = setCityFromResultSet(resultSet);
                cities.add(city);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error while getting cities {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.error_getting_cities"));
        }
        return cities;
    }

    @Override
    public List<City> getAllCitiesWithFilterIsForeign(LocaleApplication localeApplication, String orderBy, int isForeign) {
        List<City> cities = new ArrayList<>();
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));

        if (!COLUMNS_TO_ORDER.contains(orderBy)) {
            throw new ApplicationException(messages.getString("exception.error.error_sorting"));
        }

        String sqlWithSort = SQL_GET_ALL_CITIES_WITH_FILTER_IS_FOREIGN.replace("#order_column#", orderBy);

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlWithSort)) {
            preparedStatement.setInt(1, localeApplication.getLocaleId());
            preparedStatement.setInt(2, isForeign);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                City city = setCityFromResultSet(resultSet);
                cities.add(city);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error while getting cities {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.error_getting_cities"));
        }

        return cities;
    }

    private City setCityFromResultSet(ResultSet resultSet) throws SQLException {
        City city = new City();
        city.setId(resultSet.getInt("id"));
        city.setLatitude(resultSet.getLong("latitude"));
        city.setLongitude(resultSet.getLong("longitude"));
        city.setName(resultSet.getString("name"));
        city.setImgPath(resultSet.getString("img_path"));
        city.setIsForeign(resultSet.getBoolean("is_foreign"));
        return city;
    }
}
