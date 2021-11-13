package no.kristiania.controllers;


import no.kristiania.dao.AnswerDao;
import no.kristiania.dao.OptionDao;
import no.kristiania.dao.QuestionDao;
import no.kristiania.dao.UserDao;
import no.kristiania.entity.Answer;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ListAnswersByQuestionControllerTest {
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final OptionDao optionDao;
    private final AnswerDao answerDao;

    public ListAnswersByQuestionControllerTest(QuestionDao questionDao, UserDao userDao, OptionDao optionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.optionDao = optionDao;
        this.answerDao = answerDao;
    }

}
