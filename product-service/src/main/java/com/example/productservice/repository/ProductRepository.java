package com.example.productservice.repository;


import com.example.productservice.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select p from Product p where " +
            "(:name is null or p.name like CONCAT('%', :name, '%')) and " +
            "(:status is null or p.status = :status)")
    Page<Product> getAll(Pageable pageable , @Param("name") String name ,@Param("status") Integer status);

}
