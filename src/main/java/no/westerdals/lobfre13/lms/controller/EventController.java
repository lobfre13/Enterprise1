package no.westerdals.lobfre13.lms.controller;

import no.westerdals.lobfre13.lms.dao.event.EventDao;
import no.westerdals.lobfre13.lms.dao.event.JPAEvent;
import no.westerdals.lobfre13.lms.dao.event.JPASubject;
import no.westerdals.lobfre13.lms.dao.subject.SubjectDao;
import no.westerdals.lobfre13.lms.dto.Event;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 03.12.2015.
 */
@Model
public class EventController extends BaseController {
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
        try{
            event.setSubject(subjectDao.getSubject(subjectId));
            eventDao.persist(event);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "event.added");
        } catch (EJBTransactionRolledbackException e){
            String message = getConstraintViolationMessageFromException(e);
            addFacesMessage(FacesMessage.SEVERITY_ERROR, message);
        }
    }

    public String initEvent(){
        try{
            event = eventDao.getEvent(currentEventId);
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }
        if(event == null) return "/event/all-events.jsf";

        return null;
    }

    public void deleteEvent(){
        initEvent();
        if(event == null) return;

        try {
            eventDao.delete(event);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "event.deleted");
        } catch (EJBTransactionRolledbackException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }
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
        return Arrays.asList(Event.Type.values()).stream()
                .map(type -> new SelectItem(type, type.name()))
                .collect(Collectors.toList());
    }

    public List<SelectItem> allSubjects(){
        try{
            return subjectDao.getAll().stream()
                    .map(subject -> new SelectItem(subject.getId(), subject.getName()))
                    .collect(Collectors.toList());
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
            return null;
        }
    }

    public List<Event> allEvents(){
        try{
            return eventDao.getAll();
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
            return null;
        }
    }
}
