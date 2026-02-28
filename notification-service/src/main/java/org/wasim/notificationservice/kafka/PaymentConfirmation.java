package org.wasim.notificationservice.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentConfirmation {
    private String orderId;
    private boolean isSuccess;
    private String email;
}
