package controller;

import dao.event.JPALocation;
import dao.event.JPASubject;
import dao.location.LocationDao;
import dao.subject.SubjectDao;
import dao.user.JPAUser;
import dao.user.UserDAO;
import dto.Location;
import dto.Subject;
import dto.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 26.11.2015.
 */
@Model
public class SubjectController extends BaseController {
    private Subject subject;
    private int selectedSubjectId;
    private int selectedUserId;
    private int selectedLocationId;
    @Inject @JPASubject
    private SubjectDao subjectDao;
    @Inject @JPAUser
    private UserDAO userDAO;
    @Inject @JPALocation
    private LocationDao locationDao;

    public SubjectController() {
    }

    @PostConstruct
    public void construct(){
        subject = new Subject();
    }

    public void persistSubject(){
        try{
            subjectDao.persist(subject);
        } catch (EJBTransactionRolledbackException e){
            String errorCode = getSQLErrorCodeFromException(e);
            if(errorCode.equals("DUPLICATE_KEY")) errorCode = "subject.exists";
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, errorCode);
        }

    }

    public void addUser() {
        User user = initUser();
        if(user == null) return;
        if(subject.getUsers().contains(user)){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "subject.user.exists");
            return;
        }

        try{
            subject.getUsers().add(user);
            subjectDao.update(subject);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "subject.user.added");
        } catch (EJBTransactionRolledbackException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
        }
    }

    private User initUser(){
        try{
            return userDAO.getUser(selectedUserId);
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
            return null;
        }
    }

    public String initSubject() {
        try{
            subject = subjectDao.getSubject(selectedSubjectId);
        }
        catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
        }
        if(subject == null) return "/subject/all-subjects.jsf"; //invalid subject - redirecting

        return null;
    }

    private Location initLocation(){
        try{
            return locationDao.getLocation(selectedLocationId);
        } catch (IllegalArgumentException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
            return null;
        }
    }

    public List<SelectItem> getAllAvailableUsers(){
        try{
            return userDAO.getAllUsers().stream()
                    .map(user -> new SelectItem(user.getId(), user.getEmail()))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
            return null;
        }
    }

    public List<Subject> getAllSubjects(){
        try{
            return subjectDao.getAll();
        } catch (IllegalArgumentException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
            return null;
        }
    }


    public List<SelectItem> getAllLocations(){
        try {
            return locationDao.gelAll().stream()
                    .map(location -> new SelectItem(location.getId(), location.getRoom() + ", " + location.getBuilding()))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
            return null;
        }

    }

    public void setLocation() {
        Location location = initLocation();
        if(location != null && location.equals(subject.getLocation())){
            addFacesMessageFromKey(FacesMessage.SEVERITY_WARN, "subject.has.location");
            return;
        }
        try{
            subject.setLocation(location);
            subjectDao.update(subject);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "subject.location.added");
        } catch (EJBTransactionRolledbackException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
        }
    }

    public void removeUser() {
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String remove = params.get("remove");
        if(remove == null || !remove.equals("true")) return;

        try{
            User user = initUser();
            subject.getUsers().remove(user);
            subjectDao.update(subject);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "subject.user.removed");
        } catch (EJBTransactionRolledbackException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_FATAL, "error.unknown");
        }

    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getSelectedSubjectId() {
        return selectedSubjectId;
    }

    public void setSelectedSubjectId(int selectedSubjectId) {
        this.selectedSubjectId = selectedSubjectId;
    }

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;

    }

    public int getSelectedLocationId() {
        return selectedLocationId;
    }

    public void setSelectedLocationId(int selectedLocationId) {
        this.selectedLocationId = selectedLocationId;
    }


}
