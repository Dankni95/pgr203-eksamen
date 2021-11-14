package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.Survey;
import no.kristiania.entity.User;
import no.kristiania.http.HttpGetClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteAllSurveysControllerTest {
    private final HttpServer server = new HttpServer(0);

    public WriteAllSurveysControllerTest() throws IOException {
    }

    @Test
    void ShouldWriteAllSurveys() throws SQLException, IOException {
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
        UserDao userDao = new UserDao(TestData.testDataSource());

        User annon = new User();
        annon.setFirstName("Annon");
        annon.setLastName("Annon");
        annon.setEmail("Annon@annon.no");
        userDao.save(annon);

        Survey survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);

        Survey secondSurvey = new Survey();
        secondSurvey.setUserId(1); // Annon
        secondSurvey.setTitle("Second test survey name");
        surveyDao.save(secondSurvey);

        Survey thirdSurvey = new Survey();
        thirdSurvey.setUserId(1); // Annon
        thirdSurvey.setTitle("Third test survey name");
        surveyDao.save(thirdSurvey);

        server.addController("GET /api/surveys", new WriteAllSurveysController(surveyDao));

        HttpGetClient httpGetClient = new HttpGetClient(
                "localhost",
                server.getPort(),
                "/api/surveys"
        );
        assertEquals(200, httpGetClient.getStatusCode());

        assertEquals(httpGetClient.getMessageBody(), "<form action=\"/survey.html\" method=\"GET\"><label value=\"sELECT\">Choose survey to answer</label><input type=\"radio\" name=\"Test survey name\" value=\"1\">Test survey name</input><br><input type=\"radio\" name=\"Second test survey name\" value=\"2\">Second test survey name</input><br><input type=\"radio\" name=\"Third test survey name\" value=\"3\">Third test survey name</input><br><button type=\"submit\" value=\"Submit\">\n" +
                "Go to selected survey</button></form>");

        surveyDao.deleteAll();
    }
}
