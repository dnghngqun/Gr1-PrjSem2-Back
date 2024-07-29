
package com.t2307m.group1.prjsem2backend.repositories;
import com.t2307m.group1.prjsem2backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository <Payment, Integer>
{
    Optional<Payment> findByPaymentId(String paymentId);

    //OrderById: sắp xếp theo id giảm (desc)
    List<Payment> findAllByOrderByIdDesc();

    @Query("SELECT p FROM Payment p WHERE YEAR(p.paymentDate) = :year AND MONTH(p.paymentDate) = :month")
    List<Payment> findPaymentsByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT YEAR(p.paymentDate) AS year, SUM(p.amount) AS totalRevenue " +
            "FROM Payment p " +
            "GROUP BY YEAR(p.paymentDate)")
    List<Map<String, Object>> getYearlyBreakup();

    @Query("SELECT MONTH(p.paymentDate) AS month, SUM(p.amount) AS totalRevenue " +
            "FROM Payment p " +
            "WHERE YEAR(p.paymentDate) = :year " +
            "GROUP BY MONTH(p.paymentDate)")
    List<Map<String, Object>> getMonthlyEarningsByYear(@Param("year") int year);
}