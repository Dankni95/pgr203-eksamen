package no.kristiania.dao;

import no.kristiania.entity.Survey;
import no.kristiania.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {
    private static Survey survey;
    private static User user;
    private final SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
    private final UserDao userDao = new UserDao(TestData.testDataSource());

    public static Survey exampleSurveyWithUser() throws SQLException {
        UserDao userDao = new UserDao(TestData.testDataSource());

        user = new User();
        user.setFirstName("Test name");
        user.setLastName("Test lastname");
        user.setEmail("test@email.no");
        userDao.save(user);


        survey = new Survey();
        survey.setUserId(user.getId());
        survey.setTitle("Test survey name");

        return survey;
    }

    @Test
    void shouldRetrieveSavedSurveyWithUser() throws SQLException {
        Survey survey = exampleSurveyWithUser();
        surveyDao.save(survey);
        assertThat(surveyDao.retrieve(survey.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(survey);
    }

    @Test
    void shouldListAllSurveysWithUser() throws SQLException {
        Survey survey = exampleSurveyWithUser();
        surveyDao.save(survey);
        Survey anotherSurvey = exampleSurveyWithUser();
        surveyDao.save(anotherSurvey);

        assertThat(surveyDao.listAll())
                .extracting(Survey::getUserId)
                .contains(survey.getUserId(), anotherSurvey.getUserId());
    }
}