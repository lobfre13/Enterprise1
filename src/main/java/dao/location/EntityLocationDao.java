package dao.location;

import dto.Location;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 23.11.2015.
 */
@Stateless
public class EntityLocationDao implements LocationDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public EntityLocationDao() {
    }

    public EntityLocationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*@AroundInvoke
    private Object intercept(InvocationContext invocationContext) throws Exception{
        entityManager.getTransaction().begin();
        try{
            return invocationContext.proceed();
        }
        finally {
            entityManager.getTransaction().commit();
        }
    }*/

    @Override
    public Location persist(Location location) {
        entityManager.persist(location);
        return location;
    }

    @Override
    public Location getLocation(int id) {
        return entityManager.find(Location.class, id);
    }

    @Override
    public List<Location> gelAll() {
        return entityManager.createNamedQuery("Location.getAll", Location.class).getResultList();
    }

    public void close(){
        entityManager.close();
    }
}
