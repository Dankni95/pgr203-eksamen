package no.kristiania.dao;

import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoTest {
    private final QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    private static Survey survey;
    private static Question question;
    private static Option option;
    private static Option option2;
    private static Option option3;
    private static Option option4;
    private static Option option5;

    public static Question exampleQuestion() throws SQLException {
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

        option2 = new Option();
        option2.setTitle("Option 2");
        option2.setQuestionId(question.getId());

        option3 = new Option();
        option3.setTitle("Option 3");
        option3.setQuestionId(question.getId());

        option4 = new Option();
        option4.setTitle("Option 4");
        option4.setQuestionId(question.getId());

        option5 = new Option();
        option5.setTitle("Option 5");
        option5.setQuestionId(question.getId());

        optionDao.save(option);
        optionDao.save(option2);
        optionDao.save(option3);
        optionDao.save(option4);
        optionDao.save(option5);

        return question;
    }

    @Test
    void shouldRetrieveSavedQuestion() throws SQLException {
        Question question = exampleQuestion();

        assertThat(questionDao.retrieve(question.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(question);
    }
    
    @Test
    void shouldListAllQuestions() throws SQLException {
        Question question = exampleQuestion();
        Question anotherQuestion = exampleQuestion();

        assertThat(questionDao.listAll())
                .extracting(Question::getId)
                .contains(question.getId(), anotherQuestion.getId());
    }
}