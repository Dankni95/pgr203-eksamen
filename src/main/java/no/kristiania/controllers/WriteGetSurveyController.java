package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.utils.Cookie;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class WriteGetSurveyController implements HttpController {
    private final OptionDao optionDao;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final SurveyDao surveyDao;
    private final AnswerDao answerDao;

    public WriteGetSurveyController(OptionDao optionDao, QuestionDao questionDao, UserDao userDao, SurveyDao surveyDao, AnswerDao answerDao) {
        this.optionDao = optionDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.surveyDao = surveyDao;
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {

        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.getHeader("Referer"));
        Map<String, String> cookie = HttpMessage.parseRequestParameters(request.getHeader("Cookie"));
        String responseText = "";

        User user = Cookie.getUser(cookie.get("user").split(";")[0]);

        responseText += "<h2>" + parameters.get("title") + "</h2>"
                + "<p style=\"text-align: center;\">" + "Survey created by " + userDao.retrieve(surveyDao.retrieve(Integer.parseInt(parameters.get("id"))).getUserId()).getFirstName() + "</p>"
                + "<form action=\"/api/questions\" method=\"POST\">";

        for (int i = 1; i <= questionDao.listAll().size(); i++) {
            Question question = questionDao.retrieve(i);

            if (question.getSurveyId() == Integer.parseInt(parameters.get("id").trim())) {

                responseText += "<div class='form-control' for=" + "'" + question.getTitle() + "'"
                        + "<p style>" + "Question created by " + userDao.retrieve(question.getUserId()).getFirstName() + "</p>"
                        + "<h2>"
                        + question.getTitle()
                        + "</h2>"
                        + "<h4>"
                        + question.getText()
                        + "</h4>";


                for (Option op : optionDao.listOptionsByQuestionId(i)) {

                    responseText += "<div for='" + question.getTitle() + "' id='" + question.getTitle() + "'" + ">" +

                            "<label for=\"" + question.getTitle() + "\">"
                            + "<input type=\"radio\" name=\"" + question.getTitle() + "\""
                            + " value=\"" + op.getTitle() + "=" + question.getTitle() + "\">"
                            + op.getTitle() + "</input>"


                            + "<input type=\"hidden\" name=\"" + "survey" + "\""
                            + " value=\"" + parameters.get("title") + "=" + parameters.get("id") + "\">"
                            + "</input>";

                    if (user != null) {
                        responseText += "<label for=\"" + "userId" + "\">"
                                + "<input type=\"hidden\" name=\"" + "UserId" + "\""
                                + " value=\"" + user.getId() + "\">";
                    } else {
                        responseText += "<label for=\"" + "userId" + "\">"
                                + "<input type=\"hidden\" name=\"" + "UserId" + "\""
                                + " value=\"" + "1" + "\">";
                    }


                    responseText += "</div>";
                }
                responseText += "<br><hr>" + "</div>";
            }
        }
        responseText += "<br>"
                + "<button type=\"submit\" value=\"Submit\">\n" + "Submit" + "</button>" + "</form>";

        return new HttpMessage("HTTP/1.1 200 OK", responseText);

    }
}