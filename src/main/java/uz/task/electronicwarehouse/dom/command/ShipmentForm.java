package uz.task.electronicwarehouse.dom.command;

import uz.task.electronicwarehouse.dom.entities.ProductLot;
import uz.task.electronicwarehouse.dom.enums.ProductId;
import uz.task.electronicwarehouse.dom.enums.ShipmentType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public class ShipmentForm extends AbstractCommandObject{
    private Integer shipmentId;
    private Set<ProductLot> productLots = new HashSet<>();
    private ShipmentType shipmentType;
    private List<ProductLot> possibleProductLots = new ArrayList<>();
    private boolean isNewShipment = true;

    public ShipmentForm() {
        populatePossibleProductLots();
    }

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

    public List<ProductLot> getPossibleProductLots() {
        return possibleProductLots;
    }

    private void populatePossibleProductLots() {
        final int quantity = 0;
        for (ProductId productId : ProductId.values()) {
            possibleProductLots.add(new ProductLot(productId, quantity));
        }
    }

    public boolean getIsNewShipment() {
        return isNewShipment;
    }

    public void setIsNewShipment(boolean newShipment) {
        this.isNewShipment = newShipment;
    }
}
