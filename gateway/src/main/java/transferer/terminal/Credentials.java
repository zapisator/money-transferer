package transferer.terminal;

import java.time.LocalDate;
import lombok.Value;

@Value
public class Credentials {

    String user;
    String email;
    String password;
    LocalDate birthDate;
    String country;

}
