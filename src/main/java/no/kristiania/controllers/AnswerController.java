package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.dao.QuestionDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AnswerController implements HttpController {
    private final QuestionDao questionDao;

    public AnswerController(QuestionDao questionDao) {
        this.questionDao = questionDao;

    }

    @Override
    public HttpMessage handle(HttpMessage request) throws IOException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);


        // Not implemented yet!
        System.out.println(parameters);


        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/survey.html?" + parameters.get("survey"));

    }
}
