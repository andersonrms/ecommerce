package br.com.alura.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Email {
    private final String userId;
    private final String userEmail;
    private final String emailSubject;

    @Override
    public String toString() {
        return "Email {" +
                "userId='" + userId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", emailSubject='" + emailSubject + '\'' +
                '}';
    }
}
