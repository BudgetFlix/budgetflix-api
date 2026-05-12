package hu.budgetflix.api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "video.upload.queue";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }
}
