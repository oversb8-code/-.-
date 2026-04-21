package com.coffee.repository;

import com.coffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByOrderByIdDesc();

    List<Product> findByImageContaining(String keyword);


}
// 특정한 데이터만 들어올 수 있도록 하는 <> 제너릭 기법.