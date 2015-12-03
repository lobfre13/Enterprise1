package dao.subject;

import dao.event.JPASubject;
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
@JPASubject
@Stateless
public class JPASubjectDao implements SubjectDao {
    @PersistenceContext(unitName = "LMS")
    private EntityManager entityManager;

    public JPASubjectDao() {
    }

    public JPASubjectDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Subject persist(Subject subject) {
        entityManager.persist(subject);
        return subject;
    }

    @Override
    public Subject update(Subject subject) {
        if(!entityManager.contains(subject))
            subject = entityManager.merge(subject);
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
