package no.kristiania.controllers;

import no.kristiania.dao.OptionDao;
import no.kristiania.entity.Option;
import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;

import java.sql.SQLException;

public class OptionsControllerTest {
    private final OptionDao optionDao;

    public OptionsControllerTest(OptionDao optionDao) {
        this.optionDao = optionDao;
    }
    
}
