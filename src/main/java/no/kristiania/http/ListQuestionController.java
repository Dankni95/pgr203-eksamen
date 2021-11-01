package no.kristiania.http;


import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

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
            messageBody += "<div>" + question.getText() + ", " + question.getTitle() + "</div>";
        }


        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
