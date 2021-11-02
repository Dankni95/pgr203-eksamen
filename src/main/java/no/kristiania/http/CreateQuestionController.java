package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;
import no.kristiania.survey.User;

import java.sql.SQLException;
import java.util.Map;

public class CreateQuestionController implements HttpController {
    private final QuestionDao questionDao;

    public CreateQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        User user = new User();
        user.setUsername(parameters.get("firstName") + " " + parameters.get("lastName"));
        // questionDao.save(user);

        //12312312
        return new HttpMessage("HTTP/1.1 200 OK", "It is done");
    }
}
