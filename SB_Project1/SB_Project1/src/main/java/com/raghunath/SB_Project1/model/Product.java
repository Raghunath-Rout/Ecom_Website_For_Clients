package com.raghunath.SB_Project1.model;

import jakarta.persistence.*;
import lombok.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    // use name as the frontend name (compulsory)

    @Id // primary key of the table od this class
    @GeneratedValue(strategy = GenerationType.IDENTITY) // it'll auto-generate
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;

    private String imageName;
    private String imageType;
    @Lob // used for large object (to store in the db)
    private byte[] imageData;
}
