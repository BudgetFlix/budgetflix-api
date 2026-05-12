package hu.budgetflix.api.producer;

import hu.budgetflix.api.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VideoProducer {
    private final RabbitTemplate rabbitTemplate;

    public VideoProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send (String msg) {
         rabbitTemplate.convertAndSend(
                 RabbitConfig.QUEUE,msg
         );
         System.out.println("sent: " + msg);
    }
}
