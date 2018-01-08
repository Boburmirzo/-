package uz.task.electronicwarehouse.dom.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uz.task.electronicwarehouse.dom.command.ProductForm;
import uz.task.electronicwarehouse.dom.entities.Product;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Component
public class ProductConverter implements Converter<Product, ProductForm> {

    @Override
    public ProductForm convert(Product product) {
        ProductForm productForm = new ProductForm();
        productForm.setUpdatedOn(product.getUpdatedOn());
        productForm.setCreatedOn(product.getCreatedOn());
        productForm.setProductId(product.getProductId());
        productForm.setProductName(product.getProductName());
        productForm.setProductInventory(product.getProductInventory());
        productForm.setVersion(product.getVersion());

        return productForm;
    }

    public Product convert(ProductForm productForm) {
        Product product = new Product();
        // dates will be set on the way into the database
        // database id will be obtained from db
        product.setProductId(productForm.getProductId());
        product.setProductName(productForm.getProductName());
        product.setVersion(productForm.getVersion());


        return product;
    }
}
