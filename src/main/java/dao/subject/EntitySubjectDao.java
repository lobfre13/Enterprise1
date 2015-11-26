package dao.subject;

import dto.Subject;
import dto.User;

import javax.ejb.Stateless;
import javax.inject.Qualifier;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 24.11.2015.
 */
@Stateless
public class EntitySubjectDao implements SubjectDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public EntitySubjectDao() {
    }

    public EntitySubjectDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Subject persist(Subject subject) {
        entityManager.persist(subject);
        return subject;
    }

    @Override
    public Subject getSubject(int id) {
        return entityManager.find(Subject.class, id);
    }

    @Override
    public List<User> getUsers(Subject subject) {
        return null;
    }

    @Override
    public List<Subject> getAll() {
        return entityManager.createNamedQuery("Subject.getAll", Subject.class).getResultList();
    }

    public void close(){
        entityManager.close();
    }
}
