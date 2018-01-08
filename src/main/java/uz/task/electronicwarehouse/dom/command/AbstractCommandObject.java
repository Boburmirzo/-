package uz.task.electronicwarehouse.dom.command;

import java.util.Date;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public abstract class AbstractCommandObject {

    private Integer version;
    private Date createdOn;
    private Date updatedOn;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
