package no.kristiania.http;


import no.kristiania.controllers.CreateSurveyController;
import no.kristiania.controllers.OptionsController;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.dao.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
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
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        Option option = new Option();
        Option option2 = new Option();
        Question question = new Question();
        Question question2 = new Question();

        option.setTitle("Teacher");
        option.setQuestionId(1);
        option2.setTitle("Student");
        option2.setQuestionId(1);


        optionDao.save(option);
        optionDao.save(option2);

        server.addController("/api/option", new OptionsController(optionDao));
        
        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/option");
        assertEquals(
                "<option value=1>Teacher</option><option value=2>Student</option>",
                client.getMessageBody()
        );
    }


    @Test
    void shouldCreateNewQuestion() throws IOException, SQLException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        OptionDao optionDao = new OptionDao(TestData.testDataSource());
        UserDao userDao = new UserDao(TestData.testDataSource());
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

        server.addController("/api/newQuestion", new CreateSurveyController(questionDao,optionDao,userDao,surveyDao));
        
        
        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/newQuestion",
                "text=Persson&title=Test&option_1=option"
        );
        assertEquals(303, postClient.getStatusCode());
        
        assertThat(questionDao.listAll())
                .anySatisfy(p -> {
                    assertThat(p.getTitle()).isEqualTo("Test");
                    assertThat(p.getText()).isEqualTo("Persson");
                });

        assertThat(optionDao.listAll())
                .anySatisfy(o -> {
                    assertThat(o.getTitle()).isEqualTo("option");
                    assertThat(o.getQuestionId()).isEqualTo(1);
                });

        optionDao.deleteAll();
        questionDao.deleteAll();
    }
}