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
        subjectDao.persist(subject);
    }

    public void addUser() {
        User user = userDAO.getUser(selectedUserId);
        if(subject.getUsers().contains(user)){
            addFacesMessage(FacesMessage.SEVERITY_ERROR, "user.exists");
            return;
        }

        try{
            subject.getUsers().add(user);
            subjectDao.update(subject);
        } catch (EJBTransactionRolledbackException e){
            addFacesMessage(FacesMessage.SEVERITY_FATAL, "error.unknown");
            return;
        }
        addFacesMessage(FacesMessage.SEVERITY_INFO, "user.added");
    }

    public String initSubject() {
        subject = subjectDao.getSubject(selectedSubjectId);
        if(subject == null) return "/subject/all-subjects.jsf"; //invalid subject - redirecting

        return null;
    }

    public List<SelectItem> getAllAvailableUsers(){ //all users not in current subject
        return userDAO.getAllUsers().stream()
                .map(user -> new SelectItem(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public List<Subject> getAllSubjects(){
        return subjectDao.getAll();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }



    public List<SelectItem> getAllLocations(){
        return locationDao.gelAll().stream()
                .map(location -> new SelectItem(location.getId(), location.getRoom() + ", " + location.getBuilding()))
                .collect(Collectors.toList());
    }

    public void setLocation() {
        Location location = locationDao.getLocation(selectedLocationId);
        subject.setLocation(location);
        subjectDao.update(subject);
    }

    public void removeUser() {
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String remove = params.get("remove");
        if(remove == null || !remove.equals("true")) return;

        User user = userDAO.getUser(selectedUserId);
        subject.getUsers().remove(user);
        subjectDao.update(subject);
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
