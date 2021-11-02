package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


public class OptionDao extends AbstractDao<Option> {
    public OptionDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected Option readFromResultSet(ResultSet rs) throws SQLException {
        Option option = new Option();
        option.setTitle(rs.getString("option_title"));
        option.setId(rs.getLong("id"));
        return option;
    }


    public void save(Option option) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into option (option_title) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, option.getTitle());
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


    public Option retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM option WHERE id =  ?", id);
    }
}