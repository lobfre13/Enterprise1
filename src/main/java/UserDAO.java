import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */
public interface UserDAO {
    User addUser(User user);
    User updateUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
    boolean deleteUser(User user);
}
