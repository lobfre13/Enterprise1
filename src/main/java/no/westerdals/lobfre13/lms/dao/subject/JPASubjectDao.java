package no.westerdals.lobfre13.lms.dao.subject;

import no.westerdals.lobfre13.lms.dao.event.JPASubject;
import no.westerdals.lobfre13.lms.dao.interceptors.DAOInterceptor;
import no.westerdals.lobfre13.lms.dto.Subject;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Fredrik on 24.11.2015.
 */
@JPASubject
@Stateless
@Interceptors(DAOInterceptor.class)
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
    public List<Subject> getAll() {
        return entityManager.createNamedQuery("Subject.getAll", Subject.class).getResultList();
    }

    public void close(){
        entityManager.close();
    }
}
