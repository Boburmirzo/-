package uz.task.electronicwarehouse.services.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.task.electronicwarehouse.dom.command.ShipmentForm;
import uz.task.electronicwarehouse.dom.converters.ShipmentConverter;
import uz.task.electronicwarehouse.dom.entities.ProductLot;
import uz.task.electronicwarehouse.dom.entities.Shipment;
import uz.task.electronicwarehouse.dom.entities.exceptions.NegativeInventoryException;
import uz.task.electronicwarehouse.dom.enums.ProductId;
import uz.task.electronicwarehouse.services.ProductService;
import uz.task.electronicwarehouse.services.ShipmentService;
import uz.task.electronicwarehouse.services.repo.interfaces.ShipmentRepository;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Service
@Profile({"repo", "default"})
public class ShipmentRepoServiceImpl implements ShipmentService {
    private ShipmentRepository shipmentRepository;
    private ShipmentConverter shipmentConverter;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setShipmentConverter(ShipmentConverter shipmentConverter) {
        this.shipmentConverter = shipmentConverter;
    }

    @Autowired
    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Map<Integer, ShipmentForm> listAll() {
        Map<Integer, ShipmentForm> shipmentFormMap = new HashMap<>();
        shipmentRepository.findAll().forEach(
                shipment -> shipmentFormMap.put(shipment.getShipmentId(), shipmentConverter.convert(shipment)));

        return shipmentFormMap;
    }


    @Override
    public TreeMap<Integer, ProductLot> listProductLotsByShipmentId(ProductId productId) {
        TreeMap<Integer, ProductLot> shipmentFormTreeMap = new TreeMap<>();

        listAll().forEach((shipmentId, shipmentForm) -> shipmentForm.getProductLots().forEach(productLot -> {
            if (productLot.getProductId().equals(productId)) {
                shipmentFormTreeMap.put(shipmentId, productLot);
            }
        }));

        return shipmentFormTreeMap;
    }

    @Override
    public ShipmentForm findOne(Integer shipmentId) throws NoSuchElementException {
        Shipment shipment = shipmentRepository.findByShipmentId(shipmentId);
        if (shipment == null) throw new NoSuchElementException("shipmentId " + shipmentId + " not found");
        return shipmentConverter.convert(shipment);
    }

    @Transactional
    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) throws NegativeInventoryException {

        boolean isNewShipment = shipmentForm.getIsNewShipment();

        Shipment currentShipment = shipmentConverter.convert(shipmentForm);
        Integer incomingVersion = currentShipment.getVersion();

        Set<ProductLot> currentLots = new HashSet<>();
        currentLots.addAll(shipmentForm.getPossibleProductLots());
        currentShipment.setProductLots(currentLots);

        Shipment priorShipment = null;
        Integer shipmentId = null;
        if (!shipmentForm.getIsNewShipment()) {
            shipmentId = shipmentForm.getShipmentId();
            priorShipment = shipmentRepository.findByShipmentId(shipmentId);
            currentShipment.setDatabaseId(priorShipment.getDatabaseId());
        }

        processShipmentProductLots(isNewShipment, currentShipment, priorShipment, currentLots, productService);

        if (!isNewShipment) {
            if (!shipmentRepository.findByShipmentId(shipmentId).getVersion().equals(incomingVersion)) {
                throw new OptimisticLockException("Incoming shipment version no longer matches database shipment version");
            }
        }

        currentShipment.setVersion(currentShipment.getVersion() + 1);
        return shipmentConverter.convert(shipmentRepository.save(currentShipment));
    }

    @Override
    public void delete(Integer shipmentId) {
        int databaseId = shipmentRepository.findByShipmentId(shipmentId).getDatabaseId();
        shipmentRepository.delete(databaseId);
    }

    @Override
    public void clearInventoryByShipmentId(Integer shipmentId) throws NegativeInventoryException {
        Shipment shipment = shipmentRepository.findByShipmentId(shipmentId);
        ShipmentForm shipmentForm = shipmentConverter.convert(shipment);

        saveOrUpdate(shipmentForm);
    }
}
