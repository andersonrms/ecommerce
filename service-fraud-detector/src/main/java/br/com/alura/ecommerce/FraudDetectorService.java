package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static br.com.alura.ecommerce.Constants.ECOMMERCE_APPROVED_ORDERS;
import static br.com.alura.ecommerce.Constants.ECOMMERCE_REJECTED_ORDERS;

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

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
        System.out.println("###############################################");
        System.out.println("PROCESSING NEW ORDER, CHECKING STATUS");
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

        var order = record.value();
        var orderDispatcher = new KafkaDispatcher<Order>();

        if (isFraud(order)) {
            System.out.println("ORDER STATUS: REJECTED");
            orderDispatcher.send(ECOMMERCE_REJECTED_ORDERS, order.getUserId(), order);
        }else {
            System.out.println("ORDER STATUS: APPROVED");
            orderDispatcher.send(ECOMMERCE_APPROVED_ORDERS, order.getUserId(), order);

        }
        System.out.println("###############################################");

    }

    private static boolean isFraud(Order order) {
        // mock machine learning algorithm to detect fraud
        return order.getAmount().compareTo(new BigDecimal("4500")) >= 0;
    }
}
