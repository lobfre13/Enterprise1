package no.westerdals.lobfre13.lms.dao.user;

import no.westerdals.lobfre13.lms.dto.User;

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
