package uz.task.electronicwarehouse.dom.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.task.electronicwarehouse.dom.entities.Employee;
import uz.task.electronicwarehouse.services.security.UserDetailsImpl;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Component
public class UserDetailsConverter implements Converter<Employee, UserDetails> {
    @Override
    public UserDetails convert(Employee employee) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        userDetails.setUsername(employee.getEmployeeId());
        userDetails.setPassword(employee.getEncryptedPassword());
        userDetails.getAuthorities().add(new SimpleGrantedAuthority("ROLE_" + employee.getRole().toString()));
        return userDetails;
    }
}
