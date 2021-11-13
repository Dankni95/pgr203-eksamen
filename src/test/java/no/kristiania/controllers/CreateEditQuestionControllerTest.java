package no.kristiania.controllers;

import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;
import java.util.Map;

public class CreateEditQuestionControllerTest {
    private final QuestionDao questionDao;

    public CreateEditQuestionControllerTest(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

}
