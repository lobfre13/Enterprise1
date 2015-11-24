import dao.subject.EntitySubjectDao;
import dto.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Fredrik on 24.11.2015.
 */
public class EntitySubjectDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntitySubjectDao dao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        dao = new EntitySubjectDao(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        dao.close();
        factory.close();
    }

    @Test
    public void persist() throws Exception {
        Subject subject = new Subject();
        subject.setName("PG5600");
        subject.setUsers(new ArrayList<>());
        transaction.begin();
        dao.persist(subject);
        transaction.commit();

        assertTrue(subject.getId() > 0);
    }

    @Test
    public void lol() throws Exception {

    }
}
