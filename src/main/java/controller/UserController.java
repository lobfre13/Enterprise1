package controller;

import dao.event.JPASubject;
import dao.subject.SubjectDao;
import dao.user.UserDAO;
import dao.user.JPAUser;
import dto.User;

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
 * Created by Fredrik on 25.11.2015.
 */
@Model
public class UserController extends BaseController {
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
        try{
            userDAO.addUser(user);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "user.added");
        } catch (EJBTransactionRolledbackException e){
            String errorCode = getSQLErrorCodeFromException(e);
            if(errorCode.equals("DUPLICATE_KEY")) errorCode = "user.exists";
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, errorCode);
        }
    }

    public List<SelectItem> getUserRoles(){
        return Arrays.asList(User.Role.values()).stream().map(role -> new SelectItem(role, role.name())).collect(Collectors.toList());
    }

    public List<User> getAll(){
        try{
            return userDAO.getAllUsers();
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
            return null;
        }
    }

    public void deleteUser(){
        initUser();
        if(user == null) return;
        try{
            userDAO.deleteUser(user);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "user.deleted");
        } catch (EJBTransactionRolledbackException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }

    }

    public void initUser(){
        try{
            user = userDAO.getUser(currentUserId);
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }
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
