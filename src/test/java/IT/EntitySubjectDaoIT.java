package IT;

import dao.location.EntityLocationDao;
import dao.subject.EntitySubjectDao;
import dao.user.EntityUserDB;
import dto.Location;
import dto.Subject;
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
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Fredrik on 24.11.2015.
 */
public class EntitySubjectDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntitySubjectDao subjectDao;
    private EntityLocationDao locationDao;
    private EntityUserDB userDao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        subjectDao = new EntitySubjectDao(entityManager);
        locationDao = new EntityLocationDao(entityManager);
        userDao = new EntityUserDB(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        subjectDao.close();
        factory.close();
    }

    @Test
    public void persist() throws Exception {
        Subject subject = new Subject();
        subject.setName("PG5600");
        subject.setUsers(new ArrayList<>());
        transaction.begin();
        subjectDao.persist(subject);
        transaction.commit();

        assertTrue(subject.getId() > 0);
    }

    @Test
    public void hasLocation() throws Exception {
        Subject subject = subjectDao.getSubject(1);
        assertNotNull(subject.getLocation());
    }

    @Test
    public void noLocation() throws Exception {
        Subject subject = subjectDao.getSubject(2);
        assertNull(subject.getLocation());
    }

    @Test
    public void addLocation() throws Exception {
        Subject subject = subjectDao.getSubject(2);
        Location location = locationDao.getLocation(1);
        assertNull(subject.getLocation());

        subject.setLocation(location);
        transaction.begin();
        subjectDao.persist(subject);
        transaction.commit();

        Subject subject2 = subjectDao.getSubject(2);
        assertNotNull(subject2.getLocation());
    }

    @Test
    public void hasUsers() throws Exception {
        Subject subject = subjectDao.getSubject(3);
        assertEquals(5, subject.getUsers().size());
    }

    @Test
    public void addUser() throws Exception {
        Subject subject = subjectDao.getSubject(3);
        List<User> users = subject.getUsers();
        users.add(userDao.getUser(6));

        transaction.begin();
        subjectDao.persist(subject);
        transaction.commit();

        subject = subjectDao.getSubject(3);
        assertEquals(6, subject.getUsers().size());
    }

    @Test
    public void removeUser() throws Exception {
        Subject subject = subjectDao.getSubject(3);
        List<User> users = subject.getUsers();
        users.remove(users.size()-1);

        transaction.begin();
        subjectDao.persist(subject);
        transaction.commit();

        subject = subjectDao.getSubject(3);
        assertEquals(4, subject.getUsers().size());

    }
}
