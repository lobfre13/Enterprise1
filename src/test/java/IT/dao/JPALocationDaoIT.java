package IT.dao;

import no.westerdals.lobfre13.lms.dao.location.JPALocationDao;
import no.westerdals.lobfre13.lms.dto.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.*;
import static org.junit.Assert.*;

/**
 * Created by Fredrik on 23.11.2015.
 */
public class JPALocationDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private JPALocationDao locationDao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        locationDao = new JPALocationDao(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        locationDao.close();
        factory.close();
    }

    @Test
    public void addLocation() throws Exception {
        Location location = new Location("82", "Galleriet");
        transaction.begin();
        locationDao.persist(location);
        transaction.commit();
        assertTrue(location.getId() > 0);
    }

    @Test
    public void getLocation() throws Exception {
        Location location = locationDao.getLocation(1);
        assertNotNull(location);
    }

    @Test
    public void addInvalidLocation() throws Exception {
        exception.expect(RollbackException.class);
        Location location = new Location(null, null);
        transaction.begin();
        locationDao.persist(location);
        transaction.commit();
    }
}
