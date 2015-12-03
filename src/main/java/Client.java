import dao.user.UserDAO;
import dto.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.persistence.EntityManager;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class Client {

    private EntityManager entityManager;

    static{
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }
   // @Inject
    private UserDAO userDAO;

    public void lol(){
        userDAO.addUser(new User());
        System.out.println("exit");
    }

    public static void main(String[] args){
        WeldContainer container = new Weld().initialize();
        Instance<Client> service = container.instance().select(Client.class);
        service.get().lol();
    }
}