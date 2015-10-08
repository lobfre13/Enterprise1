/**
 * Created by Fredrik on 17.09.2015.
 */
public class User {
    public enum Role {STUDENT, TEACHER}
    private int id;
    private String email;
    private String password;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.getId();

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
