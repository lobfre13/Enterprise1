package dao.location;

import dao.event.JPALocation;
import dto.Location;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 23.11.2015.
 */
@JPALocation
@Stateless
public class JPALocationDao implements LocationDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;
    private Logger logger = Logger.getLogger(getClass());

    public JPALocationDao() {
    }

    public JPALocationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
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

    @Override
    public List<Location> gelAll() {
        return entityManager.createNamedQuery("Location.getAll", Location.class).getResultList();
    }

    @Override
    public Location update(Location location) {
        if(!entityManager.contains(location))
            location = entityManager.merge(location);
        return location;
    }

    @Override
    public boolean delete(Location location) {
        location = update(location);
        entityManager.remove(location);
        return true;
    }

    public void close(){
        entityManager.close();
    }
}
