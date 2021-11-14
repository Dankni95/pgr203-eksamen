package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.*;
import no.kristiania.http.HttpPostClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListAnswersControllerTest {
    private final HttpServer server = new HttpServer(0);

    public ListAnswersControllerTest() throws IOException {
    }

    @Test
    void shouldListAnswersByQuestions() throws IOException, SQLException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        UserDao userDao = new UserDao(TestData.testDataSource());
        AnswerDao answerDao = new AnswerDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        server.addController("POST /api/answers", new ListAnswersByQuestionController(questionDao, userDao, optionDao, answerDao));

        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/answers",
                "questionId=1"
        );
        assertEquals(200, postClient.getStatusCode());
        User user = new User();
        user.setFirstName("Annon");
        user.setLastName("Annon");
        user.setEmail("annon@annon.com");
        userDao.save(user);

        Survey survey = new Survey();
        survey.setUserId(user.getId());
        survey.setTitle("Survey title");
        surveyDao.save(survey);

        Question question = new Question();
        question.setTitle("question title");
        question.setText("question text");
        question.setSurveyId(survey.getId());
        question.setUserId(user.getId());
        questionDao.save(question);

        Option option = new Option();
        option.setTitle("option title");
        option.setQuestionId(question.getId());
        optionDao.save(option);

        Answer answer = new Answer();
        answer.setUserId(user.getId());
        answer.setQuestionId(question.getId());
        answer.setOptionId(option.getId());
        answerDao.save(answer);

        assertThat(answerDao.listAll())
                .anySatisfy(a -> {
                    assertThat(a.getId()).isEqualTo(answer.getId());
                    assertThat(a.getUserId()).isEqualTo(answer.getUserId());
                    assertThat(a.getQuestionId()).isEqualTo(answer.getQuestionId());
                    assertThat(a.getOptionId()).isEqualTo(answer.getOptionId());

                });
    }
}
