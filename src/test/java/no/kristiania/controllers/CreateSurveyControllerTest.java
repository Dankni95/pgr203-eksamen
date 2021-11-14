package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.http.HttpPostClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateSurveyControllerTest {
    private final HttpServer server = new HttpServer(0);

    public CreateSurveyControllerTest() throws IOException {
    }

    @Test
    void shouldCreateNewQuestion() throws IOException, SQLException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        UserDao userDao = new UserDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        server.addController("POST /api/new-survey", new CreateSurveyController(questionDao, optionDao, userDao, surveyDao));


        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/new-survey",
                "surveys=previous&new-survey=New+survey&user=Anonymous+&title=Question+title&text=Question+subtitle&option_1=Option+1&option_2=Option+2&option_3=Option+3&option_4=Option+4&option_5=Option+5"
        );
        assertEquals(303, postClient.getStatusCode());

        assertThat(questionDao.listAll())
                .anySatisfy(p -> {
                    assertThat(p.getTitle("question title")).isEqualTo("Question title");
                    assertThat(p.getText()).isEqualTo("Question subtitle");
                });

        assertThat(optionDao.listAll())
                .anySatisfy(o -> {
                    assertThat(o.getTitle()).isEqualTo("Option 1");
                    assertThat(o.getQuestionId()).isEqualTo(1);
                });

        optionDao.deleteAll();
        questionDao.deleteAll();
    }
}
