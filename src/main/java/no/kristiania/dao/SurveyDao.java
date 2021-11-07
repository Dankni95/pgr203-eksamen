package no.kristiania.dao;

import no.kristiania.entity.Survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class SurveyDao extends AbstractDao<Survey> {
    public SurveyDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected Survey readFromResultSet(ResultSet rs) throws SQLException {
        Survey survey = new Survey();
        survey.setId(rs.getLong("id"));
        survey.setTitle(rs.getString("survey_title"));
        survey.setUserId(rs.getLong("user_id"));
        return survey;
    }

    public void save(Survey survey) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into user_survey (user_id, survey_title) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setLong(1, survey.getUserId());
                statement.setString(2, survey.getTitle());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    survey.setId(rs.getLong("id"));
                }
            }
        }
    }


    public Long retrieveSurveyIdbyTitle(String name) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM user_survey WHERE survey_title = ?")) {
                statement.setString(1, name);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    return rs.getLong(1);
                }
            }
        }
    }


    @Override
    public List<Survey> listAll() throws SQLException {
        return super.listAll("SELECT * FROM user_survey");
    }

    @Override
    public void deleteAll() throws SQLException {
        super.deleteAll("delete from user_survey");
    }

    public Survey retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM user_survey WHERE id =  ?", id);
    }
}
