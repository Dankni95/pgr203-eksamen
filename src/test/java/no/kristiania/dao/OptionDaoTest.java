package no.kristiania.dao;

import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OptionDaoTest {
    private final QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    private final OptionDao optionDao = new OptionDao(TestData.testDataSource());
    private final SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
    private static Option option;
    private static Survey survey;
    private static Question question;



    public static Option exampleQuestion() throws SQLException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);

        question = new Question();
        question.setTitle("Test question name");
        question.setText("Test subtitle name");
        question.setUserId(1);
        question.setSurveyId(survey.getId());

        questionDao.save(question);

        option = new Option();
        option.setTitle("Option 1");
        option.setQuestionId(question.getId());

        Option option2 = new Option();
        option2.setTitle("Option 2");
        option2.setQuestionId(question.getId());

        Option option3 = new Option();
        option3.setTitle("Option 3");
        option3.setQuestionId(question.getId());

        Option option4 = new Option();
        option4.setTitle("Option 4");
        option4.setQuestionId(question.getId());

        Option option5 = new Option();
        option5.setTitle("Option 5");
        option5.setQuestionId(question.getId());


        optionDao.save(option);
        optionDao.save(option2);
        optionDao.save(option3);
        optionDao.save(option4);
        optionDao.save(option5);

        return option;
    }

    @Test
    void shouldRetrieveSavedOption() throws SQLException {
        Option option = exampleQuestion();
        optionDao.save(option);
        assertThat(optionDao.retrieve(option.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(option);
    }

    @Test
    void shouldListAllOptions() throws SQLException {
        Option option = exampleQuestion();
        optionDao.save(option);
        Option anotherOption = exampleQuestion();
        optionDao.save(anotherOption);

        assertThat(optionDao.listAll())
                .extracting(Option::getId)
                .contains(option.getId(), anotherOption.getId());
    }

    @Test
    void shouldDeleteOptions() throws SQLException {
        Option option = exampleQuestion();
        optionDao.save(option);
        optionDao.deleteAll();

        assertNull(optionDao.retrieve(option.getId()));
    }

    @Test
    void shouldListOptionsByQuestionId() throws SQLException {
        optionDao.deleteAll();

        survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);

        question = new Question();
        question.setTitle("Test question name");
        question.setText("Test subtitle name");
        question.setUserId(1);
        question.setSurveyId(survey.getId());

        questionDao.save(question);

        option = new Option();
        option.setTitle("Option 1");
        option.setQuestionId(question.getId());

        optionDao.save(option);

        assertThat(optionDao.listOptionsByQuestionId(1))
                .extracting(Option::getQuestionId)
                .contains(option.getQuestionId());
    }
}