package no.kristiania.controllers;

import no.kristiania.dao.UserDao;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.utils.Cookie;

import java.sql.SQLException;
import java.util.Map;

public class CreateUserController implements HttpController {
    private final UserDao userDao;

    public CreateUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);

        User user = new User();
        user.setFirstName(parameters.get("first-name"));
        user.setLastName(parameters.get("last-name"));
        user.setEmail(parameters.get("email"));
        userDao.save(user);

        Cookie.setCookie(user);

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/", "Set-cookie", "user=" + user.getCookie());
    }
}
