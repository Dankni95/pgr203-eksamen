package no.kristiania.controllers;

import no.kristiania.dao.AnswerDao;
import no.kristiania.dao.OptionDao;
import no.kristiania.entity.Answer;
import no.kristiania.entity.Option;
import no.kristiania.entity.Question;
import no.kristiania.entity.User;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.dao.QuestionDao;
import no.kristiania.utils.Cookie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CreateAnswerController implements HttpController {
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;
    private final OptionDao optionDao;

    public CreateAnswerController(QuestionDao questionDao, AnswerDao answerDao, OptionDao optionDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.optionDao = optionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws IOException, SQLException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        Answer answer = new Answer();

        User user;
        if (request.headerFields.get("Cookie") != null) {
            if ((user = Cookie.getUser(request.headerFields.get("Cookie").split("=")[1].split(";")[0])) != null) {
                answer.setUserId(user.getId());
            }else {
                answer.setUserId(1);
            }
        } else {
            answer.setUserId(1);
        }


        List<Question> question = questionDao.listAll();
        for (Question q : question) {
            String parameter;
            if ((parameter = parameters.get(q.getTitle("question title"))) != null) {
                List<Option> options;
                if ((options = optionDao.listOptionsByQuestionId(q.getId())) != null) {
                    for (Option op : options) {
                        if (op.getTitle().equals(parameter.split("=")[0])) {
                            answer.setQuestionId(q.getId());
                            answer.setOptionId(op.getId());
                            answerDao.save(answer);
                        }
                    }
                }
            }
        }


        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/survey.html?" + parameters.get("survey"));

    }
}
