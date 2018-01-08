package uz.task.electronicwarehouse.dom.validators.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import uz.task.electronicwarehouse.dom.command.PasswordForm;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface PasswordFormValidator extends Validator {
    void checkCurrentPassword(PasswordForm passwordForm, BindingResult bindingResult, UserDetails userDetails);
}
