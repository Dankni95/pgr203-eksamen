package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.dao.UserDao;
import no.kristiania.entity.Survey;
import no.kristiania.entity.User;

import java.sql.SQLException;
import java.util.Map;

public class CreateNewSurveyTest {
    private final UserDao userDao;
    private final SurveyDao surveyDao;
    Map<String, String> parameters;

    public CreateNewSurveyTest(UserDao userDao, SurveyDao surveyDao, Map<String, String> parameters) {
        this.parameters = parameters;
        this.surveyDao = surveyDao;
        this.userDao = userDao;
    }

}
