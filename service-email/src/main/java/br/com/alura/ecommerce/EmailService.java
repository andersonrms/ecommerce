package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService(
                Constants.ECOMMERCE_SEND_EMAIL_SUBJECT,
                emailService::parse,
                EmailService.class.getSimpleName(),
                Email.class,
                new HashMap<String, String>())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record){
        System.out.println("###############################################");
        System.out.println("PROCESSING SEND EMAIL");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // ignoring
            e.printStackTrace();
        }
        System.out.println("EMAIL SEND");
        System.out.println("###############################################");
    }
}
