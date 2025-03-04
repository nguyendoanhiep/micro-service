package com.example.productservice.repository;


import com.example.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where " +
            "(:name is null or p.name like %:name%) and " +
            "(:status is null or p.status = :status)")
    Page<Product> getAll(Pageable pageable , String name , Integer status);

}
