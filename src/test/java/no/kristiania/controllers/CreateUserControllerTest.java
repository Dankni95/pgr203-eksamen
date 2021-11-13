package no.kristiania.controllers;

import no.kristiania.dao.UserDao;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.utils.Cookie;

import java.sql.SQLException;
import java.util.Map;

public class CreateUserControllerTest {
    private final UserDao userDao;

    public CreateUserControllerTest(UserDao userDao) {
        this.userDao = userDao;

    }
}
