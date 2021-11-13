package no.kristiania.controllers;

import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class WriteQuestionsForAnswerControllerTest {

    private final QuestionDao questionDao;

    public WriteQuestionsForAnswerControllerTest(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

}