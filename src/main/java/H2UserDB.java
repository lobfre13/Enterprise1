import java.sql.*;
import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */
public class H2UserDB implements UserDAO {

    private Connection h2con() throws SQLException {
//        try {
//            Class.forName("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return DriverManager.getConnection("jdbc:h2:tcp://localhost/~/tools/server/db-files/h2-demo", "sa", "sa");

    }

    public boolean saveUser(User user) {
        try(Connection con = h2con()){
            PreparedStatement statement = con.prepareStatement("INSERT INTO users VALUES(null, ?, ?, ?)");
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getType().toString());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        return false;
    }

    public User getUser(int id) {
        return null;
    }

    public List<User> getAllUsers() {
        return null;
    }

    public boolean deleteUser(User user) {
        return false;
    }
}
