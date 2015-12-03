package IT;

import dao.user.JPAUserDao;
import dto.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.*;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 20.11.2015.
 */
public class JPAUserDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private JPAUserDao userDao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        userDao = new JPAUserDao(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        userDao.close();
        factory.close();
    }

    @Test
    public void addUser() throws Exception {
        User user = new User(0, "f@f.com", "validPassword123", User.Role.STUDENT);
        transaction.begin();
        userDao.addUser(user);
        transaction.commit();
        assertTrue(user.getId() > 0);
    }

    @Test
    public void getUser() throws Exception {
        User user = userDao.getUser(1);
        assertNotNull(user);
    }

    @Test
    public void updateUser() throws Exception {
        User user = userDao.getUser(1);
        user.setEmail("new@email.com");
        userDao.updateUser(user);
        user = userDao.getUser(1);
        assertEquals("new@email.com", user.getEmail());
    }

    @Test
    public void remove() throws Exception {
        User user = userDao.getUser(6);

        transaction.begin();
        userDao.deleteUser(user);
        transaction.commit();

        user = userDao.getUser(6);
        assertNull(user);
    }

    @Test
    public void addInvalidUser() throws Exception {
        exception.expect(RollbackException.class);
        User user = new User(0, "aMail", "pass", User.Role.STUDENT);
        transaction.begin();
        userDao.addUser(user);
        transaction.commit();
    }

    @Test
    public void testGetAll() throws Exception {
        assertTrue(userDao.getAllUsers().size() > 0);
    }
}
