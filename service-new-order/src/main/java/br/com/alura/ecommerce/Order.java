package br.com.alura.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Order {
    private final String userId;
    private final String orderId;
    private final BigDecimal amount;
    private final String email;

    @Override
    public String toString() {
        return "Order {" +
                "userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                ", email=" + email +
                '}';
    }
}
