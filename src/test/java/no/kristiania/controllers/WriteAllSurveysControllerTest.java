package no.kristiania.controllers;

import no.kristiania.dao.SurveyDao;
import no.kristiania.entity.Survey;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WriteAllSurveysControllerTest {
    private final SurveyDao surveyDao;
    private final WriteAllSurveysController writeAllSurveysController;

    public WriteAllSurveysControllerTest(SurveyDao surveyDao, WriteAllSurveysController writeAllSurveysController) {
        this.surveyDao = surveyDao;
        this.writeAllSurveysController = writeAllSurveysController;
    }

}
