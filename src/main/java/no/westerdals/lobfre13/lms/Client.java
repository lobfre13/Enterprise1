package no.westerdals.lobfre13.lms;

import no.westerdals.lobfre13.lms.dao.user.JPAUser;
import no.westerdals.lobfre13.lms.dao.user.UserDAO;
import no.westerdals.lobfre13.lms.dto.User;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by Fredrik on 08.10.2015.
 */
public class Client {

    private EntityManager entityManager;

    static{
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }
    @Inject @JPAUser
    private UserDAO userDAO;

    public void test(){
        userDAO.addUser(new User());
        System.out.println("exit");
    }

    public static void main(String[] args){
        WeldContainer container = new Weld().initialize();
        Instance<Client> service = container.instance().select(Client.class);
        service.get().test();
    }
}