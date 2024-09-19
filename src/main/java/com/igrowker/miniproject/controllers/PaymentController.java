package com.igrowker.miniproject.controllers;

import com.igrowker.miniproject.dtos.PaymentDto;
import com.igrowker.miniproject.services.interfaces.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payments", description = "Payments API")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @Operation(summary = "Allows the user to save a payment", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto paymentDto, @RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(paymentService.savePayment(paymentDto, headers), HttpStatus.CREATED);
    }

    @Operation(summary = "allows user to view payment history", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/history")
    public ResponseEntity<List<PaymentDto>> getPaymentByUserId(@RequestHeader HttpHeaders headers){
        return new ResponseEntity<>(paymentService.getPaymentByUserId(headers), HttpStatus.OK);
    }

}
