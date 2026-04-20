package com.coffee.repository;

import com.coffee.constant.OrderStatus;
import com.coffee.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 회원 + 주문상태 조회
    List<Order> findByMemberIdAndOrderStatusOrderByIdDesc(Long memberId, OrderStatus status);

    // 주문상태 전체 조회
    List<Order> findByOrderStatusOrderByIdDesc(OrderStatus status);

    // ✅ 주문 상태 업데이트 (수정 완료)
    @Modifying
    @Transactional
    @Query("update Order o set o.orderStatus = :status where o.id = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId,
                          @Param("status") OrderStatus status);
}