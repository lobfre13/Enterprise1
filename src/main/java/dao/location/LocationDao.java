package dao.location;

import dto.Location;

/**
 * Created by Fredrik on 23.11.2015.
 */
public interface LocationDao {
    Location persist(Location location);
    Location getLocation(int id);
}
