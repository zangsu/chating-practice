package zangsu.chatting.repository;

import lombok.Setter;
import org.springframework.dao.EmptyResultDataAccessException;
import zangsu.chatting.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
public class UserDAO {

    DataSource dataSource;

    public void checkConnection() throws SQLException {
        System.out.println(dataSource);
        Connection connection = dataSource.getConnection();
        if(connection == null)
            throw new SQLException();
        else
            connection.close();
    }

    public void add(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        try{
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(
                    "insert into users (name, id, password) values (?,?,?)");

            ps.setString(1, user.getName());
            ps.setString(2, user.getId());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if(ps != null) ps.close();
            if(connection != null) connection.close();
        }
    }

    public User get(String id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from users where id=?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        connection.close();

        if(user == null)
            throw new EmptyResultDataAccessException(1);
        return user;
    }
}
