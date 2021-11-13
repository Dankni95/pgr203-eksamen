package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class writeAllSurveysControllerTest {
    private final SurveyDao surveyDao;

    public writeAllSurveysControllerTest(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }
}
