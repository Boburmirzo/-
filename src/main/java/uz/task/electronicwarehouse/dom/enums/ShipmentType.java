package uz.task.electronicwarehouse.dom.enums;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public enum ShipmentType {
    INBOUND,
    OUTBOUND;

    private final Integer changeInventoryDirection = -1;

    ShipmentType() {}

    public Integer changeInventoryDirection() {
        return changeInventoryDirection;
    }
}
