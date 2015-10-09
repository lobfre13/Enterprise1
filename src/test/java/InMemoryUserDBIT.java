import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class InMemoryUserDBIT {
    private UserDAO db;

    @Before
    public void setUp() throws Exception {
        db = new InMemoryUserDB();
    }

    @Test
    public void addUser(){
        assertTrue(db.addUser(new User()));
    }

    @Test
    public void getUser(){
        db.addUser(new User());
        assertTrue(db.getUser(1) != null);
    }

    @Test
    public void updateUser(){
        User user = new User();
        db.addUser(user);
        user.setEmail("mail");
        assertTrue(db.updateUser(user));
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User();
        db.addUser(user);
        assertTrue(db.deleteUser(user));
    }

    @Test
    public void getUsers() throws Exception {
        db.addUser(new User());
        db.addUser(new User());
        db.addUser(new User());
        assertTrue(db.getAllUsers().size() >= 3);
    }
}