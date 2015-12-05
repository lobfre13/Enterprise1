package no.westerdals.lobfre13.lms.dao.subject;

import no.westerdals.lobfre13.lms.dto.Subject;

import java.util.List;

/**
 * Created by Fredrik on 24.11.2015.
 */
public interface SubjectDao {
    Subject persist(Subject subject);
    Subject update(Subject subject);
    Subject getSubject(int id);
    List<Subject> getAll();
}
