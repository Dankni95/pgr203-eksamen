package no.kristiania.http;

import no.kristiania.survey.OptionDao;

import java.sql.SQLException;

public class OptionsController implements HttpController {
    private final OptionDao optionDao;

    public OptionsController(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException {
        String responseText = "";

        int value = 1;
        for (String role : optionDao.listAll()) {
            responseText += "<option value=" + (value++) + ">" + role + "</option>";
        }
        return new HttpMessage("HTTP 200 OK", responseText);
    }
}
