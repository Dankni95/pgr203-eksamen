package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class WriteAllSurveysController implements HttpController {
    private final SurveyDao surveyDao;

    public WriteAllSurveysController(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        if (surveyDao.listAll().isEmpty()) {
            return new HttpMessage("HTTP/1.1 200 OK", "<h3 style=\"text-align:center;\">There is no surveys at this time, create a survey</h3>");
        } else {
            StringBuilder responseText = new StringBuilder();

            responseText.append("<form action=\"/survey.html\" method=\"GET\">")
                    .append("<label value=\"").append("sELECT").append("\">").append("Choose survey to answer").append("</label>");

            for (Survey survey : surveyDao.listAll()) {
                responseText
                        .append("<input type=\"radio\" name=\"").append(survey.getTitle()).append("\"")
                        .append(" value=\"").append(survey.getId()).append("\"").append(">").append(survey.getTitle()).append("</input>")
                        .append("<br>");

            }
            responseText.append("<button type=\"submit\" value=\"Submit\">\n").append("Go to selected survey").append("</button>").append("</form>");

            return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());
        }

    }
}
