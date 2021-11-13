package no.kristiania.controllers;

import no.kristiania.dao.OptionDao;
import no.kristiania.entity.Option;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionsControllerTest {
    private final OptionDao optionDao;
    private final OptionsController optionsController;

    public OptionsControllerTest(OptionDao optionDao, OptionsController optionsController) {
        this.optionDao = optionDao;
        this.optionsController = optionsController;
    }

    @Test
    void shouldHandleHttpMessage() throws SQLException {
        String httpMessage = "HTTP/1.1 200 OK";
        String responseText = "";

        int value = 1;
        for (Option option : optionDao.listAll()) {
            responseText += "<option value=" + (value++) + ">" + option.getTitle() + "</option>";
        }
        assertEquals(httpMessage + responseText, optionsController.handle("SOMETHING"));
    }

}
