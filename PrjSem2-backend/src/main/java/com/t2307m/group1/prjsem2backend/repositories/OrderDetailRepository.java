package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Order;
import com.t2307m.group1.prjsem2backend.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    Optional<List<OrderDetail>> findByOrderId(int orderId); // Tìm các chi tiết đơn hàng theo orderId
    Optional<List<OrderDetail>> findByCourseId(int courseId); // Tìm các chi tiết đơn hàng theo courseId

}
