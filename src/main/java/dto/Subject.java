package dto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Fredrik on 23.11.2015.
 */
@Entity
@SequenceGenerator(name = "seq", initialValue = 50)
@NamedQuery(name = "Subject.getAll", query = "SELECT s from Subject s")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;
    @NotNull
    private String name;

    @Size(min = 0, max = 100)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USR_SUB")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "FK_LOCATION")
    @Valid
    private Location location;

    public Subject(int id, String name, List<User> users, Location location) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.location = location;
    }

    public Subject() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
