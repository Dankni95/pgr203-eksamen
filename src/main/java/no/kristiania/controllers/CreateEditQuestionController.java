package no.kristiania.controllers;

import no.kristiania.dao.AnswerDao;
import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;


import java.sql.SQLException;
import java.util.Map;

public class CreateEditQuestionController implements HttpController {
    private final QuestionDao questionDao;

    public CreateEditQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);

        System.out.println(parameters);

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/survey.html?" + parameters.get("survey"));

    }
}
