package controller;

import dao.event.JPALocation;
import dao.location.LocationDao;
import dto.Location;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Fredrik on 26.11.2015.
 */
@Model
public class LocationController {
    private Location location;
    @Inject @JPALocation
    private LocationDao locationDao;

    public void persistLocation(){
        locationDao.persist(location);
    }

    @PostConstruct
    public void construct(){
        location = new Location();
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
}
