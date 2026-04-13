package com.coffee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
@Entity
@Table(name = "carts")
public class Cart {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "cart_id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 카트에는 여러 개의 카트 상품들이 담겨야 하므로 List 가 좋습니다.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartProduct> cartProducts;


}
