package hu.budgetflix.api.consumer;

import hu.budgetflix.api.model.dto.message.MediaRetryMsg;
import hu.budgetflix.api.service.UploadService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VideoUploaderConsumer {
    private static final Logger TRAFFIC_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic");
    private static final Logger FORMAT_LOG = LoggerFactory.getLogger("hu.budgetflix.api.traffic.format");

    private final UploadService uploadService;

    public VideoUploaderConsumer(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @RabbitListener(queues = "#{uploadRetryQueue.name}")
    public void consume(MediaRetryMsg msg){
        TRAFFIC_LOG.info("RabbitMQ message received from retry queue");
        FORMAT_LOG.debug("RabbitMQ inbound message format: type={}, payload={}",
                msg == null ? "null" : msg.getClass().getName(), msg);
        if(msg == null) {
            throw new RuntimeException("the retry message is null");
        }
        uploadService.handleRetryMovieMsg(msg);
    }
}
