import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 17.09.2015.
 */

@UserDAOQualifier
public class H2UserDB implements UserDAO {
    private String dbUrl;
    private String username;
    private String password;

    public H2UserDB() throws SQLException, ClassNotFoundException {
        this("jdbc:h2:tcp://localhost/~/LMS", "sa", "sa");
    }
    public H2UserDB(String dbUrl, String username, String password) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }
    private Connection getCon() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public User addUser(User user) {
        String sql = "INSERT INTO users VALUES(null, ?, ?, ?)";
        try(Connection con = getCon();
            PreparedStatement statement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            if(res.next()) user.setId(res.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User updateUser(User user) {
        String sql = "UPDATE users SET email = ?, password = ?, role = ? WHERE id = ?";
        try(Connection con = getCon();
            PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUser(int id) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection con = getCon();
            PreparedStatement statement = con.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                user.setId(res.getInt("id"));
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("password"));
                user.setRole(User.Role.valueOf(res.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try(Connection con = getCon();
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet res = statement.executeQuery()){
            while(res.next()){
                User user = new User();
                user.setId(res.getInt("id"));
                user.setEmail(res.getString("email"));
                user.setPassword(res.getString("password"));
                user.setRole(User.Role.valueOf(res.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try(Connection con = getCon();
            PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
