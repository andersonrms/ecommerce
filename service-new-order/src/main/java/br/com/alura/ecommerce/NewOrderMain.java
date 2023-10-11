package br.com.alura.ecommerce;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {

                for (int i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();

                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var userEmail = Math.random() + "@gmail.com";
                    var order = new Order(userId, orderId, amount, userEmail);

                    orderDispatcher.send(Constants.ECOMMERCE_NEW_ORDER_SUBJECT, userId, order);


                    var emailSubject = "Thank by buy with us! We are processing your order";
                    var email = new Email(userId, userEmail, emailSubject);
                    emailDispatcher.send(Constants.ECOMMERCE_SEND_EMAIL_SUBJECT, userId, email);
                }
            }
        }
    }
}
