import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Fredrik on 20.11.2015.
 */
public class EntityUserDBIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityUserDB dao;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("User");
        entityManager = factory.createEntityManager();
        dao = new EntityUserDB(entityManager);
    }

    @After
    public void tearDown() throws Exception {
        dao.close();
        factory.close();
    }

    @Test
    public void addUser() throws Exception {
        User user = new User();
        entityManager.getTransaction().begin();
        dao.addUser(user);
        entityManager.getTransaction().commit();
        assertTrue(user.getId() > 0);
    }

    @Test
    public void getUser() throws Exception {
        User user = dao.getUser(1);
        assertTrue(user != null);
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
        dao.deleteUser(user);
        assertTrue(dao.getAllUsers().isEmpty());
    }

    @Test
    public void testGetAll() throws Exception {
        dao.getAllUsers();
    }
}
