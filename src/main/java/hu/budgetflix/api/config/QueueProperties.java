package hu.budgetflix.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@Setter
@ConfigurationProperties(prefix = "queues")
public class QueueProperties {

    private String upload;
    private String uploadRetry;


}
