package dao.user;

import dto.Subject;
import dto.User;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 09.10.2015.
 */
@JPAUser
@Stateless
public class JPAUserDao implements UserDAO{
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;
    private Logger logger = Logger.getLogger(getClass());

    public JPAUserDao() {
    }

    public JPAUserDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public User addUser(User user) {
        try{
            entityManager.persist(user);
            return user;
        } catch (EntityExistsException | IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User updateUser(User user) {
        try{
            if(!entityManager.contains(user))
                user = entityManager.merge(user);
            return user;
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public User getUser(int id) {
        try {
            return entityManager.find(User.class, id);
        }
        catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return entityManager.createNamedQuery("User.getAll", User.class).getResultList();
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;

        }
    }

    @Override
    public boolean deleteUser(User user) {
        try{
            user = updateUser(user);
            entityManager.remove(user);
            for(Subject subject : user.getSubjects()){
                subject.getUsers().remove(user);
            }
            return true;
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    public void close(){
        entityManager.close();
    }
}
