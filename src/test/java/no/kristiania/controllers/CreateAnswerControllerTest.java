package no.kristiania.controllers;

import no.kristiania.dao.AnswerDao;
import no.kristiania.dao.OptionDao;
import no.kristiania.dao.QuestionDao;

public class CreateAnswerControllerTest {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;
    private final OptionDao optionDao;

    public CreateAnswerControllerTest(QuestionDao questionDao, AnswerDao answerDao, OptionDao optionDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.optionDao = optionDao;
    }
}
