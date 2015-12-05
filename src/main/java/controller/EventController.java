package controller;

import dao.event.EventDao;
import dao.event.JPAEvent;
import dao.event.JPASubject;
import dao.subject.SubjectDao;
import dto.Event;
import dto.Subject;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 03.12.2015.
 */
@Model
public class EventController {
    private Event event;
    private int subjectId;
    private int currentEventId;

    @Inject @JPAEvent
    private EventDao eventDao;

    @Inject @JPASubject
    private SubjectDao subjectDao;

    @PostConstruct
    public void construct(){
        event = new Event();
    }

    public void persistEvent(){
        event.setSubject(subjectDao.getSubject(subjectId));
        eventDao.persist(event);
    }

    public String initEvent(){
        event = eventDao.getEvent(currentEventId);
        if(event == null) return "/event/all-events.jsf";

        return null;
    }

    public void deleteEvent(){
        Event event = eventDao.getEvent(currentEventId);
        if(event == null) return;
        eventDao.delete(event);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getCurrentEventId() {
        return currentEventId;
    }

    public void setCurrentEventId(int currentEventId) {
        this.currentEventId = currentEventId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public List<SelectItem> eventTypes(){
        return Arrays.asList(Event.Type.values()).stream().map(type -> new SelectItem(type, type.name())).collect(Collectors.toList());
    }

    public List<SelectItem> allSubjects(){
        return subjectDao.getAll().stream().map(subject -> new SelectItem(subject.getId(), subject.getName())).collect(Collectors.toList());
    }

    public List<Event> allEvents(){
        return eventDao.getAll();
    }
}
