package server.db;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private final DatabaseManager databaseManager;
    public UserDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public boolean existsByLogin(String login) throws SQLException {
        String sql = "SELECT 1 FROM lab7_users WHERE login = ?";
        try (
            Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
    ) {
        statement.setString(1, login);
        try (ResultSet resultSet = statement.executeQuery()) {
            return resultSet.next();
        }
    }
    }

    public void register(String login, String passwordHash) throws SQLException {
    String sql = "INSERT INTO lab7_users (login, password_hash) VALUES (?, ?)";

    try (
            Connection connection = databaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, login);
            statement.setString(2, passwordHash);
            statement.executeUpdate();
        }
    }

    public Integer findUserIdByLoginAndPasswordHash(String login, String passwordHash) throws SQLException {
        String sql = "SELECT id FROM lab7_users WHERE login = ? AND password_hash = ?";

        try (
                Connection connection = databaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
                ) {
            statement.setString(1, login);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
                return null;
            }
        }
    }
}
