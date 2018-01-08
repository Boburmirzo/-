package uz.task.electronicwarehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.task.electronicwarehouse.dom.command.EmployeeForm;
import uz.task.electronicwarehouse.dom.validators.interfaces.EmployeeFormValidator;
import uz.task.electronicwarehouse.services.EmployeeService;
import uz.task.electronicwarehouse.services.security.AuthorityService;

import javax.validation.Valid;
import java.util.TreeMap;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeFormValidator employeeFormValidator;
    private AuthorityService authorityService;

    @Autowired
    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setEmployeeFormValidator(EmployeeFormValidator employeeFormValidator) {
        this.employeeFormValidator = employeeFormValidator;
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String postEmployee(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid EmployeeForm employeeForm,
                               BindingResult bindingResult) {

        if (!authorityService.hasAuthorityToEditEmployee(employeeForm, userDetails)) {
            return "authority_denied";
        }

        employeeFormValidator.validate(employeeForm, bindingResult);

        if (bindingResult.hasErrors()) {
            if (!employeeForm.isNewEmployee()) {
                employeeForm.setUpdatedOn(employeeService.findOne(employeeForm.getEmployeeId()).getUpdatedOn());
            }

            return "employee/form";
        }

        try {
            employeeService.saveOrUpdate(employeeForm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/employee/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/")
    public String root() {
        return "redirect:/employee/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/all")
    public String listAll(Model model) {
        model.addAttribute("employees", new TreeMap<>(employeeService.listAll()));

        return "employee/all";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/show/{id}")
    public String showOne(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.findOne(id));

        return "employee/show";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/new")
    public String newEmployee(Model model) {
        model.addAttribute("employeeForm", EmployeeForm.createEmployeeForm());

        return "employee/form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String editEmployee(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String id, Model model) {
        EmployeeForm employeeForm = employeeService.findOne(id);

        if (!authorityService.hasAuthorityToEditEmployee(employeeForm, userDetails)) {
            return "authority_denied";
        }

        model.addAttribute("employeeForm", employeeForm);

        return "employee/form";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/password_reset/{id}")
    public String resetPassword(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String id) {
        EmployeeForm employeeForm = employeeService.findOne(id);
        if (userDetails.getUsername().equals(id)) {
            return "authority_denied";
        }
        if (!authorityService.hasAuthorityToEditEmployee(employeeForm, userDetails)) {
            return "authority_denied";
        }
        employeeService.resetPassword(id);

        return "redirect:/employee/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/remove/{id}")
    public String removeEmployee(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String id) {
        if (userDetails.getUsername().equals(id)) {
            return "authority_denied";
        }
        employeeService.delete(id);

        return "redirect:/employee/all";
    }

}
