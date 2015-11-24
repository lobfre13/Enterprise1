import dao.user.H2UserDB;
import dto.User;
import dao.user.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class H2UserDBIT {
    private String dbUrl = "jdbc:h2:mem://localhost/~/testDB";
    private UserDAO h2;
    private Connection con;

    @Before
    public void setUp() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        con = DriverManager.getConnection(dbUrl, "sa", "sa");
        createTable();

        User user = new User(0, "lol@l.com", "lul", User.Role.STUDENT);
        h2 = new H2UserDB(dbUrl, "sa", "sa");
        h2.addUser(user);
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void addUser() throws Exception {
        User user = new User(0, "lal", "pass", User.Role.TEACHER);
        boolean result = h2.addUser(user).getId() > 0;
        assertTrue(result);
    }

    @Test
    public void updateUser() throws Exception {
        User user = h2.getUser(1);
        user.setEmail("changed@g.com");
        h2.updateUser(user);
        User user2 = h2.getUser(1);
        assertEquals("changed@g.com", user2.getEmail());
    }

    @Test
    public void getUser() throws Exception {
        User user = h2.getUser(1);
        assertTrue(user != null);
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = h2.getAllUsers();
        assertFalse(users.isEmpty());
    }

    private void createTable(){
        String createTable = "CREATE TABLE users(id int auto_increment, email varchar(255), password varchar(255), role varchar(30))";
        try(PreparedStatement statement = con.prepareStatement(createTable)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}