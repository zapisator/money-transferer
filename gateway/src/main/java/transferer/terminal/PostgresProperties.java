package transferer.terminal;

import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "postgres")
@Data
public class PostgresProperties {
    String host;
    String port;
    String database;
    String username;
    String password;
    String driverClassName;
    String url;
    Pool pool;

    @Data
    public static class Pool {
        String name;
        int initialSize;
        int mapSize;
        Duration maxIdleTime;
    }

}
