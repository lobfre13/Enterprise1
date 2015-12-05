package no.westerdals.lobfre13.lms.dao.event;

import no.westerdals.lobfre13.lms.dto.Event;

import java.util.List;

/**
 * Created by Fredrik on 03.12.2015.
 */
public interface EventDao {
    Event persist(Event event);
    List<Event> getAll();
    Event getEvent(int id);
    Event update(Event event);
    boolean delete(Event event);
}
