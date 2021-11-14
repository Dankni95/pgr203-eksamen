package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.User;
import no.kristiania.http.*;
import no.kristiania.utils.Cookie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteUserControllerTest {
    private final HttpServer server = new HttpServer(0);

    public WriteUserControllerTest() throws IOException {
    }

    @Test
    void shouldHandleHttpMessage() throws SQLException, IOException {

        UserDao userDao = new UserDao(TestData.testDataSource());

        User user = new User();
        user.setFirstName("Kari"); // Annon
        user.setLastName("Normann");
        user.setEmail("kari@live.no");

        Cookie.setCookie(user);

        server.addController("POST /api/user", new CreateUserController(userDao));

        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/user",
                "first-name=Kari&last-name=Normann&email=kari%40live.no"
        );

        assertEquals(303, postClient.getStatusCode());

        assertThat(userDao.listAll())
                .anySatisfy(p -> {
                    assertThat(p.getFirstName()).isEqualTo("Kari");
                    assertThat(p.getLastName()).isEqualTo("Normann");
                    assertThat(p.getEmail()).isEqualTo("kari@live.no");
                });

        server.addController("GET /api/user", new WriteUserController());

        HttpGetClient httpGetClient = new HttpGetClient(
                "localhost",
                server.getPort(),
                "/api/user",
                user.getCookie()
        );

        assertEquals(200, httpGetClient.getStatusCode());
        assertEquals(httpGetClient.getMessageBody().split(" ")[0], user.getFirstName());
        assertEquals(httpGetClient.getMessageBody().split(" ")[1], user.getLastName());
    }
}
