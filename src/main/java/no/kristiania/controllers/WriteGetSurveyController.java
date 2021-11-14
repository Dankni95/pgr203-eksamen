package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;

import java.sql.SQLException;
import java.util.Map;

public class WriteGetSurveyController implements HttpController {
    private final OptionDao optionDao;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final SurveyDao surveyDao;


    public WriteGetSurveyController(OptionDao optionDao, QuestionDao questionDao, UserDao userDao, SurveyDao surveyDao,AnswerDao answerDao) {
        this.optionDao = optionDao;
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.surveyDao = surveyDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String responseText = "";

        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.getHeader("Referer"));

        User surveyCreator;
        if ((surveyCreator = userDao.retrieve(surveyDao.retrieve(Integer.parseInt(parameters.get("id"))).getUserId())) != null){

        }else {
            surveyCreator = new User();
            surveyCreator.setFirstName("Annon");
        }

        responseText += "<h2>" + parameters.get("title") + "</h2>"
                + "<p style=\"text-align: center;\">" + "Survey created by " + surveyCreator.getFirstName() + "</p>"
                + "<form action=\"/api/questions\" method=\"POST\">";

        for (int i = 1; i <= questionDao.listAll().size(); i++) {
            Question question = questionDao.retrieve(i);

            if (question.getSurveyId() == Integer.parseInt(parameters.get("id").trim())) {

                responseText += "<div class='form-control' for=" + "'" + question.getTitle("question title") + "'"
                        + "<p style>" + "Question created by " + userDao.retrieve(question.getUserId()).getFirstName() + "</p>"
                        + "<h2>"
                        + question.getTitle("question title")
                        + "</h2>"
                        + "<h4>"
                        + question.getText()
                        + "</h4>";


                for (Option op : optionDao.listOptionsByQuestionId(i)) {

                    responseText += "<div for='" + question.getTitle("question title") + "' id='" + question.getTitle("question title") + "'" + ">" +

                            "<label for=\"" + question.getTitle("question title") + "\">"
                            + "<input type=\"radio\" name=\"" + question.getTitle("question title") + "\""
                            + " value=\"" + op.getTitle() + "=" + question.getTitle("question title") + "\">"
                            + op.getTitle() + "</input>"


                            + "<input type=\"hidden\" name=\"" + "survey" + "\""
                            + " value=\"" + parameters.get("title") + "=" + parameters.get("id") + "\">"
                            + "</input>";


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