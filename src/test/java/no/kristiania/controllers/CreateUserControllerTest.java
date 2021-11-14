package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.User;
import no.kristiania.http.*;
import no.kristiania.utils.Cookie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserControllerTest {
    private final HttpServer server = new HttpServer(0);

    public CreateUserControllerTest() throws IOException {
    }

    @Test
    void shouldHandleHttpMessage() throws SQLException, IOException {
        UserDao userDao = new UserDao(TestData.testDataSource());
        userDao.deleteAll();

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

    }
}
