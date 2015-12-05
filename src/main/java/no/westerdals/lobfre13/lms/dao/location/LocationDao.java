package no.westerdals.lobfre13.lms.dao.location;

import no.westerdals.lobfre13.lms.dto.Location;

import java.util.List;

/**
 * Created by Fredrik on 23.11.2015.
 */
public interface LocationDao {
    Location persist(Location location);
    Location getLocation(int id);
    List<Location> gelAll();
    Location update(Location location);
    boolean delete(Location location);
}
