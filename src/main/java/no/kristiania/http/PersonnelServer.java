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


        httpServer.addController("POST /api/new-survey", new CreateSurveyController(questionDao, optionDao, userDao, surveyDao));
        httpServer.addController("POST /api/questions", new CreateAnswerController(questionDao, answerDao, optionDao));
        httpServer.addController("POST /api/edit", new CreateEditQuestionController(questionDao));
        httpServer.addController("POST /api/answers", new ListAnswersByQuestionController(questionDao, userDao, optionDao, answerDao));
        httpServer.addController("POST /api/user", new CreateUserController(userDao));


        httpServer.addController("GET /api/questions", new WriteGetSurveyController(optionDao, questionDao, userDao, surveyDao, answerDao));
        httpServer.addController("GET /api/edit", new WriteEditQuestionController(questionDao));
        httpServer.addController("GET /api/surveys", new WriteAllSurveysController(surveyDao));
        httpServer.addController("GET /api/all-surveys", new WriteAllSurveysAsOptionsController(surveyDao));
        httpServer.addController("GET /api/answers", new WriteQuestionsForAnswerController(questionDao));
        httpServer.addController("GET /api/user", new WriteUserController());


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
