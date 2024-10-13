package com.raghunath.SB_Project1.service;

import com.raghunath.SB_Project1.model.Product;
import com.raghunath.SB_Project1.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getProductById(int pid){
        return repo.findById(pid).orElse(null);
    }

    public Product addProduct(Product p, MultipartFile image) throws IOException{
        p.setImageName(image.getOriginalFilename());
        p.setImageType(image.getContentType());
        p.setImageData(image.getBytes());
        return repo.save(p);
    }

    public Product updateProductById(int pid, Product product, MultipartFile imageFile) throws IOException{
        // Here the can be used for security purpose
        // because by the save() if the product is not present
        // it'll create a new product

        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int pid) {
        repo.deleteById(pid);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
