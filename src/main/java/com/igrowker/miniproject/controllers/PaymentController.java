package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.PaymentDto;
import com.igrowker.miniproject.services.interfaces.PaymentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto paymentDto, @RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(paymentService.savePayment(paymentDto, headers), HttpStatus.CREATED);
    }

}
