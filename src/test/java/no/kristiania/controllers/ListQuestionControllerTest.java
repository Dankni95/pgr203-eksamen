package no.kristiania.controllers;


import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListQuestionControllerTest {
    private final QuestionDao questionDao;
    private final ListQuestionController listQuestionController;

    public ListQuestionControllerTest(QuestionDao questionDao, ListQuestionController listQuestionController) {
        this.questionDao = questionDao;
        this.listQuestionController = listQuestionController;
    }


}
