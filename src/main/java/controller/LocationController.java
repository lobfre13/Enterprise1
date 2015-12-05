package controller;

import dao.event.JPALocation;
import dao.event.JPASubject;
import dao.location.LocationDao;
import dao.subject.SubjectDao;
import dto.Location;
import dto.Subject;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Fredrik on 26.11.2015.
 */
@Model
public class LocationController extends BaseController {
    private Location location;
    @Inject @JPALocation
    private LocationDao locationDao;
    @Inject @JPASubject
    private SubjectDao subjectDao;
    private int selectedLocationId;

    @PostConstruct
    public void construct(){
        location = new Location();
    }

    public void persistLocation(){
        try {
            locationDao.persist(location);
        }
        catch (EJBTransactionRolledbackException e){
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getErrorCode(e), null));
//            addFacesMessage(FacesMessage.SEVERITY_ERROR, "location.exists");
            return;
        }
        addFacesMessage(FacesMessage.SEVERITY_INFO, "location.added");
    }

    protected String getErrorCode(RuntimeException e) {
        Throwable throwable = e;
        while(throwable != null && !(throwable instanceof SQLException)) {
            throwable = throwable.getCause();
        }
        if (throwable == null) return "none";

        //Properties properties = --> load sql error code form configuration file.
        SQLException sqlex = (SQLException) throwable;

        return sqlex.getErrorCode() + "";


    }

    public void deleteLocation(){
        location = locationDao.getLocation(selectedLocationId);
        if(location == null)return;
        subjectDao.getAll()
                .stream()
                .filter(subject -> location.equals(subject.getLocation()))
                .forEach(subject -> {
                    subject.setLocation(null);
                    subjectDao.update(subject);
                });

        locationDao.delete(location);
    }


    public List<Location> getAll(){
        return locationDao.gelAll();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getSelectedLocationId() {
        return selectedLocationId;
    }

    public void setSelectedLocationId(int selectedLocationId) {
        this.selectedLocationId = selectedLocationId;
    }
}
