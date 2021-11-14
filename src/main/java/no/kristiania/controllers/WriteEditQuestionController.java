package no.kristiania.controllers;

import no.kristiania.dao.QuestionDao;
import no.kristiania.entity.Question;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;
import java.util.Map;

public class WriteEditQuestionController implements HttpController {

    private final QuestionDao questionDao;
    Map<String, String> parameters;

    public WriteEditQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {


        parameters = HttpMessage.parseRequestParameters(request.getHeader("Referer"));
        StringBuilder responseText = new StringBuilder();

        writeEditHeader(responseText);
        writeEditQuestions(responseText);


        return new HttpMessage("HTTP/1.1 200 OK", responseText.toString());

    }

    private void writeEditHeader(StringBuilder responseText) {

        responseText
                .append("<form action=\"/api/edit\" method=\"POST\">")
                .append("<label value=\"").append("select").append("\">").append("Choose question to edit").append("</label>")
                .append("<select name=").append("\"questionId").append("\"").append(">");


    }

    private void writeEditQuestions(StringBuilder responseText) throws SQLException {
        int surveyId = Integer.parseInt(parameters.get("id"));

        for (Question q : questionDao.listAll()) {
            if(q.getSurveyId() == surveyId){
                responseText
                        .append("<option").append(" value=").append("\"").append(q.getId()).append("\"").append(">").append(q.getTitle("question title"))
                        .append("</option>");

            }

        }

        responseText
                .append("<input type=\"hidden\" name=\"").append("survey").append("\"")
                .append(" value=\"").append(parameters.get("title")).append("=").append(parameters.get("id")).append("\">")
                .append("</input>").append("</select>")
                .append("<br>").append("<label value=\"").append("select").append("\">").append("Change question name").append("</label>")
                .append("<input type=\"text\" name=\"").append("newQuestionName").append("\"")
                .append(" value=\"").append("\">")
                .append("</input>")
                .append("<br>").append("<label value=\"").append("select").append("\">").append("Change question subtitle").append("</label>")
                .append("<input type=\"text\" name=\"").append("newQuestionSubtitle").append("\"")
                .append(" value=\"").append("\">")
                .append("</input>")
                .append("<button type=\"submit\" value=\"Submit\">\n").append("Go to selected question").append("</button>").append("</form>");

    }
}


