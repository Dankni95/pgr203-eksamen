package no.kristiania.controllers;

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

        Question question = questionDao.retrieve(Integer.parseInt(parameters.get("questionId")));
        String newTitle = parameters.get("newQuestionName");
        String newSubtitle = parameters.get("newQuestionSubtitle");

        if (newTitle.equals("")) {
            // if empty do nothing
        } else question.setTitle(newTitle);

        if (newSubtitle.equals("")) {
        } else question.setText(newSubtitle);

        questionDao.update(question);

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/survey.html?" + parameters.get("survey"));

    }
}
