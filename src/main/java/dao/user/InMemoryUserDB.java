package dao.user;

import dto.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */

public class InMemoryUserDB implements UserDAO {
    private List<User> users;
    private int nextId;

    public InMemoryUserDB(){
        users = new ArrayList<>();
        nextId = 1;
    }

    public User addUser(User user) {
        if(user.getId() == 0) user.setId(nextId++);
        users.add(user);
        return user;
    }

    public User updateUser(User user) {
        if(deleteUser(user)) users.add(user);
        return user;
    }

    public User getUser(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().get();
        /*for(dto.User user : users){
            if(user.getId() == id) return user;
        }
        return null;*/
    }

    public List<User> getAllUsers() {
        return users;
    }

    public boolean deleteUser(User user) {
        return users.remove(user);
    }
}
