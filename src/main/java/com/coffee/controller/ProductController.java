package com.coffee.controller; // 2026.04.10 오후

import com.coffee.entity.Product;
import com.coffee.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> list() {
        return this.productService.getProductList();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            boolean isDeleted = this.productService.deleteProduct(id);
            if (isDeleted) {
                return ResponseEntity.ok(id + "번 상품이 삭제되었습니다.");
            } else {
                return ResponseEntity.badRequest().body(id + "번 상품이 존재하지 않습니다.");
            }
        } catch (DataIntegrityViolationException err) {
            String message = "해당 상품은 장바구니에 포함되어 있거나, 이미 매출이 발생한 상품입니다. 확인해주세요.";
            return ResponseEntity.badRequest().body(message);
        } catch (Exception err) {
            return ResponseEntity.internalServerError().body("오류 발생 :" + err.getMessage());
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@Valid @RequestBody Product product,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError xx : bindingResult.getFieldErrors()) {
                errors.put(xx.getField(), xx.getDefaultMessage());
            }
            return new ResponseEntity<>(
                    Map.of(
                            "message", "상품 등록 유효성 검사에 문제가 있습니다.",
                            "errors", errors
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        try {
            Product savedProduct = this.productService.insertProduct(product);

                    if(savedProduct == null){
                return ResponseEntity
                        .status(500)
                        .body(
                                Map.of("message", "상품 등록에 실패하였습니다.",
                                        "error", "bad image file format"
                                )
                        );

            }
                    return  ResponseEntity.ok(
                    Map.of(
                            "message", "상품이 성공적으로 등록되었습니다.",
                            "image", savedProduct.getImage()
                    )
            );
        } catch (IllegalStateException err) { // 경로 또는 이미지 저장 문제
            return ResponseEntity
                    .status(500)
                    .body(
                            Map.of("message", err.getMessage(),
                                    "error", "File save Error")
                    );

        } catch (Exception err) { // 데이터 베이스 오류
            return ResponseEntity
                    .status(500)
                    .body(
                            Map.of("message", err.getMessage(),
                                    "error", "Internal Server Error")
                    );
        }
    }
}