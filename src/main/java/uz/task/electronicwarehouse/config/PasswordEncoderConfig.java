package uz.task.electronicwarehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Configuration
public class PasswordEncoderConfig {
    // this is in a separate file to prevent cyclical dependencies
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
