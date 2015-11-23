import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Fredrik on 17.09.2015.
 */

@Entity
@NamedQuery(name = "User.getAll", query = "select u from User u")
public class User {
    public enum Role {STUDENT, TEACHER}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+$")
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
