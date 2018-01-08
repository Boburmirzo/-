package uz.task.electronicwarehouse.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Service
public class EncryptionServiceImpl implements EncryptionService {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }
}
