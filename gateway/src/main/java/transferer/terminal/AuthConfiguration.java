package transferer.terminal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfiguration {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Auth auth(PasswordEncoder encoder) {
        return new Auth(encoder);
    }

    @Bean
    public Runner runner(Auth auth) {
        return new Runner(auth);
    }

}
