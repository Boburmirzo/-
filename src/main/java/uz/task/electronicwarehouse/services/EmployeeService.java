package uz.task.electronicwarehouse.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import uz.task.electronicwarehouse.dom.command.EmployeeForm;
import uz.task.electronicwarehouse.dom.command.PasswordForm;

import java.util.NoSuchElementException;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface EmployeeService extends CRUDService<EmployeeForm, String>{
    boolean isIdInUse(EmployeeForm employeeForm);
    UserDetails findUserDetails(String query) throws NoSuchElementException;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    void resetPassword(String id);

    @PreAuthorize("isFullyAuthenticated()")
    void changePassword(PasswordForm passwordForm, UserDetails userDetails);
}
