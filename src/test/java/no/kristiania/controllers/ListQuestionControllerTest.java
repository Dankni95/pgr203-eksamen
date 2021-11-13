package no.kristiania.controllers;


import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.io.IOException;
import java.sql.SQLException;

public class ListQuestionControllerTest {
    private final QuestionDao questionDao;

    public ListQuestionControllerTest(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
