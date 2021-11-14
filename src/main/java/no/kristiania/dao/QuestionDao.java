package no.kristiania.dao;

import no.kristiania.entity.Question;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class QuestionDao extends AbstractDao<Question> {
    public QuestionDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Question readFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setId(rs.getLong("id"));
        question.setTitle(rs.getString("question_title"));
        question.setText(rs.getString("question_text"));
        question.setSurveyId(rs.getLong("survey_id"));
        question.setUserId(rs.getLong("user_id"));
        return question;
    }

    public void save(Question question) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into question (question_title, question_text, survey_id, user_id) values (?, ?,?,?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getText());
                statement.setLong(3, question.getSurveyId());
                statement.setLong(4, question.getUserId());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    question.setId(rs.getLong("id"));
                }
            }
        }
    }

    public void update(Question question) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "update question set (question_title, question_text) = (?,?) where id = (?)"
            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getText());
                statement.setLong(3, question.getId());

                statement.executeUpdate();
            }
        }
    }


    @Override
    public List<Question> listAll() throws SQLException {
        return super.listAll("SELECT * FROM question");
    }

    @Override
    public void deleteAll() throws SQLException {
        super.deleteAll("delete from question");
    }

    public Question retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM question WHERE id =  ?", id);
    }
}
