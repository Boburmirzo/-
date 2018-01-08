package uz.task.electronicwarehouse.dom.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import uz.task.electronicwarehouse.dom.command.PasswordForm;
import uz.task.electronicwarehouse.dom.validators.interfaces.PasswordFormValidator;
import uz.task.electronicwarehouse.services.security.EncryptionService;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Component
public class PasswordFormValidatorImpl implements PasswordFormValidator {
    private EncryptionService encryptionService;

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PasswordForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PasswordForm passwordForm = (PasswordForm) o;

        if (passwordForm.getCurrentPassword() == null) {
            errors.rejectValue("currentPassword", "PasswordEmpty", "Field must be filled out");
        }
        if (passwordForm.getNewPassword() == null) {
            errors.rejectValue("newPassword", "PasswordEmpty", "Field must be filled out");
        }
        if (passwordForm.getConfirmPassword() == null) {
            errors.rejectValue("confirmPassword", "PasswordEmpty", "Field must be filled out");
        }
        if (passwordForm.getNewPassword().length() < 8) {
            errors.rejectValue("newPassword", "PasswordTooShort","Password must be at least 8 characters");
        }
        if (passwordForm.getNewPassword().length() > 25) {
            errors.rejectValue("newPassword", "PasswordTooLong", "Password cannot be longer than 25 characters");
        }
        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            errors.rejectValue("newPassword", "PasswordsDoNotMatch", "Passwords must match");
        }

    }

    public void checkCurrentPassword(PasswordForm passwordForm, BindingResult bindingResult, UserDetails userDetails) {
        if (!encryptionService.matches(passwordForm.getCurrentPassword(), userDetails.getPassword())) {
            bindingResult.rejectValue("currentPassword", "IncorrectPassword", "Current password incorrect");
        }
    }
}
