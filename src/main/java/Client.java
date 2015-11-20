import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class Client {

    static{
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    @Inject
    @UserDAOQualifier
    private UserDAO userDAO;

    public void printUsers(){
        userDAO.addUser(new User(0, "lol", "luil", User.Role.STUDENT));
        userDAO.addUser(new User(0, "lol2", "luil", User.Role.STUDENT));
        userDAO.addUser(new User(0, "lol3", "luil", User.Role.STUDENT));
        //System.out.println(userDAO.getAllUsers());
        System.out.println(userDAO.getUser(2));
        User user = userDAO.getUser(2);
        user.setEmail("HAHA");
        userDAO.updateUser(user);
        System.out.println(userDAO.getUser(2));
    }

    public void lol(){
        userDAO.addUser(new User());
    }

    public static void main(String[] args){
        WeldContainer container = new Weld().initialize();
        Instance<Client> service = container.instance().select(Client.class);
        service.get().lol();
        //service.get().printUsers();
    }
}


