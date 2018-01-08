package uz.task.electronicwarehouse.dom.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uz.task.electronicwarehouse.dom.command.ShipmentForm;
import uz.task.electronicwarehouse.dom.entities.Shipment;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Component
public class ShipmentConverter implements Converter<Shipment, ShipmentForm> {

    @Override
    public ShipmentForm convert(Shipment shipment) {
        ShipmentForm shipmentForm = new ShipmentForm();
        shipmentForm.setShipmentId(shipment.getShipmentId());
        shipmentForm.setCreatedOn(shipment.getCreatedOn());
        shipmentForm.setUpdatedOn(shipment.getUpdatedOn());
        shipmentForm.setProductLots(shipment.getProductLots());
        shipmentForm.setShipmentType(shipment.getShipmentType());
        shipmentForm.setVersion(shipment.getVersion());
        shipmentForm.setIsNewShipment(false);

        return shipmentForm;
    }

    public Shipment convert(ShipmentForm shipmentForm) {
        Shipment shipment = new Shipment();
        // dates will be set on the way into the database
        // database id will be checked for on the way in
        shipment.setProductLots(shipmentForm.getProductLots());
        shipment.setShipmentId(shipmentForm.getShipmentId());
        shipment.setShipmentType(shipmentForm.getShipmentType());
        shipment.setVersion(shipmentForm.getVersion());
        if (shipment.getVersion() == null) {
            shipment.setVersion(0);
        }

        return shipment;
    }
}
