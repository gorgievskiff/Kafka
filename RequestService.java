import kafka.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class RequestService {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();

        props.put("bootstrap.servers","kafkaserver.devops.mk:9092");
        props.put("acks","all");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer",StringSerializer.class.getName());

        Producer<String,String> producer = new KafkaProducer<String,String>(props);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("Vnesi tip na virtuelka (storage,compute): ");
            String tip = br.readLine();
            System.out.println("Kolicina na RAM (vo MB): ");
            String ram = br.readLine();
            System.out.println("Broj na jadra: ");
            String jadra = br.readLine();
            System.out.println("Potrebno vreme za izvrsuvanje vo sekundi: ");
            String vreme = br.readLine();

            String msg = tip + ";" + ram + ";" + jadra + ";" + vreme;

            //String key = "kluc";

            if(tip.toLowerCase().equals("storage")){
                producer.send(new ProducerRecord<String,String>("storage",null,msg));
            }else{
                producer.send(new ProducerRecord<String,String>("compute",null,msg));
            }

            //producer.send(new ProducerRecord<String,String>("nesto",null,msg));

            System.out.println("Uspesno isprateno baranje");
        }
    }
}
