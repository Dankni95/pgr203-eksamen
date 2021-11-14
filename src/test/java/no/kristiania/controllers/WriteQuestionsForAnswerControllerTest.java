package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpGetClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteQuestionsForAnswerControllerTest {
    private final HttpServer server = new HttpServer(0);

    public WriteQuestionsForAnswerControllerTest() throws IOException {
    }

    @Test
    void shouldHandleHttpMessage() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        Survey survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);

        server.addController("GET /api/answers", new WriteQuestionsForAnswerController(questionDao));

        HttpGetClient httpGetClient = new HttpGetClient(
                "localhost",
                server.getPort(),
                "/api/answers"
        );
        assertEquals(200, httpGetClient.getStatusCode());

        assertEquals(httpGetClient.getMessageBody(), "<form action=\"api/answers\" method=\"POST\"><label value=\"select\">Choose question to see answers</label><select name=\"questionId\"></select><button>Go to selected question</button>");

        surveyDao.deleteAll();
    }
}
