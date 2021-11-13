package no.kristiania.controllers;

import no.kristiania.dao.UserDao;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.utils.Cookie;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserControllerTest {
    private final UserDao userDao;

    public CreateUserControllerTest(UserDao userDao) {
        this.userDao = userDao;

    }

    @Test
    void shouldCreateUser() throws SQLException {
        User user = new User();
        user.setFirstName("Anonym");
        user.setLastName("Anonymesen");
        user.setEmail("ano@mail.com");
        userDao.save(user);

        assertEquals(user, userDao.listAll());
    }
}
