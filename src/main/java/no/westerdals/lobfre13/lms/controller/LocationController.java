package no.westerdals.lobfre13.lms.controller;

import no.westerdals.lobfre13.lms.dao.event.JPALocation;
import no.westerdals.lobfre13.lms.dao.event.JPASubject;
import no.westerdals.lobfre13.lms.dao.location.LocationDao;
import no.westerdals.lobfre13.lms.dao.subject.SubjectDao;
import no.westerdals.lobfre13.lms.dto.Location;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
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
    private boolean deleteRequested;

    @PostConstruct
    public void construct(){
        location = new Location();
        initDeleteParam();
        if(deleteRequested){
            initLocation();
            deleteLocation();
        }
    }

    public void persistLocation(){
        try {
            locationDao.persist(location);
            addFacesMessageFromKey(FacesMessage.SEVERITY_INFO, "location.added");
        }
        catch (EJBTransactionRolledbackException e){
            String errorCode = getSQLErrorCodeFromException(e);
            if("DUPLICATE_KEY".equals(errorCode)) errorCode = "location.exists";
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, errorCode);
        }
    }

    public void deleteLocation(){
        if(location == null) return;
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
        } catch (EJBException e){ //to capture from all three dao calls
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }
    }

    public void initLocation(){
        try{
            location = locationDao.getLocation(selectedLocationId);
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
        }
    }

    private void initDeleteParam(){
        String id = facesContext.getExternalContext().getRequestParameterMap().get("del");
        try{
            int i = Integer.parseInt(id);
            setSelectedLocationId(i);
            deleteRequested = true;
        } catch (NumberFormatException e){
            deleteRequested = false;
        }
    }

    public List<Location> getAll(){
        try{
            return locationDao.getAll();
        } catch (EJBException e){
            addFacesMessageFromKey(FacesMessage.SEVERITY_ERROR, "error.unknown");
            return null;
        }
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
