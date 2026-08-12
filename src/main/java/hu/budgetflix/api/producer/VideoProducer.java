package hu.budgetflix.api.producer;


import hu.budgetflix.api.model.dto.message.MediaMessage;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VideoProducer {
    private static final Logger TRAFFIC_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic");
    private static final Logger FORMAT_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic.format");
    private final RabbitTemplate rabbitTemplate;
    private final Queue uploadQueue;

    public VideoProducer(RabbitTemplate rabbitTemplate, Queue uploadQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.uploadQueue = uploadQueue;
    }

    public void send(MediaMessage message) {
        TRAFFIC_LOG.info("RabbitMQ message sent to queue '{}'", uploadQueue.getName());
        FORMAT_LOG.debug("RabbitMQ outbound message format: queue={}, type={}, payload={}", uploadQueue.getName(),
                message == null ? "null" : message.getClass().getName(), message);
        rabbitTemplate.convertAndSend(
                uploadQueue.getName(), message
        );
    }
}
