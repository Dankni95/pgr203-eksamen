package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteAllSurveysControllerTest {
    private final SurveyDao surveyDao;
    private final WriteAllSurveysController writeAllSurveysController;

    public WriteAllSurveysControllerTest(SurveyDao surveyDao, WriteAllSurveysController writeAllSurveysController) {
        this.surveyDao = surveyDao;
        this.writeAllSurveysController = writeAllSurveysController;
    }

    @Test
    void shouldHandleHttpMessage() throws SQLException {
        String httpMessage = "HTTP/1.1 200 OK";
        StringBuilder messageBody = new StringBuilder();

        for (Survey survey : surveyDao.listAll()) {
            messageBody.append("<option value=").append("\"").append(survey.getTitle()).append("\"").append("onclick=\"removeDisabled()\">").append(survey.getTitle()).append("</option>");
        }
        assertEquals(httpMessage + messageBody, writeAllSurveysController.handle("SOMETHING") );
    }
}
