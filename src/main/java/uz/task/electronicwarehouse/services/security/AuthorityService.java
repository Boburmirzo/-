package uz.task.electronicwarehouse.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import uz.task.electronicwarehouse.dom.command.EmployeeForm;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface AuthorityService {
    boolean hasAuthorityToEditEmployee(EmployeeForm employeeForm, UserDetails userDetails);
}
