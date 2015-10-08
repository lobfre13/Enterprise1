import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */
@Alternative
public class InMemoryUserDB implements UserDAO {
    private List<User> users;
    private int nextId;

    public InMemoryUserDB(){
        users = new ArrayList<>();
        nextId = 1;
    }

    public boolean addUser(User user) {
        if(user.getId() == 0) user.setId(nextId++);
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
