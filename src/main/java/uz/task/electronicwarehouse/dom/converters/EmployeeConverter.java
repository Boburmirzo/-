package uz.task.electronicwarehouse.dom.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import uz.task.electronicwarehouse.dom.command.EmployeeForm;
import uz.task.electronicwarehouse.dom.entities.Employee;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Component
public class EmployeeConverter implements Converter<Employee, EmployeeForm> {

    @Override
    public EmployeeForm convert(Employee employee) {
        EmployeeForm employeeForm = new EmployeeForm();
        employeeForm.setLastName(employee.getLastName());
        employeeForm.setFirstName(employee.getFirstName());
        employeeForm.setEmployeeId(employee.getEmployeeId());
        employeeForm.setCreatedOn(employee.getCreatedOn());
        employeeForm.setUpdatedOn(employee.getUpdatedOn());
        employeeForm.setVersion(employee.getVersion());
        employeeForm.setAddress(employee.getAddress());
        employeeForm.setNewEmployee(false);
        employeeForm.setPassword(employee.getPassword());
        employeeForm.setRole(employee.getRole().toString());

        return employeeForm;
    }

    public Employee convert(EmployeeForm employeeForm) {
        Employee employee = new Employee();
        // dates will be set on the way into the database
        // database id will be obtained from db
        employee.setLastName(employeeForm.getLastName());
        employee.setFirstName(employeeForm.getFirstName());
        employee.setEmployeeId(employeeForm.getEmployeeId().toUpperCase());
        employee.setAddress(employeeForm.getAddress());
        employee.setVersion(employeeForm.getVersion());
        employee.setPassword(employeeForm.getPassword());
        if (employeeForm.isNewEmployee()) {
            employee.setRole(employeeForm.getRole());
        }

        return employee;
    }

}
