package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByAccount_Id(int id); // Tìm các đơn hàng theo userId
    Optional<List<Order>> findByStatus(int status); // Tìm các đơn hàng theo trạng thái
    boolean existsByAccountId(int Id);
    void deleteByAccount_Id(int Id);

}
