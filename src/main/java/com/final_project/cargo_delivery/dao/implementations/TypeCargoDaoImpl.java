package com.final_project.cargo_delivery.dao.implementations;

import com.final_project.cargo_delivery.config.DBCPDataSource;
import com.final_project.cargo_delivery.dao.interfaces.TypeCargoDao;
import com.final_project.cargo_delivery.entity.LocaleApplication;
import com.final_project.cargo_delivery.entity.TypeCargo;
import com.final_project.cargo_delivery.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * TypeCargoDao implementation
 *
 * @author Mykhailo Hryb
 */
@Repository
public class TypeCargoDaoImpl implements TypeCargoDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeCargoDaoImpl.class);

    private static final String SQL_GET_ALL_TYPES_CARGO = "SELECT types_cargos.*,  types_cargos_l10n.name " +
            "FROM types_cargos INNER JOIN types_cargos_l10n ON types_cargos.id = types_cargos_l10n.cargo_id " +
            "WHERE locale_id = ?";

    private static final String SQL_GET_TYPE_CARGO_BY_ID = "SELECT types_cargos.*,  types_cargos_l10n.name " +
            "FROM types_cargos INNER JOIN types_cargos_l10n ON types_cargos.id = types_cargos_l10n.cargo_id " +
            "WHERE locale_id = ? AND types_cargos.id = ?";

    @Override
    public List<TypeCargo> getAllTypesCargos(LocaleApplication localeApplication) {
        List<TypeCargo> typeCargoList = new ArrayList<>();
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_TYPES_CARGO)) {
            preparedStatement.setInt(1, localeApplication.getLocaleId());
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TypeCargo typeCargo = fillTypeCargoFromDataBase(resultSet);
                typeCargoList.add(typeCargo);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error while getting all type cargos {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_find_types_cargos"));
        }

        return typeCargoList;
    }

    @Override
    public TypeCargo getCargoById(LocaleApplication localeApplication, int cargoId) {
        TypeCargo typeCargo = new TypeCargo();
        ResourceBundle messages = ResourceBundle.getBundle("messages",
                new Locale(localeApplication.getShortName()));
        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_TYPE_CARGO_BY_ID)) {
            preparedStatement.setInt(1, localeApplication.getLocaleId());
            preparedStatement.setInt(2, cargoId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                typeCargo = fillTypeCargoFromDataBase(resultSet);
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error while getting type cargo {}", sqlException.getMessage());
            throw new ApplicationException(messages.getString("exception.error.cannot_find_type_cargo"));
        }

        return typeCargo;
    }

    private TypeCargo fillTypeCargoFromDataBase(ResultSet resultSet) throws SQLException {
        TypeCargo typeCargo = new TypeCargo();
        typeCargo.setId(resultSet.getInt("id"));
        typeCargo.setName(resultSet.getString("name"));
        typeCargo.setImagePath(resultSet.getString("img_path"));
        return typeCargo;
    }
}
