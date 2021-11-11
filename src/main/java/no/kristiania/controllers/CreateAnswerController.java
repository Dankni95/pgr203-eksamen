package no.kristiania.controllers;

import no.kristiania.dao.AnswerDao;
import no.kristiania.entity.Answer;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.dao.QuestionDao;
import no.kristiania.utils.Cookie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class CreateAnswerController implements HttpController {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    public CreateAnswerController(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws IOException, SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);

        User user;
        if ((user = Cookie.getUser(request.headerFields.get("Cookie").split("=")[1])) != null) System.out.println(user.getFirstName());

        System.out.println("THIS: "+parameters);
        Answer answer = new Answer();

        //Selected user
        /*Check if the selected user exists and set this id to be the id in answers table
         * If not, then choos default user*/


        //Selected survey
        /*Set the selected surveys id to be the survey id in answers table*/


        //Find question
        /*Find the id of the answered question and set it to be the id of question in answers table*/

        //Find selected option
        /*Find the id of chosen option and set it to be option id in answers table*/


        answer.setUserSurveyId(Integer.parseInt(parameters.get("surveyId")));
        answer.setQuestionId(Integer.parseInt(parameters.get("questionId")));


        answer.setOptionTitle(parameters.get("optionTitle"));
        answerDao.save(answer);



        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/survey.html?" + parameters.get("survey"));

    }
}
