package no.kristiania.controllers;


import no.kristiania.dao.AnswerDao;
import no.kristiania.dao.OptionDao;
import no.kristiania.dao.QuestionDao;
import no.kristiania.dao.UserDao;
import no.kristiania.entity.Answer;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class ListAnswersByQuestionController implements HttpController {
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final OptionDao optionDao;
    private final AnswerDao answerDao;

    public ListAnswersByQuestionController(QuestionDao questionDao, UserDao userDao, OptionDao optionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.userDao = userDao;
        this.optionDao = optionDao;
        this.answerDao = answerDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);

        String messageBody = "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Answers</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>";

        for (Option option : optionDao.listOptionsByQuestionId(Integer.parseInt(parameters.get("questionId")))) {
            for (Answer answer : answerDao.listAll()) {
                if (option.getId() == answer.getOptionId()) {
                    Question question = questionDao.retrieve(Integer.parseInt(parameters.get("questionId")));
                    User user = userDao.retrieve(answer.getUserId());
                    messageBody += "<div style='text-align: center;'><h1>" + question.getTitle() + "</h1>" +
                            "<h4>" + question.getText() + "</h4>" +
                            "<p>" + "Answer: " + option.getTitle() + "</p>" +
                            "<p>" + "Answered by: " + user.getFirstName() + " " + user.getLastName() + "</p><br>";
                }
            }
        }
        messageBody += "</div>";

        if (messageBody.endsWith("<body></div>")){
            messageBody += "<h1 style='text-align: center;'> There is no answers to this question </h1></body></html>";
        }else {
            messageBody += "</body></html>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
