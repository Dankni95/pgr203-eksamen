package no.kristiania.controllers;

import no.kristiania.dao.OptionDao;
import no.kristiania.dao.QuestionDao;
import no.kristiania.dao.SurveyDao;
import no.kristiania.dao.UserDao;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;
import java.util.Map;

public class CreateSurveyControllerTest {
    private final QuestionDao questionDao;
    private final OptionDao optionDao;
    private final UserDao userDao;
    private final SurveyDao surveyDao;

    public CreateSurveyControllerTest(QuestionDao questionDao, OptionDao optionDao, UserDao userDao, SurveyDao surveyDao) {
        this.questionDao = questionDao;
        this.optionDao = optionDao;
        this.userDao = userDao;
        this.surveyDao = surveyDao;
    }
}
