import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class ComputeService {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers","kafkaserver.devops.mk:9092");
        props.put("group.id","group1");
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms","1000");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer",StringDeserializer.class.getName());

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(props);

        consumer.subscribe(Arrays.asList("compute"));

        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for(ConsumerRecord<String,String> record : records){
                String message = record.value();
                String [] split = message.split(";");
                System.out.println("Primeno e baranje za virtuelka:");
                System.out.printf("Tip: %s so memorija od %s MB i %s procesorski jadra. %nPotrebno vreme za izvrsuvanje od %s sekundi.%n%n", split[0], split[1], split[2], split[3]);

                try {
                    Thread.sleep(Long.parseLong(split[3])*1000);
                    System.out.printf("Baranjeto e izvrseno!%nCekam baranja za virtuelki!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
