package server.auth;

import server.db.DatabaseManager;
import server.db.UserDAO;

import java.sql.SQLException;

public class AuthService {
    private final UserDAO userDAO;
    private final PasswordHasher passwordHasher;


    public AuthService(UserDAO userDAO, PasswordHasher passwordHasher) {
        this.userDAO = userDAO;
        this.passwordHasher = passwordHasher;
    }

    public void register(String login, String password) throws SQLException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Логин и пароль не могут быть пустыми");
        }

        if (userDAO.existsByLogin(login)) {
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");
        }

        String passwordHash = passwordHasher.hash(password);
        userDAO.register(login, passwordHash);
    }

    public Integer authenticate(String login, String password) throws SQLException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return null;
        }

        String passwordHash = passwordHasher.hash(password);
        return userDAO.findUserIdByLoginAndPasswordHash(login, passwordHash);
    }
}
