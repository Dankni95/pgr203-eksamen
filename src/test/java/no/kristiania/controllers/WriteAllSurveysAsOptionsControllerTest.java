package no.kristiania.controllers;


import no.kristiania.dao.SurveyDao;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public class WriteAllSurveysAsOptionsControllerTest {
    private final SurveyDao surveyDao;

    public WriteAllSurveysAsOptionsControllerTest(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }
}
