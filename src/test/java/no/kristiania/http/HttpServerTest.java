package no.kristiania.http;

import no.kristiania.controllers.CreateQuestionController;
import no.kristiania.controllers.ListQuestionController;
import no.kristiania.controllers.OptionsController;
import no.kristiania.survey.*;
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

        option.setTitle("Teacher");
        option2.setTitle("Student");

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
        server.addController("/api/newQuestion", new CreateQuestionController(questionDao));
        
        
        HttpPostClient postClient = new HttpPostClient(
                "localhost",
                server.getPort(),
                "/api/newQuestion",
                "text=Persson&title=Test"
        );
        assertEquals(200, postClient.getStatusCode());
        
        assertThat(questionDao.listAll())
                .anySatisfy(p -> {
                    assertThat(p.getTitle()).isEqualTo("Test");
                    assertThat(p.getText()).isEqualTo("Persson");
                });
        questionDao.deleteAll();
    }

    @Test
    void shouldListQuestions() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());

        server.addController("/api/question", new ListQuestionController(questionDao));
        
        Question question1 = QuestionDaoTest.exampleQuestion();
        Question question2 = QuestionDaoTest.exampleQuestion();
        questionDao.save(question1);
        questionDao.save(question2);


        //Still fails because not decoded correctly

        
        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/question");
        assertThat(client.getMessageBody())
                .contains("<h1>" + question1.getText() + ", " + question1.getTitle() + "</h1>")
                .contains("<h1>" + question2.getText() + ", " + question2.getTitle() + "</h1>");

        questionDao.deleteAll();
    }
}