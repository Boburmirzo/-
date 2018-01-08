package uz.task.electronicwarehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.task.electronicwarehouse.dom.enums.ProductId;
import uz.task.electronicwarehouse.services.ProductService;
import uz.task.electronicwarehouse.services.ShipmentService;

import java.util.TreeMap;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private ShipmentService shipmentService;

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Autowired
    public void setProductService(ProductService productService) { this.productService = productService; }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/")
    public String root() {
        return "redirect:/product/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("products", new TreeMap<>(productService.listAll()));
        return "product/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/show/{id}")
    public String showOne(@PathVariable ProductId id, Model model) {
        model.addAttribute("product", productService.findOne(id));
        model.addAttribute("lots", shipmentService.listProductLotsByShipmentId(id));

        return "product/show";
    }
}
