package no.kristiania.dao;

import no.kristiania.entity.Option;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OptionDao extends AbstractDao<Option> {
    public OptionDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected Option readFromResultSet(ResultSet rs) throws SQLException {
        Option option = new Option();
        option.setQuestionId(rs.getLong("question_id"));
        option.setTitle(rs.getString("option_title"));
        option.setId(rs.getLong("id"));
        return option;
    }


    public void save(Option option) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into option (question_id,option_title) values (?,?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setLong(1, option.getQuestionId());
                statement.setString(2, option.getTitle());
                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    option.setId(rs.getLong("id"));
                }
            }
        }
    }


    @Override
    public List<Option> listAll() throws SQLException {
        return super.listAll("SELECT * FROM option");
    }

    @Override
    public void deleteAll() throws SQLException {
        super.deleteAll("delete from option");
    }

    public Option retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM option WHERE id =  ?", id);
    }

    public List<Option> listOptionsByQuestionId(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM option WHERE question_id = ?")) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Option> options = new ArrayList<>();
                    while (rs.next()) {
                        options.add(readFromResultSet(rs));
                    }
                    return options;
                }
            }
        }
    }
}