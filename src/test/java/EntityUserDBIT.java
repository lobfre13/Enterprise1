import dao.user.EntityUserDB;
import dto.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Fredrik on 20.11.2015.
 */
public class EntityUserDBIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityUserDB dao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        dao = new EntityUserDB(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        dao.close();
        factory.close();
    }

    @Test
    public void addUser() throws Exception {
        User user = new User(0, "f@f.com", "validPassword123", User.Role.STUDENT);
        transaction.begin();
        dao.addUser(user);
        transaction.commit();
        assertTrue(user.getId() > 0);
    }

    @Test
    public void getUser() throws Exception {
        User user = dao.getUser(1);
        assertNotNull(user);
    }

    @Test
    public void updateUser() throws Exception {
        User user = dao.getUser(1);
        user.setEmail("new@email.com");
        dao.updateUser(user);
        user = dao.getUser(1);
        assertEquals("new@email.com", user.getEmail());
    }

    @Test
    public void remove() throws Exception {
        User user = dao.getUser(1);
        transaction.begin();
        dao.deleteUser(user);
        transaction.commit();
        user = dao.getUser(1);
        assertNull(user);
    }

    @Test
    public void addInvalidUser() throws Exception {
        exception.expect(ConstraintViolationException.class);
        User user = new User(0, "aMail", "pass", User.Role.STUDENT);
        transaction.begin();
        dao.addUser(user);
        transaction.commit();
    }

    @Test
    public void testGetAll() throws Exception {
        assertTrue(dao.getAllUsers().size() > 0);
    }
}
