package no.kristiania.controllers;


import no.kristiania.dao.*;
import no.kristiania.entity.Question;
import no.kristiania.entity.Survey;
import no.kristiania.http.HttpGetClient;
import no.kristiania.http.HttpPostClient;
import no.kristiania.http.HttpServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteAllSurveysAsOptionsControllerTest {
    private final HttpServer server = new HttpServer(0);

    public WriteAllSurveysAsOptionsControllerTest() throws IOException {
    }
}
