package com.coffee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
// @Data는
// @Getter, @Setter, @Tostring
// @EqualsAndHashCode @RequiredArgsConstructor 한번에.

@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto { // 주문 내역 1개를 표현하기 위한 자바 클래스
    private Long orderId;
    private String name;
    private LocalDate orderDate;
    private String status; // 주문 상태를 의미하는 문자열

    private List<OrderItem> orderItems;

    @Data
    @AllArgsConstructor
    public static class OrderItem{
        private String productName; // 상품명
        private int quantity; // 주문 수량

    }

}
