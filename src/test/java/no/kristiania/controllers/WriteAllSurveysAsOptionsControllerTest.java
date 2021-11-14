package no.kristiania.controllers;


import no.kristiania.dao.*;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpGetClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteAllSurveysAsOptionsControllerTest {
    private final HttpServer server = new HttpServer(0);

    public WriteAllSurveysAsOptionsControllerTest() throws IOException {
    }


    @Test
    void shouldHandleHttpMessage() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        UserDao userDao = new UserDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        Survey survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);

        server.addController("GET /api/all-surveys", new WriteAllSurveysAsOptionsController(surveyDao));


        HttpGetClient httpGetClient = new HttpGetClient(
                "localhost",
                server.getPort(),
                "/api/all-surveys"
        );
        assertEquals(200, httpGetClient.getStatusCode());

        assertEquals(httpGetClient.getMessageBody(), "<option value=\"Test survey name\"onclick=\"removeDisabled()\">Test survey name</option>");

        surveyDao.deleteAll();
    }
}
