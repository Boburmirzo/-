package uz.task.electronicwarehouse.services.repo.interfaces;

import org.springframework.data.repository.CrudRepository;
import uz.task.electronicwarehouse.dom.entities.Shipment;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface ShipmentRepository extends CrudRepository<Shipment, Integer> {
    Shipment findByShipmentId(Integer shipmentId);
}
