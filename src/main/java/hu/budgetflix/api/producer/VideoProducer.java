package hu.budgetflix.api.producer;

import hu.budgetflix.api.config.RabbitConfig;
import hu.budgetflix.api.model.dto.message.MediaMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VideoProducer {
    private final RabbitTemplate rabbitTemplate;

    public VideoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(MediaMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.QUEUE, message
        );
    }
}
