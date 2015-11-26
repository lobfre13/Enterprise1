package dao.user;

import dto.User;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

/**
 * Created by Fredrik on 09.10.2015.
 */
@UserDAOQualifier
@Stateless
public class EntityUserDB implements UserDAO{
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public EntityUserDB() {
    }

    public EntityUserDB(EntityManager entityManager){
        this.entityManager = entityManager;
    }

//    @AroundInvoke
//    private Object intercept(InvocationContext invocationContext) throws Exception{
//        entityManager.getTransaction().begin();
//        try{
//            return invocationContext.proceed();
//        }
//        finally {
//            entityManager.getTransaction().commit();
//        }
//    }

    @Override
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if(!entityManager.contains(user))
            entityManager.merge(user);
        return user;
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createNamedQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public boolean deleteUser(User user) {
        entityManager.remove(user);
        return true;
    }

    public void close(){
        entityManager.close();
    }
}
