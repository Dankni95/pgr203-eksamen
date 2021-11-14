package no.kristiania.dao;

import no.kristiania.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao extends AbstractDao<User> {
    public UserDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    protected User readFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    public void save(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into user_info (first_name, last_name, email) values (?, ?,?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    user.setId(rs.getLong("id"));
                }
            }
        }
    }


    @Override
    public List<User> listAll() throws SQLException {
        return super.listAll("SELECT * FROM user_info");
    }

    @Override
    public void deleteAll() throws SQLException {
        super.deleteAll("delete from user_info");
    }

    public User retrieve(long id) throws SQLException {
        return super.retrieve("SELECT * FROM user_info WHERE id =  ?", id);
    }
}
