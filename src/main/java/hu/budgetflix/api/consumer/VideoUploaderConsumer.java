package hu.budgetflix.api.consumer;

import hu.budgetflix.api.model.dto.message.MediaRetryMsg;
import hu.budgetflix.api.service.UploadService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class VideoUploaderConsumer {

    private final UploadService uploadService;

    public VideoUploaderConsumer(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @RabbitListener(queues = "#{uploadRetryQueue.name}")
    public void consume(MediaRetryMsg msg){
        if(msg == null) {
            throw new RuntimeException("the retry message is null");
        }
        uploadService.handleRetryMovieMsg(msg);
    }
}
