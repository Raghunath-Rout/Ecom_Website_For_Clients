package com.raghunath.SB_Project1.controller;


import com.raghunath.SB_Project1.model.Product;
import com.raghunath.SB_Project1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    /***********    Must Read The Comment Lines in below    ************/

       @RequestMapping("/")
       public String greet(){
           return "Hello World";
       }

       @Autowired
       ProductService service;

       @GetMapping("/products")
       public ResponseEntity<List<Product>> getAllProducts(){
           List<Product> plist = service.getAllProducts();
           return new ResponseEntity<>(plist, HttpStatus.OK);
       }

       @GetMapping("/product/{pid}")
       public ResponseEntity<Product> getProductById(@PathVariable int pid){

           Product pdct = service.getProductById(pid);
           if(pdct != null)
               return new ResponseEntity<>(pdct,HttpStatus.OK);
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       @PostMapping("/product")
       public ResponseEntity<?> addProduct(@RequestPart Product product,
                                           @RequestPart MultipartFile imageFile){

         // Here in the above parameter it is compulsory to write the,
         // "product" && "imageFile" , otherwise it'll not work
         // Because I think it target the object by it's "beanName"
           try{
               Product pro = service.addProduct(product, imageFile);
               return new ResponseEntity<>(pro, HttpStatus.CREATED);
           }
           catch(Exception e) {
               return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
           }
       }

       @GetMapping("/product/{pid}/image")
       public ResponseEntity<byte[]> getImageById(@PathVariable int pid) {
           Product product = service.getProductById(pid);
           if(product != null) {
               byte[] imageFile = product.getImageData();
               //  return ResponseEntity.ok()
               //          .contentType(MediaType.valueOf(product.getImageType()))
               //          .body(imageFile);

               // You can Actually send the imageData without it's type

               return ResponseEntity.ok().body(imageFile);
           }
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       @PutMapping("/product/{pid}")
       public ResponseEntity<String> updateProduct(@PathVariable int pid,
                                              @RequestPart Product product,
                                              @RequestPart MultipartFile imageFile) throws IOException {
           Product product1 = null;
           product1 = service.updateProductById(pid, product, imageFile);
           if (product1 != null) {
               return new ResponseEntity<>("Update Success", HttpStatus.OK);
           }
           return new ResponseEntity<>("Filed to Update", HttpStatus.BAD_REQUEST);
       }

       @DeleteMapping("/product/{pid}")
       public ResponseEntity<String> deleteProduct(@PathVariable int pid){
           Product p = service.getProductById(pid);
           if(p != null) {
               service.deleteProduct(pid);
               return new ResponseEntity("Mission Success", HttpStatus.FOUND);
           }
           return new ResponseEntity<>("Mission Failed", HttpStatus.NO_CONTENT);
       }

       @GetMapping("/products/search")
       public ResponseEntity<List<Product>> searchProducts(@RequestParam
                                                                String keyword){
           System.out.println("Searching with "+keyword);
            List<Product> products = service.searchProducts(keyword);
            if(products != null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(products, HttpStatus.FOUND);
       }
}
