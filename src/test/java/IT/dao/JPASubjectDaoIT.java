package IT.dao;

import no.westerdals.lobfre13.lms.dao.location.JPALocationDao;
import no.westerdals.lobfre13.lms.dao.subject.JPASubjectDao;
import no.westerdals.lobfre13.lms.dao.user.JPAUserDao;
import no.westerdals.lobfre13.lms.dto.Location;
import no.westerdals.lobfre13.lms.dto.Subject;
import no.westerdals.lobfre13.lms.dto.User;
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

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 24.11.2015.
 */
public class JPASubjectDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private JPASubjectDao subjectDao;
    private JPALocationDao locationDao;
    private JPAUserDao userDao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        subjectDao = new JPASubjectDao(entityManager);
        locationDao = new JPALocationDao(entityManager);
        userDao = new JPAUserDao(entityManager);
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
        subject.setName("PG6100");
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
        users.remove(users.iterator().next());

        transaction.begin();
        subjectDao.persist(subject);
        transaction.commit();

        subject = subjectDao.getSubject(3);
        assertEquals(4, subject.getUsers().size());

    }
}
