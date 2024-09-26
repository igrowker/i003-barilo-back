package com.igrowker.miniproject.mappers;

import com.igrowker.miniproject.dtos.PaymentDto;
import com.igrowker.miniproject.models.Payment;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMapper {

    private static final ModelMapper modelMapper = new ModelMapper();
    /*
    static {
        // Definir mapeo personalizado para userId
        modelMapper.addMappings(new PropertyMap<Payment, PaymentDto>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId()); // Mapea Payment.user.id a PaymentDto.userId
            }
        });
    }*/

    public  PaymentDto toPaymentDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }

    public Payment toPayment(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, Payment.class);
    }

    public List<PaymentDto> paymentDtos(List<Payment> payments) {
        return payments.stream()
                .map(payment -> modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());
    }

}
