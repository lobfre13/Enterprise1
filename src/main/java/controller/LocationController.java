package controller;

import dao.event.JPALocation;
import dao.event.JPASubject;
import dao.location.LocationDao;
import dao.subject.SubjectDao;
import dto.Location;

import javax.annotation.PostConstruct;
import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.util.List;

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
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "location.added");
        }
        catch (EJBTransactionRolledbackException e){
            String errorCode = getSQLErrorCodeFromException(e);
            if(errorCode.equals("DUPLICATE_KEY")) errorCode = "location.exists";
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, errorCode);
        }
    }

    public void deleteLocation(){
        location = locationDao.getLocation(selectedLocationId);
        if(location == null)return;
        try{
            subjectDao.getAll()
                    .stream()
                    .filter(subject -> location.equals(subject.getLocation()))
                    .forEach(subject -> {
                        subject.setLocation(null);
                        subjectDao.update(subject);
                    });
            locationDao.delete(location);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "location.deleted");
        } catch (EJBTransactionRolledbackException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }
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
