package no.kristiania.controllers;

import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class WriteQuestionsForAnswerController implements HttpController {
    private final QuestionDao questionDao;

    public WriteQuestionsForAnswerController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder responseText = new StringBuilder();

        writeAnswersHeader(responseText);
        writeAnswerBody(responseText);

        responseText.append("<button>").append("Go to selected question").append("</button>");

        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());

    }

    private void writeAnswersHeader(StringBuilder responseText) {

        responseText
                .append("<form action=\"api/answers\" method=\"POST\">")
                .append("<label value=\"").append("select").append("\">").append("Choose question to see answers").append("</label>")
                .append("<select name=").append("\"questionId").append("\"").append(">");


    }

    private void writeAnswerBody(StringBuilder responseText) throws SQLException {
        for (Question q : questionDao.listAll()) {

            responseText
                    .append("<option").append(" value=").append("\"").append(q.getId()).append("\"").append(">").append(q.getTitle())
                    .append("</option>");
        }
        responseText.append("</select>");
    }
}