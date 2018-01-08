package uz.task.electronicwarehouse.services.security;

/**
 * Created by Boburmirzo on 06/01/18.
 */
public interface EncryptionService {
    String encryptPassword(String password);
    boolean matches(String password, String encryptedPassword);
}
