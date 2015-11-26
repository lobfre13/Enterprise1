package controller;

import dao.subject.SubjectDao;
import dao.user.UserDAO;
import dao.user.UserDAOQualifier;
import dto.Subject;
import dto.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
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
    @Inject @UserDAOQualifier
    private UserDAO userDAO;
    @Inject
    private SubjectDao subjectDao;

   @PostConstruct
    public void construct(){
        user = new User();
    }

    public void persistUser(){
        if(userDAO != null)
            userDAO.addUser(user);
    }

    public List<SelectItem> getUserRoles(){
        return Arrays.asList(User.Role.values()).stream().map(role -> new SelectItem(role, role.name())).collect(Collectors.toList());
    }

    public List<User> getAll(){
        return userDAO.getAllUsers();
    }

    @Transactional
    public void deleteUser(User user){
        user = userDAO.updateUser(user);
        userDAO.deleteUser(user);
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
        user = userDAO.getUser(currentUserId);
    }
}
