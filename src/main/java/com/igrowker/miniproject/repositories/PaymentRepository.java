package com.igrowker.miniproject.repositories;

import com.igrowker.miniproject.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public List<Payment> findAllPaymentByUserId(Long UserId);

}
