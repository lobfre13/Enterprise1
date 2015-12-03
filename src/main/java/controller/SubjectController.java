package controller;

import dao.event.JPALocation;
import dao.event.JPASubject;
import dao.location.LocationDao;
import dao.subject.SubjectDao;
import dao.user.UserDAO;
import dao.user.JPAUser;
import dto.Location;
import dto.Subject;
import dto.User;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 26.11.2015.
 */
@Model
public class SubjectController {
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

    public void addUser(){
        User user = userDAO.getUser(selectedUserId);
        subject.getUsers().add(user);
        subjectDao.update(subject);
    }

    public void initSubject() throws IOException {
        subject = subjectDao.getSubject(selectedSubjectId);
        if(subject != null) return;

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/subject/all-subjects.jsf");
    }

    public List<SelectItem> getAllUsers(){
        return userDAO.getAllUsers().stream().map(user -> new SelectItem(user.getId(), user.getEmail())).collect(Collectors.toList());
    }

    public List<Subject> getAll(){
        return subjectDao.getAll();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }



    public List<SelectItem> getAllLocations(){
        return locationDao.gelAll().stream().map(location -> new SelectItem(location.getId(), location.getRoom() + ", " + location.getBuilding())).collect(Collectors.toList());
    }

    public void setLocation() throws IOException {
        initSubject();
        Location location = locationDao.getLocation(selectedLocationId);
        subject.setLocation(location);
        subjectDao.update(subject);
    }

    public void removeUser(int userId, int subjectId) throws IOException {
       // Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        //if(!params.get("remove").equals("true")) return;

        //initSubject();
        subject = subjectDao.getSubject(subjectId);
        User user = userDAO.getUser(userId);
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
