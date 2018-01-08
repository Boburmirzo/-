package uz.task.electronicwarehouse.services;

import uz.task.electronicwarehouse.dom.command.ProductForm;
import uz.task.electronicwarehouse.dom.entities.Product;
import uz.task.electronicwarehouse.dom.entities.exceptions.NegativeInventoryException;
import uz.task.electronicwarehouse.dom.enums.ProductId;


/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface ProductService extends CRUDService<ProductForm, ProductId> {

    void updateInventory(ProductId productId, Integer quantity) throws NegativeInventoryException;

    ProductForm saveOrUpdate(Product product);

}
