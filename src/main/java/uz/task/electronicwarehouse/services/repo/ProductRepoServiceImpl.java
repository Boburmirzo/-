package uz.task.electronicwarehouse.services.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.task.electronicwarehouse.dom.command.ProductForm;
import uz.task.electronicwarehouse.dom.converters.ProductConverter;
import uz.task.electronicwarehouse.dom.entities.Product;
import uz.task.electronicwarehouse.dom.entities.exceptions.NegativeInventoryException;
import uz.task.electronicwarehouse.dom.enums.ProductId;
import uz.task.electronicwarehouse.services.ProductService;
import uz.task.electronicwarehouse.services.repo.interfaces.ProductRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Service
@Profile({"repo", "default"})
public class ProductRepoServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Map<ProductId, ProductForm> listAll() {
        Map<ProductId, ProductForm> productFormMap = new HashMap<>();

        productRepository.findAll().forEach(product ->
                productFormMap.put(product.getProductId(), productConverter.convert(product)));

        return  productFormMap;
    }

    @Override
    public ProductForm findOne(ProductId query) throws NoSuchElementException {
        return productConverter.convert(productRepository.findByProductId(query));
    }

    @Override
    public ProductForm saveOrUpdate(ProductForm productForm) {
        // dates and inventories will be trashed during conversion
        // -- for consistency of created on dates
        // -- to force employees to fix incorrect shipments rather than modifying inventory
        Product product = productConverter.convert(productForm);
        ProductId productId = product.getProductId();

        Optional<Product> oldProduct = Optional.ofNullable(productRepository.findByProductId(productId));
        // check for and add creation date and inventory
        if (oldProduct.isPresent()) {
            product.setCreatedOn(oldProduct.get().getCreatedOn());
            product.setProductInventory(oldProduct.get().getProductInventory());
            product.setDatabaseId(oldProduct.get().getDatabaseId());
        }

        return productConverter.convert(productRepository.save(product));
    }

    @Override
    public ProductForm saveOrUpdate(Product product) {
        return productConverter.convert(productRepository.save(product));
    }

    @Override
    public void updateInventory(ProductId productId, Integer quantity) throws NegativeInventoryException {
        Product product = productRepository.findByProductId(productId);
        product.adjustInventory(quantity);
//        must use saveOrUpdate(Product), if converted to ProductForm databaseId is lost and an extra record is created
        saveOrUpdate(product);
    }

    @Override
    public void delete(ProductId query) {
        productRepository.delete(productRepository.findByProductId(query));
    }
}
