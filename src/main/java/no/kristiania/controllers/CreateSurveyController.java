package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.dao.UserDao;
import no.kristiania.entity.Survey;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.entity.Option;
import no.kristiania.dao.OptionDao;
import no.kristiania.entity.Question;
import no.kristiania.dao.QuestionDao;

import java.sql.SQLException;
import java.util.Map;

public class CreateSurveyController implements HttpController {
    private final QuestionDao questionDao;
    private final OptionDao optionDao;
    private final UserDao userDao;
    private final SurveyDao surveyDao;

    public CreateSurveyController(QuestionDao questionDao, OptionDao optionDao, UserDao userDao, SurveyDao surveyDao) {
        this.questionDao = questionDao;
        this.optionDao = optionDao;
        this.userDao = userDao;
        this.surveyDao = surveyDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);

        String surveyTitle;
        if (parameters.get("surveys").equals("previous")) {

            Survey survey = new CreateNewSurvey(userDao, surveyDao, parameters).create(parameters);
            createQuestionsAndOptions(parameters, survey.getTitle());

        } else {
            surveyTitle = parameters.get("surveys");
            createQuestionsAndOptions(parameters, surveyTitle);
        }

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/");
    }

    private void createQuestionsAndOptions(Map<String, String> parameters, String surveyTitle) throws SQLException {
        Question question = new Question();
        question.setTitle(parameters.get("title"));
        question.setText(parameters.get("text"));

        for (User user : userDao.listAll()) {
            String name = user.getFirstName() + " " + user.getLastName();
            if (name.equals(parameters.get("user"))) {
                question.setUserId(user.getId());
            } else question.setUserId(1);
        }

        question.setSurveyId(surveyDao.retrieveSurveyIdbyTitle(surveyTitle));
        questionDao.save(question);

        Option option = new Option();
        option.setTitle(parameters.get("option_1"));
        option.setQuestionId(question.getId());
        optionDao.save(option);

        for (int i = 2; i <= 5; i++) {
            Option options = new Option();
            String optionCount = "option_" + i;
            if (parameters.get(optionCount) != null) {
                options.setTitle(parameters.get(optionCount));
                options.setQuestionId(question.getId());
                optionDao.save(options);
            }
        }
    }
}
