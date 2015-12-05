package dto;

import dto.constraints.Password;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */

@Entity
@NamedQuery(name = "User.getAll", query = "select u from User u")
@SequenceGenerator(name = "seq", initialValue = 50)
public class User {
    public enum Role {STUDENT, TEACHER}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+$", message = "{no.westerdals.lobfre13.lms.User.email.message}")
    @Column(unique = true)
    private String email;
    @Password
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE")
    private Role role;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Subject> subjects;


    public User(){
        this(0, null, null, Role.STUDENT);
    }

    public User(int id, String email, String password, Role role){
        setId(id);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "dto.User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }
}
