package com.coffee.controller;

import com.coffee.dto.CartItemDto;
import com.coffee.dto.CartProductDto;
import com.coffee.entity.Member;
import com.coffee.service.CartProductService;
import com.coffee.service.CartService;
import com.coffee.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/insert")
    public ResponseEntity<String> addToCart(@RequestBody CartProductDto dto, Authentication
            authentication){

        String email = authentication.getName();
        String message = cartService.addProductToCart(dto, email);

        return ResponseEntity.ok(message);
    }

    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<List<CartItemDto>> getCartProducts(Authentication authentication) {
        String email = authentication.getName();

        Member member = memberService.findByEmail(email);

        if(member == null){
            new RuntimeException("사용자가 존재하지 않습니다.");
        }
        return ResponseEntity.ok(cartService.getCartItemsByMemberId(member.getId()));
    }

    private final CartProductService cartProductService;

@PatchMapping("/edit{cartProductId}")
    public ResponseEntity<String> editCartProductQuantity(
            @PathVariable Long cartProductId,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Long cartProductId){
    System.out.println("카트 상품 아이디 : "+ cartProductId);
    System.out.println("변경할 갯수 "+ quantity);
        System.out.println("상품아이디 "+ productId);

    String message = cartProductService.editCartProductQuantity(cartProductId,quantity,productId);
    if(message.startsWith("오류")){
        return ResponseEntity.badRequest().body(message);
    }else {
        return ResponseEntity.ok(message);
    }

    }
    public ResponseEntity<String> deleteCartProduct(@PathVariable Long cartProductId){
    return null;
    }

}
