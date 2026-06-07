package server.db;


import common.PersonData;
import common.domain.Coordinates;
import common.domain.Country;
import common.domain.Location;
import common.domain.Person;
import server.services.PersonFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PersonDAO {
    private final DatabaseManager databaseManager;

    public PersonDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public long insert(PersonData data, int ownerId, java.util.Date creationDate) throws SQLException {
        String sql = """
            INSERT INTO lab7_persons (
                name,
                coordinates_x,
                coordinates_y,
                creation_date,
                height,
                birthday,
                passport_id,
                nationality,
                location_x,
                location_y,
                location_z,
                owner_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
            """;
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, data.name());
            statement.setInt(2, data.coordinates().getX());
            statement.setInt(3, data.coordinates().getY());
            statement.setTimestamp(4, new java.sql.Timestamp(creationDate.getTime()));
            statement.setDouble(5, data.height());
            statement.setDate(6, java.sql.Date.valueOf(data.birthday()));
            statement.setString(7, data.passportId());
            if (data.nationality() == null) {
                statement.setNull(8, java.sql.Types.VARCHAR);
            } else {
                statement.setString(8, data.nationality().name());
            }
            statement.setFloat(9, data.location().getX());
            statement.setInt(10, data.location().getY());
            statement.setDouble(11, data.location().getZ());
            statement.setInt(12, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                   return resultSet.getLong("id");
                }
            throw new SQLException("База не вернула id созданного объекта");
            }
        }
    }

    public boolean update(long id, PersonData data, int ownerId) throws SQLException {
        String sql = """
                UPDATE lab7_persons 
                SET name = ?,
                    coordinates_x = ?,
                    coordinates_y = ?,
                    height = ?,
                    birthday = ?,
                    passport_id = ?,
                    nationality = ?,
                    location_x = ?,
                    location_y = ?,
                    location_z = ?
                WHERE id = ? AND owner_id = ?  
                """;
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, data.name());
            statement.setInt(2, data.coordinates().getX());
            statement.setInt(3, data.coordinates().getY());
            statement.setDouble(4, data.height());
            statement.setDate(5, java.sql.Date.valueOf(data.birthday()));
            statement.setString(6, data.passportId());
            if (data.nationality() == null) {
                statement.setNull(7, java.sql.Types.VARCHAR);
            } else {
                statement.setString(7, data.nationality().name());
            }
            statement.setFloat(8, data.location().getX());
            statement.setInt(9, data.location().getY());
            statement.setDouble(10, data.location().getZ());
            statement.setLong(11, id);
            statement.setInt(12, ownerId);
            int rows = statement.executeUpdate();
            return rows > 0;
            }
    }


    public boolean removeById(long id, int ownerId) throws SQLException {
        String sql ="""
                DELETE FROM lab7_persons
                WHERE id = ? AND owner_id = ?
                """;
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            statement.setInt(2, ownerId);
            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public java.util.List<Long> clearByOwner(int ownerId) throws SQLException {
        String sql = """
                DELETE FROM lab7_persons
                WHERE owner_id = ?
                RETURNING id
                """;

        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, ownerId);
            java.util.List<Long> removedIds = new java.util.ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    removedIds.add(resultSet.getLong("id"));
                }
            }
            return removedIds;
        }
    }

    public int removeGreaterKey(long key, int ownerId) throws SQLException {
        String sql = """
                DELETE FROM lab7_persons
                WHERE id > ? AND owner_id = ?
                """;
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setLong(1, key);
            statement.setInt(2, ownerId);
            return statement.executeUpdate();
        }
    }
    public List<Long> removeByIds(Collection<Long> ids, int ownerId) throws SQLException {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        String placeholders = String.join(", ", Collections.nCopies(ids.size(), "?"));
        String sql = """
                DELETE FROM lab7_persons
                WHERE owner_id = ? AND id IN (%s)
                RETURNING id
                """.formatted(placeholders);
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, ownerId);
            int index = 2;
            for (Long id : ids) {
                statement.setLong(index, id);
                index++;
            }
            List<Long> removedIds = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    removedIds.add(resultSet.getLong("id"));
                }
            }
            return removedIds;
        }
    }

    public Map<Long, Person> loadCollection(PersonFactory personFactory) throws SQLException {
        String sql = """
                SELECT id,
                       name,
                       coordinates_x,
                       coordinates_y,
                       creation_date,
                       height,
                       birthday,
                       passport_id,
                       nationality,
                       location_x,
                       location_y,
                       location_z,
                       owner_id
                FROM lab7_persons
                """;

        Map<Long, Person> map = new HashMap<>();
        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Coordinates coordinates = new Coordinates(
                        resultSet.getInt("coordinates_x"),
                        resultSet.getInt("coordinates_y")
                );
                java.util.Date creationDate = new java.util.Date(
                        resultSet.getTimestamp("creation_date").getTime()
                );
                double height = resultSet.getDouble("height");
                java.time.LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                String passportId = resultSet.getString("passport_id");
                String nationalityString = resultSet.getString("nationality");
                Country nationality = null;

                if (nationalityString != null) {
                    nationality = Country.valueOf(nationalityString);
                }
                Location location = new Location(
                        resultSet.getFloat("location_x"),
                        resultSet.getInt("location_y"),
                        resultSet.getDouble("location_z")
                );
                Person person = personFactory.create(
                        id,
                        name,
                        coordinates,
                        creationDate,
                        height,
                        birthday,
                        passportId,
                        nationality,
                        location
                );
                map.put(id, person);
            }
        }
        return map;
    }
}
