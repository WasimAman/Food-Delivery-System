package org.wasim.paymentservice.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentConfirmation {
    private String orderId;
    private boolean isSuccess;
}
