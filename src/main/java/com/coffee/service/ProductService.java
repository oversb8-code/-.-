package com.coffee.service;

import com.coffee.entity.Product;
import com.coffee.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductList(){
        return this.productRepository.findProductByOrderByIdDesc();
    }

    @Value("${productImageLocation}")
    private String productImageLocation; // 기본 값 : null

    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if(product == null){
            return false;
        }

        String fileName = product.getImage();
        if(fileName != null && !fileName.isEmpty()){
            File file = new File(productImageLocation + fileName);

            System.out.println("삭제될 파일 이름");
            System.out.println(file.getAbsoluteFile());

            if (file.exists()){
                boolean deleted = file.delete();

                if(!deleted) {
                    System.out.println("이미지 삭제 실패");
                }

            }
        }
        productRepository.deleteById(id);
        return true;
    }


    private String saveProductImage(String base64Image){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = LocalDateTime.now().format(formatter);
        //UUID 클래스 공부

        String imageFileName = "Product_" + formattedNow + ".jpg";

        File imageFile = new File(productImageLocation + imageFileName);
        System.out.println("등록할 이미지 이름");
        System.out.println(imageFile.getAbsoluteFile());

        byte[] decodedImage = Base64.getDecoder().decode(base64Image.split(",")[1]);

        try{
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(decodedImage); //c:\shop\images 복사
            return imageFileName;
        }catch(Exception e) {
            throw new IllegalStateException("이미지 파일 저장 중 오류가 발생");
        }
    }

    public Product insertProduct(Product product){
        //product는 리액트에서 넘어온 상품 등록을 위한 정보입니다.
        if ((product.getImage() != null && product.getImage().startsWith("data:image"))) {
            String imageFileName = this.saveProductImage(product.getImage());

            //데이터베이스에는 product_년월일시분초.jpg 형식으로 저장되어야 합니다
            product.setImage(imageFileName);

            product.setInputdate(LocalDate.now());
            System.out.println("서비스 크랠스에서 상품 등록 정보 확인");
            System.out.println(product);

            return this.productRepository.save(product); //데이터베이스에 추가하기

        }else{
            return null;
        }

    }

    public Product getProductById(Long id){
        Optional<Product> product = this.productRepository.findById(id);

        return product.orElse(null);
    }

    public Optional<Product> findById(Long id){
        return this.productRepository.findById(id);
    }
    private void deleteOldImage(String oldImageFileName) {
        if (oldImageFileName == null || oldImageFileName.isBlank()){
            return ;
        }
        File oldImageFile = new File(productImageLocation + oldImageFileName);

        if(oldImageFile.exists()){
            boolean deleted = oldImageFile.delete();
            if(!deleted) {
                System.out.println("기존 이미지 삭제 실패 : " + oldImageFileName);
            }
        }
    }
    public Product updateProduct(Product savedProduct, Product updatedProduct) {
        savedProduct.setName(updatedProduct.getName());
        savedProduct.setPrice(updatedProduct.getPrice());
        savedProduct.setCategory(updatedProduct.getCategory());
        savedProduct.setStock(updatedProduct.getStock());
        savedProduct.setDescription(updatedProduct.getDescription());

        if (updatedProduct.getImage() != null && updatedProduct.getImage().startsWith("data:image")) {
            deleteOldImage(savedProduct.getImage());
            String imageFileName = saveProductImage(updatedProduct.getImage());
            savedProduct.setImage(imageFileName);
        }

        return productRepository.save(savedProduct);
    }

    public Optional<Product> findProductById(Long productId) {
        return productRepository.findById(productId);
    }


}
