package no.kristiania.controllers;

import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;
import java.util.Map;

public class EditQuestionController implements HttpController {

    private final QuestionDao questionDao;

    public EditQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {


        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.getHeader("Referer"));
        StringBuilder responseText = new StringBuilder();

        writeEditHeader(responseText);
        writeEditQuestions(responseText);


        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());

    }

    private void writeEditHeader(StringBuilder responseText) {

        responseText
                .append("<form action=\"/api/edit\" method=\"GET\">")
                .append("<label value=\"").append("select").append("\">").append("Choose question to edit").append("</label>")
                .append("<select name=").append("\"questionId").append("\"").append(">");

    }

    private void writeEditQuestions(StringBuilder responseText) throws SQLException {
        for (Question q : questionDao.listAll()) {
            responseText
                    .append("<option").append(" value=").append("\"").append(q.getId()).append("\"").append(">").append(q.getTitle())
                    .append("</option>");

        }
        responseText.append("</select>").append("<button type=\"submit\" value=\"Submit\">\n").append("Go to selected question").append("</button>").append("</form>");
    }
}


