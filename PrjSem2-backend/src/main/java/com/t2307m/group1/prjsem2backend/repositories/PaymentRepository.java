
package com.t2307m.group1.prjsem2backend.repositories;
import com.t2307m.group1.prjsem2backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository <Payment, Integer>
{
    Optional<Payment> findByPaymentId(String paymentId);
}