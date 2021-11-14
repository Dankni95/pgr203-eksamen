package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.http.HttpPostClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAnswerControllerTest {
    private final HttpServer server = new HttpServer(0);

    public CreateAnswerControllerTest() throws IOException {
    }

    @Test
    void shouldCreateNewAnswer() throws IOException, SQLException {
        /*QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        UserDao userDao = new UserDao(TestData.testDataSource());
        AnswerDao answerDao = new AnswerDao(TestData.testDataSource());

        server.addController("POST /api/answers", new CreateAnswerController(questionDao, answerDao, optionDao));


        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/answers",
                "survey=Transport%3D2&Transport=ikke+i+det+hele+tatt%3DTransport&survey=Transport%3D2"
        );
        assertEquals(303, postClient.getStatusCode());

        assertThat(answerDao.listAll())
                .anySatisfy(a -> {
                    assertThat(a.getId()).isEqualTo("id");
                    assertThat(a.getQuestionId()).isEqualTo(2);
                    assertThat(a.getOptionId()).isEqualTo("Option id");
                    assertThat(a.getUserId()).isEqualTo("User id");
                });
    */}
}