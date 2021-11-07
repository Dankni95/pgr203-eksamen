package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.entity.Option;
import no.kristiania.dao.OptionDao;
import no.kristiania.entity.Question;
import no.kristiania.dao.QuestionDao;

import java.sql.SQLException;
import java.util.Map;

public class GetSurveyController implements HttpController {
    private final OptionDao optionDao;
    private final QuestionDao questionDao;

    public GetSurveyController(OptionDao optionDao, QuestionDao questionDao) {
        this.optionDao = optionDao;
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {

        Map<String, String> parameters = HttpMessage.parseGetRequestParameters(request.getHeader("Referer"));
        StringBuilder responseText = new StringBuilder();

        // check for null

        responseText.append("<h2>").append(parameters.get("title")).append("</h2>");
        responseText.append("<form action=\"/api/answers\" method=\"POST\">");

        for (int i = 1; i <= questionDao.listAll().size(); i++) {
            Question question = questionDao.retrieve(i);

            System.out.println(Integer.parseInt(parameters.get("id").trim()));
            if (question.getSurveyId() == Integer.parseInt(parameters.get("id").trim())) {

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
        }
        responseText.append("<br>")
                .append("<button type=\"submit\" value=\"Submit\">\n").append("Submit").append("</button>").append("</form>");
        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());

    }
}


