package controller;

import dao.subject.SubjectDao;
import dto.Subject;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Fredrik on 26.11.2015.
 */
@Model
public class SubjectController {
    private Subject subject;
    @Inject
    private SubjectDao subjectDao;

    @PostConstruct
    public void construct(){
        subject = new Subject();
    }

    public void persistSubject(){
        subjectDao.persist(subject);
    }

    public List<Subject> getAll(){
        return subjectDao.getAll();
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
