package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.dao.UserDao;
import no.kristiania.entity.Survey;
import no.kristiania.entity.User;

import java.sql.SQLException;
import java.util.Map;

public class CreateNewSurvey {
    private final UserDao userDao;
    private final SurveyDao surveyDao;
    Map<String, String> parameters;

    public CreateNewSurvey(UserDao userDao, SurveyDao surveyDao, Map<String, String> parameters) {
        this.parameters = parameters;
        this.surveyDao = surveyDao;
        this.userDao = userDao;
    }

    public Survey create(Map<String, String> parameters) throws SQLException {

        Survey survey = new Survey();

        for (User user : userDao.listAll()) {
            String name = user.getFirstName() + " " + user.getLastName();
            if (name.equals(parameters.get("user"))) {
                survey.setUserId(user.getId());
            } else survey.setUserId(1);
        }

        survey.setTitle(parameters.get("new-survey"));
        surveyDao.save(survey);

        return survey;
    }
}
