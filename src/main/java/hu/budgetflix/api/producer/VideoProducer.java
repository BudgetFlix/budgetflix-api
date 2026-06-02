package hu.budgetflix.api.producer;


import hu.budgetflix.api.model.dto.message.MediaMessage;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VideoProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue uploadQueue;

    public VideoProducer(RabbitTemplate rabbitTemplate, Queue uploadQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.uploadQueue = uploadQueue;
    }

    public void send(MediaMessage message) {
        rabbitTemplate.convertAndSend(
                uploadQueue.getName(), message
        );
    }
}
