package no.kristiania.controllers;


import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.entity.Question;
import no.kristiania.dao.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListQuestionController implements HttpController {
    private final QuestionDao questionDao;

    public ListQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";

        for (Question question : questionDao.listAll()) {
            messageBody += "<h1>" + question.getText() + ", " + question.getTitle("question title") + "</h1>";
        }


        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
