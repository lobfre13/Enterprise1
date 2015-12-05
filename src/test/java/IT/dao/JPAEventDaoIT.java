package IT.dao;

import no.westerdals.lobfre13.lms.dao.event.JPAEventDao;
import no.westerdals.lobfre13.lms.dao.subject.JPASubjectDao;
import no.westerdals.lobfre13.lms.dto.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Fredrik on 03.12.2015.
 */
public class JPAEventDaoIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private JPAEventDao eventDao;
    private JPASubjectDao subjectDao;
    private EntityTransaction transaction;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("LMS");
        entityManager = factory.createEntityManager();
        eventDao = new JPAEventDao(entityManager);
        subjectDao = new JPASubjectDao(entityManager);
        transaction = entityManager.getTransaction();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        factory.close();
    }

    @Test
    public void getEvent() throws Exception {
        Event event = eventDao.getEvent(1);
        assertNotNull(event);
    }

    @Test
    public void addEvent() throws Exception {
        Event event = new Event();
        event.setTitle("testEvent");
        event.setDescription("TestDesc");
        event.setType(Event.Type.LECTURE);

        Calendar cal = Calendar.getInstance();
        event.setStartTime(cal.getTime());
        event.setEndTime(new Date(cal.getTimeInMillis() + 3600000));
        event.setSubject(subjectDao.getSubject(1));

        transaction.begin();
        eventDao.persist(event);
        transaction.commit();

        assertTrue(event.getId() > 0);
    }

    @Test
    public void getAllEvents_shouldBeSortedDESC() throws Exception {
        List<Event> events = eventDao.getAll();
        assertTrue(events.get(0).getStartTime().compareTo(events.get(1).getStartTime()) > 0);
        assertTrue(events.get(1).getStartTime().compareTo(events.get(2).getStartTime()) > 0);
    }
}
