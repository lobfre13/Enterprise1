/**
 * Created by Fredrik on 17.09.2015.
 */
public class User {
    public enum Type {SUDENT, TEACHER}
    private int id;
    private String email;
    private String password;
    private Type type;

    public User(){
        this(0, null, null, Type.SUDENT);
    }

    public User(int id, String email, String password, Type type){
        setId(id);
        setEmail(email);
        setPassword(password);
        setType(type);
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


}
