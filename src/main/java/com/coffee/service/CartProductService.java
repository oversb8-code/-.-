package com.coffee.service;


import com.coffee.entity.CartProduct;
import com.coffee.entity.Product;
import com.coffee.repository.CartProductRepository;
import com.coffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartProductService {
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository; // 유지

    public void saveCartProduct(CartProduct cp){
        this.cartProductRepository.save(cp);
    }

    public String editCartProductQuantity(Long cartProductId, Integer quantity){
        // 1. 수량 검증
        if (quantity == null || quantity < 1){
            return "오류 : 장바구니 품목은 최소 1개 이상이어야 합니다.";
        }

        // 2. 해당 카트 상품 찾기 (타입을 CartProduct로 수정)
        Optional<CartProduct> cartProductOptional = cartProductRepository.findById(cartProductId);
        if(cartProductOptional.isEmpty()){
            return "오류 : 카트 품목을 찾을 수 없습니다.";
        }

        CartProduct cartProduct = cartProductOptional.get();

        // 3. 재고 수량 검증 및 수량 변경
        int stock = cartProduct.getProduct().getStock();
        if(quantity > stock){
            return "오류 : 재고 수량이 부족합니다. (현재 재고: " + stock + ")";
        }

        cartProduct.setQuantity(quantity);
        cartProductRepository.save(cartProduct);

        return "카트 상품 아이디 " + cartProductId + "번이 " + quantity + "개로 수정되었습니다.";
    }

    public void deleteCartProductById(Long cartProductId){
        cartProductRepository.deleteById(cartProductId);
    }
}
