package no.kristiania.dao;

import no.kristiania.entity.Answer;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AnswerDao extends AbstractDao<Answer> {
    public AnswerDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Answer readFromResultSet(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setId(rs.getLong("id"));
        answer.setUser_survey_id(rs.getLong("user_survey_id"));
        answer.setQuestion_id(rs.getLong("question_id"));
        answer.setOption_id(rs.getLong("option_id"));
        return answer;

    }

    public void save(Answer answer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into user_survey_answer (user_survey_id, question_id, option_id) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setLong(1, answer.getUser_survey_id());
                statement.setLong(2, answer.getQuestion_id());
                statement.setLong(3, answer.getOption_id());
                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answer.setId(rs.getLong("id"));
                }
            }
        }
    }

    @Override
    public List<Answer> listAll() throws SQLException {
        return super.listAll("SELECT * FROM user_survey_answer");
    }

    @Override
    public void deleteAll() throws SQLException {
        super.deleteAll("delete from user_survey_answer");
    }

    public Answer retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM user_survey_answer WHERE id = ?", id);
    }

    public Long retrieveAnswerIdByUserSurvey(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM user_survey_answer WHERE user_survey_id = ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    return rs.getLong(1);
                }
            }
        }
    }
}


