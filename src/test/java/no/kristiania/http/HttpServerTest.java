package no.kristiania.http;


import no.kristiania.controllers.OptionsController;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.dao.*;
import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HttpServerTest {

    private final HttpServer server = new HttpServer(0);

    HttpServerTest() throws IOException {
    }

    @Test
    void shouldReturn404ForUnknownRequestTarget() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }
    
    @Test
    void shouldRespondWithRequestTargetIn404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals("File not found: /non-existing", client.getMessageBody());
    }
    
    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
    }
    
    @Test
    void shouldServeFiles() throws IOException {
        String fileContent = "A file created at " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/example-file.txt"), fileContent.getBytes());
        
        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldUseFileExtensionForContentType() throws IOException {
        String fileContent = "<p>Hello</p>";
        Files.write(Paths.get("target/test-classes/example-file.html"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }

    @Test
    void shouldReturnOptionsFromServer() throws IOException, SQLException {
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

        Option option = new Option();
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



        server.addController("/api/option", new OptionsController(optionDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/option");
        assertEquals(
                "<option value=1>Option 1</option><option value=2>Option 2</option><option value=3>Option 3</option><option value=4>Option 4</option><option value=5>Option 5</option>",
                client.getMessageBody()
        );
    }
}