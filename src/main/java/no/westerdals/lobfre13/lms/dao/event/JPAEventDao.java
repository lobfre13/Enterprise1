package no.westerdals.lobfre13.lms.dao.event;

import no.westerdals.lobfre13.lms.dto.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 03.12.2015.
 */
@JPAEvent
@Stateless
public class JPAEventDao implements EventDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public JPAEventDao() {
    }

    public JPAEventDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public Event persist(Event event) {
        entityManager.persist(event);
        return event;
    }

    @Override
    public List<Event> getAll() {
        return entityManager.createNamedQuery("Event.getAll", Event.class).getResultList();
    }

    @Override
    public Event getEvent(int id) {
        return entityManager.find(Event.class, id);
    }

    @Override
    public Event update(Event event) {
        if(!entityManager.contains(event))
            event = entityManager.merge(event);

        return event;
    }

    @Override
    public boolean delete(Event event) {
        event = update(event); //assert managed entity
        entityManager.remove(event);
        return true;
    }

    public void close(){
        entityManager.close();
    }
}
