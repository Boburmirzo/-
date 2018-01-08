package uz.task.electronicwarehouse.dom.entities;

import uz.task.electronicwarehouse.dom.enums.ShipmentType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Entity
public class Shipment extends AbstractEntityObject {

    private Integer shipmentId;
    private Integer version = 0;

    @Enumerated(EnumType.STRING)
    private ShipmentType shipmentType;

    @ElementCollection
    @CollectionTable(name = "PRODUCT_LOT", joinColumns = @JoinColumn(name = "shipment_id"))
    private Set<ProductLot> productLots = new HashSet<>();

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Set<ProductLot> getProductLots() {
        return productLots;
    }

    public void setProductLots(Set<ProductLot> productLots) {
        this.productLots = productLots;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public void updateTimeStamps() {
        super.updateTimeStamps();
        getProductLots().forEach(productLot -> {
            productLot.updateTimeStamps();
            productLot.setShipmentType(shipmentType);
        });
    }

    @Override
    public String toString() {
        return String.format("##########%nShipmentID: %s%nVersion: %s%nDatabase ID: %s%n##########%n",
                getShipmentId(),
                getVersion(),
                getDatabaseId());
    }
}
