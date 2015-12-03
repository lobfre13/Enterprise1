package controller;

import dao.event.JPASubject;
import dao.subject.SubjectDao;
import dao.user.UserDAO;
import dao.user.JPAUser;
import dto.Subject;
import dto.User;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 26.11.2015.
 */
@Model
public class SubjectController {
    private Subject subject;
    private int selectedSubjectId;
    private int selectedUserId;
    @Inject @JPASubject
    private SubjectDao subjectDao;
    @Inject @JPAUser
    private UserDAO userDAO;

    public SubjectController() {
    }

    @PostConstruct
    public void construct(){
        subject = new Subject();
    }

    public void persistSubject(){
        subjectDao.persist(subject);
    }

    @Transactional
    public void addUser(){
        User user = userDAO.getUser(selectedUserId);
       // user = userDAO.updateUser(user);
        subject.getUsers().add(user);
    }

    public int getSelectedSubjectId() {
        return selectedSubjectId;
    }

    public void setSelectedSubjectId(int selectedSubjectId) {
        this.selectedSubjectId = selectedSubjectId;
        subject = subjectDao.getSubject(selectedSubjectId);
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

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
}
