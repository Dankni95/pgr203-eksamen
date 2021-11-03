package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.survey.Option;
import no.kristiania.survey.OptionDao;
import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.sql.SQLException;

public class SurveyController implements HttpController {
    private final OptionDao optionDao;
    private final QuestionDao questionDao;

    public SurveyController(OptionDao optionDao, QuestionDao questionDao) {
        this.optionDao = optionDao;
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        StringBuilder responseText = new StringBuilder();
        responseText.append("<form action=\"/api/answers\" method=\"POST\">");
        int value = 0;
        for (int i = 1; i < questionDao.listAll().size(); i++) {
            Question question = questionDao.retrieve(i);
            responseText.append("<div class='form-control'>")
                    .append("<h2>")
                    .append(question.getTitle())
                    .append("</h2>")
                    .append("<h4>")
                    .append(question.getText())
                    .append("</h4>");

            for (Option op : optionDao.listOptionsByQuestionId(i)) {
                responseText
                        .append("<label for=\"").append(question.getTitle()).append("\">")
                        .append("<input type=\"radio\" name=\"").append(question.getTitle()).append("\"")
                        .append(" value=\"").append(op.getTitle()).append("\">")
                        //add checked
                        .append(op.getTitle()).append("</input>");
            }
            responseText.append("<br><hr>").append("</div>");
        }
        responseText.append("<br>")
                .append("<button type=\"submit\" value=\"Submit\">\n").append("Submit").append("</button>").append("</form>");
        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());

    }
}


