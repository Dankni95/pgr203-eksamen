package no.kristiania.http;

import no.kristiania.controllers.*;
import no.kristiania.dao.*;
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
        SurveyDao surveyDao = new SurveyDao(dataSource);
        UserDao userDao = new UserDao(dataSource);
        AnswerDao answerDao = new AnswerDao(dataSource);


        httpServer.addController("/error", new ErrorController());


        httpServer.addController("/api/question", new ListQuestionController(questionDao));
        httpServer.addController("/api/newQuestion", new CreateSurveyController(questionDao, optionDao, userDao, surveyDao));
        httpServer.addController("GET /api/questions", new GetSurveyController(optionDao, questionDao, userDao, surveyDao));
        httpServer.addController("POST /api/questions", new AnswerController(questionDao,answerDao));
        httpServer.addController("/api/edit", new EditQuestionController(questionDao));
        httpServer.addController("/api/surveys", new writeAllSurveysController(surveyDao));
        httpServer.addController("/api/all-surveys", new ListAllSurveysAsOptionsController(surveyDao));
        httpServer.addController("/api/get-user", new writeUserController());
        httpServer.addController("/api/user", new CreateUserController(userDao));


        logger.info("Started http://localhost:{}/", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
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
