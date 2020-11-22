package ro.tuc.ds2020rabbitmq;



import com.google.gson.JsonObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;

    private FileRepository fileRepository = new FileRepository();


    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "myNameIsJeff", "Hello from RabbitMQ!");
//
//        for(int i=0;;i++){
//            System.out.println("Message " + i);
//            rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "myNameIsJeff", "Message " + i);
//            Thread.sleep(1000);
//        }

        fileRepository.fileReader("activity.txt").stream().forEach(line->{
            try {
                String[] line1 = line.split("\t\t");

                long millisStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(line1[0]).getTime();
                long millisEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(line1[1]).getTime();


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("patient_id", "5c2494a3-1140-4c7a-991a-a1a2561c6bc2");
                jsonObject.addProperty("start", millisStart);
                jsonObject.addProperty("end", millisEnd);
                jsonObject.addProperty("activity", line1[2].replaceAll("\\s+",""));

                System.out.println(jsonObject);
                rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "myNameIsJeff", jsonObject.toString());

                Thread.sleep(1000);
            } catch (InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        });


    }

}