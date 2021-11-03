package no.kristiania.http;

import no.kristiania.controllers.AnswerController;
import no.kristiania.controllers.CreateQuestionController;
import no.kristiania.controllers.ListQuestionController;
import no.kristiania.controllers.SurveyController;
import no.kristiania.survey.OptionDao;
import no.kristiania.survey.QuestionDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PersonnelServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(8081);
        DataSource dataSource = createDataSource();

        QuestionDao questionDao = new QuestionDao(dataSource);
        OptionDao optionDao = new OptionDao(dataSource);


        httpServer.addController("/api/question", new ListQuestionController(questionDao));
        httpServer.addController("/api/newQuestion", new CreateQuestionController(questionDao));
        httpServer.addController("/api/option", new SurveyController(optionDao, questionDao));
        httpServer.addController("/api/answers", new AnswerController(questionDao, "/index.html"));

        logger.info("Started http://localhost:{}/", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("src/main/resources/conf/pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url", "jdbc:postgresql://localhost:5432/survey_db"));
        dataSource.setUser(properties.getProperty("dataSource.username", "survey_dbuser"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }
}
