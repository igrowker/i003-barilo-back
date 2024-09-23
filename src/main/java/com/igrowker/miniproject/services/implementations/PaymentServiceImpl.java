package com.igrowker.miniproject.services.implementations;

import com.igrowker.miniproject.auth.service.AuthService;
import com.igrowker.miniproject.dtos.PaymentDto;
import com.igrowker.miniproject.exceptions.FieldInvalidException;
import com.igrowker.miniproject.exceptions.NotFoundException;
import com.igrowker.miniproject.mappers.PaymentMapper;
import com.igrowker.miniproject.models.Payment;
import com.igrowker.miniproject.models.User;
import com.igrowker.miniproject.repositories.PaymentRepository;
import com.igrowker.miniproject.repositories.UserRepository;
import com.igrowker.miniproject.services.interfaces.PaymentService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AuthService authService;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, AuthService authService, PaymentMapper paymentMapper,
                              UserRepository userRepository){
        this.paymentRepository = paymentRepository;
        this.authService = authService;
        this.paymentMapper = paymentMapper;
        this.userRepository = userRepository;
    }
    @Override
    public PaymentDto savePayment(PaymentDto paymentDto, HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers); //Obtener el ID del usuario logueado
        //Obtener el usuario por ID
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new NotFoundException("Usuario no encontrado!"));
        //Obtener el saldo pendiente del usuario
        BigDecimal pendingBalance = user.getPendingBalance();

        // Validar si saldo pendiente es nulo
        if (pendingBalance == null) {
            throw new FieldInvalidException("El saldo pendiente es null!");
        }
        // Validar que el monto no sea mayor q saldo pendiente
        if (paymentDto.getAmount().compareTo(pendingBalance) > 0){
            throw new FieldInvalidException("El pago es mayor al saldo pendiente!");
        }
        // Descontar al usuario el saldo pendiente restando el nuevo monto o pago
        user.setPendingBalance(user.getPendingBalance().subtract(paymentDto.getAmount()));
        userRepository.save(user); // Actualiza el usuario con el nuevo saldo pendiente

        Payment payment = paymentMapper.toPayment(paymentDto); // Mapear el DTO a la entidad
        LocalDateTime date = LocalDateTime.now(); //Obtener la fecha actual
        payment.setDate(date);
        payment.setUser(user);
        return paymentMapper.toPaymentDto(paymentRepository.save(payment));
    }

    @Override
    public List<PaymentDto> getPaymentByUserId(HttpHeaders headers) {
        Long userId = authService.getIdByLoguedUser(headers);
        return paymentMapper.paymentDtos(paymentRepository.findAllPaymentByUserId(userId));
    }
}
