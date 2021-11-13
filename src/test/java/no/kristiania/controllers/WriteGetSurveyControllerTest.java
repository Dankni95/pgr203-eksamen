package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.utils.Cookie;

import java.sql.SQLException;
import java.util.Map;

public class WriteGetSurveyControllerTest {
    private final OptionDao optionDao;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final SurveyDao surveyDao;
    private final AnswerDao answerDao;

    public WriteGetSurveyControllerTest(OptionDao optionDao, QuestionDao questionDao, UserDao userDao, SurveyDao surveyDao, AnswerDao answerDao) {
        this.optionDao = optionDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.surveyDao = surveyDao;
        this.answerDao = answerDao;
    }
}