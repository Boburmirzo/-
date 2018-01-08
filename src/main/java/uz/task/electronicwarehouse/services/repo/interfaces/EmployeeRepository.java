package uz.task.electronicwarehouse.services.repo.interfaces;

import org.springframework.data.repository.CrudRepository;
import uz.task.electronicwarehouse.dom.entities.Employee;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findByEmployeeId(String employeeId);
}
