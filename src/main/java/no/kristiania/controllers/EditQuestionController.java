package no.kristiania.controllers;

import no.kristiania.dao.AnswerDao;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;


import java.sql.SQLException;
import java.util.Map;

public class EditQuestionController implements HttpController {
    private final AnswerDao answerDao;

    public EditQuestionController(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);

        System.out.println(parameters);
        System.out.println("Save to question in table here");

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/survey.html?" + parameters.get("survey"));

    }
}
