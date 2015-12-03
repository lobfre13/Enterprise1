package controller;

import dao.event.JPASubject;
import dao.subject.SubjectDao;
import dao.user.UserDAO;
import dao.user.JPAUser;
import dto.User;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 25.11.2015.
 */
@Model
public class UserController {
    private User user;
    private int currentUserId;
    @Inject @JPAUser
    private UserDAO userDAO;
    @Inject @JPASubject
    private SubjectDao subjectDao;

   @PostConstruct
    public void construct(){
        user = new User();
    }

    public void persistUser(){
        userDAO.addUser(user);
    }

    public List<SelectItem> getUserRoles(){
        return Arrays.asList(User.Role.values()).stream().map(role -> new SelectItem(role, role.name())).collect(Collectors.toList());
    }

    public List<User> getAll(){
        return userDAO.getAllUsers();
    }

    public void deleteUser(){
        if(currentUserId <= 0) return;
        initUser();
        userDAO.deleteUser(user);
    }

    public void initUser(){
        user = userDAO.getUser(currentUserId);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }
}
