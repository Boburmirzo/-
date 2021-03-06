package uz.task.electronicwarehouse.dom.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import uz.task.electronicwarehouse.dom.command.ShipmentForm;
import uz.task.electronicwarehouse.dom.entities.ProductLot;
import uz.task.electronicwarehouse.dom.enums.ProductId;
import uz.task.electronicwarehouse.dom.validators.interfaces.ShipmentFormValidator;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Component
public class ShipmentFormValidatorImpl implements ShipmentFormValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ShipmentForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ShipmentForm shipmentForm = (ShipmentForm) o;
        if (shipmentForm.getShipmentType() == null) {
            errors.rejectValue("shipmentType", "ShipmentTypeNull", "Shipment Type required");
        }
        if (shipmentForm.getPossibleProductLots().size() != ProductId.values().length) {
            errors.rejectValue("possibleProductLots",
                    "PossibleProductLotsWrongSize",
                    "Possible Product Lots includes more or less than possible products");
        }

        boolean lotsAllEmpty = true;
        for (ProductLot lot : shipmentForm.getPossibleProductLots()) {
            // if text field is submitted empty, interpret as a quantity of 0
            if (lot.getQuantity() == null) {lot.setQuantity(0);}
            if (lot.getQuantity() > 0) lotsAllEmpty = false;
            if (lot.getQuantity() < 0) {
                errors.rejectValue("possibleProductLots[" + lot.getProductId().ordinal() + "].quantity",
                        "PossibleProductLotsNegativeValue",
                        "Quantity cannot be negative");
            }

        }
        if (lotsAllEmpty) {
            errors.rejectValue("possibleProductLots",
                    "PossibleProductLotsEmpty",
                    "Shipment must contain inventories");
        }



    }
}
