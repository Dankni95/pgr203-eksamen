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
    private final String fileTarget;

    public AnswerController(QuestionDao questionDao, String fileTarget) {
        this.questionDao = questionDao;
        this.fileTarget = fileTarget;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws IOException {
        InputStream fileResource = getClass().getResourceAsStream(fileTarget);
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);






        // Not implemented yet!







        String responseText;
        if (fileResource != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            fileResource.transferTo(buffer);
             responseText = buffer.toString();
        }else responseText = "Answers collected";

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
