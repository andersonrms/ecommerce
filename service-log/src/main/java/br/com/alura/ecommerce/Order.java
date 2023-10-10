package br.com.alura.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class Order {
    private final String userId;
    private final String orderId;
    private final BigDecimal amount;

    public String toString() {
        return "{ userId: " + this.userId + " - orderId: " + this.orderId + " - amount: " + this.amount + "}";
    }
}
