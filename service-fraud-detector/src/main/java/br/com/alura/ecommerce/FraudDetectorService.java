package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try (var service = new KafkaService<Order>(
                Constants.ECOMMERCE_NEW_ORDER_SUBJECT,
                fraudService::parse,
                FraudDetectorService.class.getSimpleName(),
                Order.class,
                Map.of())) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("###############################################");
        System.out.println("PROCESSING NEW ORDER, CHECKING FOR FRAUD");
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
        System.out.println("ORDER PROCESSED");
        System.out.println("###############################################");

    }
}
