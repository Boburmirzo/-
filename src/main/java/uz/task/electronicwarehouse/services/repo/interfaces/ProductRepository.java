package uz.task.electronicwarehouse.services.repo.interfaces;

        import org.springframework.data.repository.CrudRepository;
        import uz.task.electronicwarehouse.dom.entities.Product;
        import uz.task.electronicwarehouse.dom.enums.ProductId;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByProductId(ProductId productId);
}
