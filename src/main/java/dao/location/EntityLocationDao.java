package dao.location;

import dto.Location;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 * Created by Fredrik on 23.11.2015.
 */
public class EntityLocationDao implements LocationDao {
    private EntityManager entityManager;

    public EntityLocationDao() {
    }

    public EntityLocationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @AroundInvoke
    private Object intercept(InvocationContext invocationContext) throws Exception{
        entityManager.getTransaction().begin();
        try{
            return invocationContext.proceed();
        }
        finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public Location persist(Location location) {
        entityManager.persist(location);
        return location;
    }

    @Override
    public Location getLocation(int id) {
        return entityManager.find(Location.class, id);
    }

    public void close(){
        entityManager.close();
    }
}
