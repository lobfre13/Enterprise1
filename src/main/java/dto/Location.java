package dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Fredrik on 23.11.2015.
 */
@SequenceGenerator(name = "seq", initialValue = 50)
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private int id;
    @NotNull
    private String room;
    @NotNull
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
}
