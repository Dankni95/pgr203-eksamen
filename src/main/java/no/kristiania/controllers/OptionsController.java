package no.kristiania.controllers;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.entity.Option;
import no.kristiania.dao.OptionDao;

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
        for (Option option : optionDao.listAll()) {
            responseText += "<option value=" + (value++) + ">" + option.getTitle() + "</option>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", responseText);

    }
}
