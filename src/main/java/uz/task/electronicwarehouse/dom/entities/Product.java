package uz.task.electronicwarehouse.dom.entities;

import uz.task.electronicwarehouse.dom.entities.exceptions.NegativeInventoryException;
import uz.task.electronicwarehouse.dom.enums.ProductId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Version;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Entity
public class Product extends AbstractEntityObject {

    @Enumerated(EnumType.STRING)
    private ProductId productId;
    private String productName;
    private Integer productInventory = 0;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public void adjustInventory(Integer quantity) throws NegativeInventoryException {
        if (this.getProductInventory() + quantity < 0) {
            throw new NegativeInventoryException("Inventory cannot be negative");
        }
        this.productInventory += quantity;
    }
}
