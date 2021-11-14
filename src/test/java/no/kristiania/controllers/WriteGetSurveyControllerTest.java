package no.kristiania.controllers;

import no.kristiania.dao.*;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import no.kristiania.entity.User;
import no.kristiania.http.HttpGetClient;
import no.kristiania.http.HttpServer;
import org.checkerframework.checker.units.qual.A;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteGetSurveyControllerTest {
    private final HttpServer server = new HttpServer(0);
    QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    OptionDao optionDao = new OptionDao(TestData.testDataSource());
    UserDao userDao = new UserDao(TestData.testDataSource());
    SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
    AnswerDao answerDao = new AnswerDao(TestData.testDataSource());


    public WriteGetSurveyControllerTest() throws IOException {
    }


    @Test
    void ShouldGetSpecificSurvey() throws IOException, SQLException {

        User annon = new User();
        annon.setFirstName("Annon");
        annon.setLastName("Annon");
        annon.setEmail("Annon@annon.no");
        userDao.save(annon);

        Survey survey = new Survey();
        survey.setUserId(annon.getId()); // Annon
        survey.setTitle("Test survey name");
        surveyDao.save(survey);


        Question question = new Question();
        question.setTitle("Test question name");
        question.setText("Test subtitle name");
        question.setUserId(annon.getId());
        question.setSurveyId(survey.getId());

        questionDao.save(question);

        System.out.println(question.getId());

        Option option = new Option();
        option.setTitle("Option 1");
        option.setQuestionId(question.getId());

        Option option2 = new Option();
        option2.setTitle("Option 2");
        option2.setQuestionId(question.getId());

        Option option3 = new Option();
        option3.setTitle("Option 3");
        option3.setQuestionId(question.getId());


        optionDao.save(option);
        optionDao.save(option2);
        optionDao.save(option3);


        server.addController("GET /api/questions", new WriteGetSurveyController(optionDao, questionDao, userDao, surveyDao, answerDao));


        HttpGetClient httpGetClient = new HttpGetClient(
                "localhost",
                server.getPort(),
                "/api/questions"
        );
        assertEquals(200, httpGetClient.getStatusCode());

        assertEquals(httpGetClient.getMessageBody(), "<h2>Test survey name</h2><p style=\"text-align: center;\">Survey created by Annon</p><form action=\"/api/questions\" method=\"POST\"><div class='form-control' for='Test question name'<p style>Question created by Annon</p><h2>Test question name</h2><h4>Test subtitle name</h4><div for='Test question name' id='Test question name'><label for=\"Test question name\"><input type=\"radio\" name=\"Test question name\" value=\"Option 1=Test question name\">Option 1</input><input type=\"hidden\" name=\"survey\" value=\"Test survey name=1\"></input></div><div for='Test question name' id='Test question name'><label for=\"Test question name\"><input type=\"radio\" name=\"Test question name\" value=\"Option 2=Test question name\">Option 2</input><input type=\"hidden\" name=\"survey\" value=\"Test survey name=1\"></input></div><div for='Test question name' id='Test question name'><label for=\"Test question name\"><input type=\"radio\" name=\"Test question name\" value=\"Option 3=Test question name\">Option 3</input><input type=\"hidden\" name=\"survey\" value=\"Test survey name=1\"></input></div><br><hr></div><br><button type=\"submit\" value=\"Submit\">\n" +
                "Submit</button></form>");
    }

}