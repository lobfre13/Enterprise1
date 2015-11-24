package dao.subject;

import dto.Subject;
import dto.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Fredrik on 24.11.2015.
 */
public class EntitySubjectDao implements SubjectDao {
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

    public void close(){
        entityManager.close();
    }
}
