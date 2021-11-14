package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpGetClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteEditQuestionControllerTest {
    private final HttpServer server = new HttpServer(0);

    public WriteEditQuestionControllerTest() throws IOException {
    }

    @Test
    void ShouldListAllQuestions() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        Survey survey = new Survey();
        survey.setUserId(1); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);

        Question question = new Question();
        question.setTitle("Test question name");
        question.setText("Test subtitle name");
        question.setUserId(1);
        question.setSurveyId(survey.getId());
        questionDao.save(question);

        Question secondQuestion = new Question();
        secondQuestion.setTitle("Second test question name");
        secondQuestion.setText("Second test subtitle name");
        secondQuestion.setUserId(1);
        secondQuestion.setSurveyId(survey.getId());

        questionDao.save(secondQuestion);

        Option option = new Option();
        option.setTitle("Option 1");
        option.setQuestionId(question.getId());
        optionDao.save(option);

        Option secondOption = new Option();
        secondOption.setTitle("Option 1");
        secondOption.setQuestionId(secondQuestion.getId());

        optionDao.save(secondOption);

        server.addController("GET /api/answers", new WriteQuestionsForAnswerController(questionDao));

        HttpGetClient httpGetClient = new HttpGetClient(
                "localhost",
                server.getPort(),
                "/api/answers"
        );
        assertEquals(200, httpGetClient.getStatusCode());

        assertEquals(httpGetClient.getMessageBody(), "<form action=\"api/answers\" method=\"POST\"><label value=\"select\">Choose question to see answers</label><select name=\"questionId\"><option value=\"1\">Test question name</option><option value=\"2\">Second test question name</option></select><button>Go to selected question</button>");

        surveyDao.deleteAll();
        questionDao.deleteAll();
        optionDao.deleteAll();
    }
}


