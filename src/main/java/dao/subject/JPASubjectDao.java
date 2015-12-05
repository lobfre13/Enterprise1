package dao.subject;

import dao.event.JPASubject;
import dto.Subject;
import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 24.11.2015.
 */
@JPASubject
@Stateless
public class JPASubjectDao implements SubjectDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;
    private Logger logger = Logger.getLogger(getClass());

    public JPASubjectDao() {
    }

    public JPASubjectDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Subject persist(Subject subject) {
        try{
            entityManager.persist(subject);
            return subject;
        } catch (EntityExistsException | IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Subject update(Subject subject) {
        try{
            if(!entityManager.contains(subject))
                subject = entityManager.merge(subject);
            return subject;
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Subject getSubject(int id) {
        try{
            return entityManager.find(Subject.class, id);
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Subject> getAll() {
        try{
            return entityManager.createNamedQuery("Subject.getAll", Subject.class).getResultList();
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    public void close(){
        entityManager.close();
    }
}
