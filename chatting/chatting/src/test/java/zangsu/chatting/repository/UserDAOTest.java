package zangsu.chatting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import zangsu.chatting.domain.User;

import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/ApplicationContext.xml")
class UserDAOTest {

    @Autowired
    UserDAO userDAO;

    @Test
    public void checkConnection() throws SQLException {
        userDAO.checkConnection();
    }

    @Test
    public void addUser() throws SQLException {
        User user = new User("zangsu", "zangsuID", "zangsuPW");
        userDAO.add(user);

        User getUser = userDAO.get(user.getId());
        Assertions.assertThat(getUser.getName()).isEqualTo(user.getName());
        Assertions.assertThat(getUser.getPassword()).isEqualTo(user.getPassword());
    }
}