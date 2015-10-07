import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */
public class InMemoryUserDB implements UserDAO {
    private List<User> users;

    public InMemoryUserDB(){
        users = new ArrayList<User>();
    }

    public boolean saveUser(User user) {
        return users.add(user);
    }

    public boolean updateUser(User user) {
        deleteUser(user);
        return users.add(user);
    }

    public User getUser(int id) {
        for(User user : users){
            if(user.getId() == id ) return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public boolean deleteUser(User user) {
        return users.remove(user);
    }
}
