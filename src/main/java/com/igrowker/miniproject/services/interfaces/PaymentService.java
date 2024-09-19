package com.igrowker.miniproject.services.interfaces;

import com.igrowker.miniproject.dtos.PaymentDto;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface PaymentService {

    public PaymentDto savePayment(PaymentDto paymentDto, HttpHeaders headers);

    public List<PaymentDto> getPaymentByUserId(HttpHeaders headers);

}
