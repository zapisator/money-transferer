package transferer.terminal;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class Auth {

    final static String EMAIL_VALIDATION_ERROR = "\nlocal-part\n"
            + "\n"
            + "    uppercase and lowercase Latin letters A to Z and a to z\n"
            + "    digits 0 to 9\n"
            + "    Allow dot (.), underscore (_) and hyphen (-)\n"
            + "    dot (.) is not the first or last character\n"
            + "    dot (.) does not appear consecutively, e.g. mkyong..yong@example.com is not allowed\n"
            + "    Max 64 characters\n"
            + "\n"
            + "domain\n"
            + "\n"
            + "    uppercase and lowercase Latin letters A to Z and a to z\n"
            + "    digits 0 to 9\n"
            + "    hyphen (-) is not the first or last character\n"
            + "    dot (.) is not the first or last character\n"
            + "    dot (.) does not appear consecutively\n"
            + "    tld min 2 characters";
    final static String PASSWORD_VALIDATION_ERROR = "Passwords do not match";

    final PasswordEncoder encoder;

    public boolean login() {
        final TextIO textIO = TextIoFactory.getTextIO();

        final Boolean isRegistredUser = textIO.newBooleanInputReader()
                .withDefaultValue(true)
                .read("Are you a registred user?");

        textIO.dispose("User is loggin in...");
        return isRegistredUser;
    }

    public Credentials signUp() {
        final int YEAR_OF_MAJORITY = 18;


        final TextIO textIO = TextIoFactory.getTextIO();

        final String user = textIO.newStringInputReader()
                .withMinLength(1)
                .withMaxLength(255)
                .read("First name");

        final String email = textIO.newStringInputReader()
                .withMaxLength(1023)
                .withValueChecker((sVal, itemName) -> {
                    if (sVal.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
                        return null;
                    }
                    return Collections.singletonList(EMAIL_VALIDATION_ERROR);
                })
                .read("Username (email)");

        final String password = textIO.newStringInputReader()
                .withMinLength(6)
                .withInputMasking(true)
                .read("Password");

        textIO.newStringInputReader()
                .withMinLength(6)
                .withInputMasking(true)
                .withValueChecker((sVal, itemName) -> {
                    if (sVal.equals(password)) {
                        return null;
                    }
                    return Collections.singletonList(PASSWORD_VALIDATION_ERROR);
                })
                .read("Repeat password");

        final int year = textIO.newIntInputReader()
                .withMinVal(1900)
                .withMaxVal(LocalDate.now().getYear() - YEAR_OF_MAJORITY)
                .read("Year of birth");

        final Month month = textIO.newEnumInputReader(Month.class)
                .read("What month were you born in?");

        final int day = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(YearMonth.of(year, month).lengthOfMonth())
                .read("Day of birth");

        final String country = textIO.newStringInputReader()
                .withDefaultValue("GB")
                .withInlinePossibleValues("GB", "NL", "GH")
                .read("Country");

        textIO.getTextTerminal().printf(
                "user %s"
                        + "\nemail %s"
                        + "\nyear/month/day %d/%d/%d"
                        + "\ncountry %s"
                        + "\n",
                user, email, year, month.getValue(), day, country );

        textIO.newStringInputReader().withMinLength(0).read("\nPress enter to terminate...");
        textIO.dispose("User '" + user + "' has singed up.");

        return new Credentials(user, encoder.encode(email), encoder.encode(password), LocalDate.of(year, month.getValue(), day), country);
    }

    public boolean signIn() {
        final TextIO textIO = TextIoFactory.getTextIO();

        final String email = textIO.newStringInputReader()
                .withMaxLength(1023)
                .withValueChecker((sVal, itemName) -> {
                    if (sVal.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
                        return null;
                    }
                    return Collections.singletonList(EMAIL_VALIDATION_ERROR);
                })
                .read("Username (email)");

        final String password = textIO.newStringInputReader()
                .withMinLength(6)
                .withInputMasking(true)
                .read("Password");

        textIO.dispose("User has signed in.");
        return true;
    }

}
