package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.dao.UserDao;
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
    private final UserDao userDao;
    private final SurveyDao surveyDao;

    public GetSurveyController(OptionDao optionDao, QuestionDao questionDao, UserDao userDao, SurveyDao surveyDao) {
        this.optionDao = optionDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.surveyDao = surveyDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {

        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.getHeader("Referer"));
        StringBuilder responseText = new StringBuilder();

        // check for null


        responseText.append("<h2>").append(parameters.get("title")).append("</h2>")
                .append("<p style=\"text-align: center;\">").append("Survey created by ").append(userDao.retrieve(surveyDao.retrieve(Integer.parseInt(parameters.get("id"))).getUserId()).getFirstName()).append("</p>");

        responseText.append("<form action=\"/api/questions\" method=\"POST\">");

        for (int i = 1; i <= questionDao.listAll().size(); i++) {
            Question question = questionDao.retrieve(i);

            if (question.getSurveyId() == Integer.parseInt(parameters.get("id").trim())) {

                responseText.append("<div class='form-control'>")
                        .append("<p style>").append("Question created by ").append(userDao.retrieve(question.getUserId()).getFirstName()).append("</p>")
                        .append("<h2>")
                        .append(question.getTitle())
                        .append("</h2>")
                        .append("<h4>")
                        .append(question.getText())
                        .append("</h4>");


                for (Option op : optionDao.listOptionsByQuestionId(i)) {
                    //add checked
                    responseText
                            .append("<label for=\"").append(question.getTitle()).append("\">")
                            .append("<input type=\"radio\" name=\"").append(question.getTitle()).append("\"")
                            .append(" value=\"").append(op.getTitle()).append("\">")
                            //add checked
                            .append(op.getTitle()).append("</input>")
                            .append("<input type=\"hidden\" name=\"").append("survey").append("\"")
                            .append(" value=\"").append(parameters.get("title")).append("=").append(parameters.get("id")).append("\">")
                            .append("</input>");

                }
                responseText.append("<br><hr>").append("</div>");
            }
        }
        responseText.append("<br>")
                .append("<button type=\"submit\" value=\"Submit\">\n").append("Submit").append("</button>").append("</form>");
        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());

    }
}