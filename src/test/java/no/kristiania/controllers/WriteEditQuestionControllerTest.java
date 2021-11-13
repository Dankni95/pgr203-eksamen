package no.kristiania.controllers;

import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;
import java.util.Map;

public class WriteEditQuestionControllerTest {

    private final QuestionDao questionDao;
    Map<String, String> parameters;

    public WriteEditQuestionControllerTest(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

}


