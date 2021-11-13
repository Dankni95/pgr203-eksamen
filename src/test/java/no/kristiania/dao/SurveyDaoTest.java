package no.kristiania.dao;

import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class SurveyDaoTest {
    private final SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
    private static Survey survey;



    public static Survey exampleSurvey() throws SQLException {

        survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");

        return survey;
    }

    @Test
    void shouldRetrieveSavedSurvey() throws SQLException {
        Survey survey = exampleSurvey();
        surveyDao.save(survey);
        assertThat(surveyDao.retrieve(survey.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(survey);
    }
    
    @Test
    void shouldListAllSurveys() throws SQLException {
        Survey survey = exampleSurvey();
        surveyDao.save(survey);
        Survey anotherSurvey = exampleSurvey();
        surveyDao.save(anotherSurvey);

        assertThat(surveyDao.listAll())
                .extracting(Survey::getId)
                .contains(survey.getId(), anotherSurvey.getId());
    }
}