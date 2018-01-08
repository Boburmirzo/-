package uz.task.electronicwarehouse.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.task.electronicwarehouse.services.EmployeeService;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        Optional<UserDetails> userDetails = Optional.ofNullable(employeeService.findUserDetails(employeeId));
        if (!userDetails.isPresent()) {
            throw new UsernameNotFoundException("This ID does not exist");
        }
        return userDetails.get();
    }

}
