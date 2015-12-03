package dao.subject;

import dto.Subject;
import dto.User;

import java.util.List;

/**
 * Created by Fredrik on 24.11.2015.
 */
public interface SubjectDao {
    Subject persist(Subject subject);
    Subject update(Subject subject);
    Subject getSubject(int id);
    List<User> getUsers(Subject subject);
    List<Subject> getAll();
}
