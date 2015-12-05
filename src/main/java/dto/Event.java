package dto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Fredrik on 03.12.2015.
 */
@Entity
@SecondaryTable(name = "EVENT_DETAILS")
@SequenceGenerator(name = "seq", initialValue = 50)
@NamedQuery(name = "Event.getAll", query = "SELECT e FROM Event e order by e.startTime DESC")
public class Event {
    public enum Type {LECTURE, WORKSHOP}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "EVENT_TYPE")
    private Type type;
    @NotNull
    @Size(min = 5, max = 25, message = "{no.westerdals.lobfre13.lms.Event.title.message}")
    private String title;
    @Size(max = 100, message = "{no.westerdals.lobfre13.lms.Event.description.message}")
    private String description;

    @ManyToOne
    @JoinColumn(name = "FK_SUBJECT")
    @NotNull
    @Valid
    private Subject subject;

    @Column(table = "EVENT_DETAILS")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "{no.westerdals.lobfre13.lms.Event.startTime}")
    private Date startTime;
    @Column(table = "EVENT_DETAILS")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "{no.westerdals.lobfre13.lms.Event.endTime}")
    private Date endTime;

    public Event() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
