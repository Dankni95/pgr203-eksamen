package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public class WriteAllSurveysAsOptionsController implements HttpController {
    private final SurveyDao surveyDao;

    public WriteAllSurveysAsOptionsController(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        StringBuilder messageBody = new StringBuilder();

        for (Survey survey : surveyDao.listAll()) {
            messageBody.append("<option value=").append("\"").append(survey.getTitle()).append("\"").append("onclick=\"removeDisabled()\">").append(survey.getTitle()).append("</option>");
        }

        return new HttpMessage("HTTP/1.1 200 OK", messageBody.toString());
    }
}
