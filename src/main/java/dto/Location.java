package dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Fredrik on 23.11.2015.
 */
@SequenceGenerator(name = "seq", initialValue = 50)
@Entity
@NamedQuery(name = "Location.getAll", query = "SELECT l FROM Location l")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"room", "building"}, name = "ROOM_BUILDING_UNIQUE"))
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;
    @NotNull
    @NotBlank(message = "{no.westerdals.lobfre13.lms.Location.room.message}")

    private String room;
    @NotNull
    @NotBlank(message = "{no.westerdals.lobfre13.lms.Location.building.message}")
    private String building;

    public Location() {
    }

    public Location(String room, String building) {
        this.room = room;
        this.building = building;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return id == location.id;
    }
}
