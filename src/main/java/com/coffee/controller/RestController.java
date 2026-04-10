package com.coffee.controller;

import com.coffee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private ProductService productService;
}
