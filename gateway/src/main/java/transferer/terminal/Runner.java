package transferer.terminal;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    final Auth auth;

    @Override
    public void run(String... args) {

        boolean isRegistred = auth.login();
        if (isRegistred) {
            auth.signIn();
        } else {
            auth.signUp();
        }
    }
}
