import dao.location.EntityLocationDao;
import dto.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.*;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Fredrik on 23.11.2015.
 */
public class EntityLocationDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityLocationDao dao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        dao = new EntityLocationDao(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        dao.close();
        factory.close();
    }

    @Test
    public void addLocation() throws Exception {
        Location location = new Location("82", "Galleriet");
        transaction.begin();
        dao.persist(location);
        transaction.commit();
        assertTrue(location.getId() > 0);
    }

    @Test
    public void getLocation() throws Exception {
        Location location = dao.getLocation(1);
        assertNotNull(location);
    }

    @Test
    public void addInvalidLocation() throws Exception {
        exception.expect(RollbackException.class);
        Location location = new Location(null, null);
        transaction.begin();
        dao.persist(location);
        transaction.commit();
    }
}
