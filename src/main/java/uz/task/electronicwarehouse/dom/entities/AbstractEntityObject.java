package uz.task.electronicwarehouse.dom.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@MappedSuperclass
public abstract class AbstractEntityObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer databaseId;

    private Date createdOn;
    private Date updatedOn;

    public Integer getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Integer databaseId) {
        this.databaseId = databaseId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    @PrePersist
    @PreUpdate
    public void updateTimeStamps() {

        if (createdOn == null) {
            createdOn = new Date();
        }

        updatedOn = new Date();
    }
}
