package no.kristiania.dao;

import no.kristiania.entity.Answer;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;


class AnswerDaoTest {
    private final AnswerDao answerDao = new AnswerDao(TestData.testDataSource());
    private static Survey survey;
    private static Question question;
    private static Option option;

    public static Answer exampleAnswer() throws SQLException {
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

        optionDao.save(option);

        Answer answer = new Answer();
        answer.setUserId(survey.getUserId()); //Annon
        answer.setQuestionId(question.getId());
        answer.setOptionId(option.getId());

        return answer;
    }

    @Test
    void shouldRetrieveSavedAnswer() throws SQLException {
        Answer answer = exampleAnswer();
        answerDao.save(answer);
        assertThat(answerDao.retrieve(answer.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answer);
    }

    @Test
    void shouldListAllAnswers() throws SQLException {
        Answer answer = exampleAnswer();
        answerDao.save(answer);
        Answer anotherAnswer = exampleAnswer();
        answerDao.save(anotherAnswer);

        assertThat(answerDao.listAll())
                .extracting(Answer::getId)
                .contains(answer.getId(), anotherAnswer.getId());
    }

    @Test
    void shouldDeleteAllAnswers() throws SQLException{
        Answer answer = exampleAnswer();
        answerDao.save(answer);
        answerDao.deleteAll();

        assertNull(answerDao.retrieve(answer.getId()));
    }
}