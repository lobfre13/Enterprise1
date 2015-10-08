import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class H2UserDBIT {
    private String dbUrl = "jdbc:h2:mem://localhost/~/LMS";
    private User user;
    private UserDAO h2;
    private Connection con;

    @Before
    public void setUp() throws Exception {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection(dbUrl, "sa", "sa");
        createTable();
        user = new User(0, "lol@l.com", "lul", User.Role.STUDENT);
        h2 = new H2UserDB(dbUrl);
        h2.addUser(user);
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void addUser() throws Exception {
        boolean result = h2.addUser(user);
        assertTrue(result);
    }

    @Test
    public void updateUser() throws Exception {
        User user = h2.getUser(1);
        user.setEmail("changed@g.com");
        boolean result = h2.updateUser(user);
        assertTrue(result);
    }

    @Test
    public void getUser() throws Exception {
        User user = h2.getUser(1);
        assertTrue(this.user.equals(user));
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