import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class Client {

    @Inject
    private UserDAO userDAO;

    public void printUsers(){
        userDAO.addUser(new User(0, "lol", "luil", User.Role.STUDENT));
        userDAO.addUser(new User(0, "lol2", "luil", User.Role.STUDENT));
        userDAO.addUser(new User(0, "lol3", "luil", User.Role.STUDENT));
        System.out.println(userDAO.getAllUsers());
//        System.out.println(userDAO.getUser(1));
        User user = userDAO.getUser(1);
        user.setEmail("HAHA");
        userDAO.updateUser(user);


    }

    public static void main(String[] args){
        WeldContainer container = new Weld().initialize();
        Instance<Client> service = container.instance().select(Client.class);
        service.get().printUsers();
    }
}
