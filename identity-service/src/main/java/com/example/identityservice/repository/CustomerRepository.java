package com.example.identityservice.repository;
import com.example.identityservice.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.numberPhone =:numberPhone")
    Customer findByNumberPhone(String numberPhone);

    @Query(value = "select c from Customer c where " +
            "(:search is null or c.name like :search% or c.numberPhone like :search%) and " +
            "(:status is null or c.status = :status)")
    Page<Customer> getAll(Pageable pageable, String search, Integer status);



//    @Query(value = "SELECT new com.tom.restaurant.entity.dto.CustomerResponse(c.id, c.name, c.numberPhone, c.status, c.loyaltyPoints,case when EXISTS (SELECT 1 FROM VoucherCustomer vc " +
//            "WHERE vc.numberPhone = c.numberPhone AND vc.voucherId = :voucherId) then 1 else 0 end AS result) " +
//            "FROM Customer c WHERE c.status = 1 " +
//            "and (:search is null or c.name LIKE :search% OR c.numberPhone LIKE :search%)")
//    List<CustomerResponse> getAllByVoucherId(Pageable pageable,String search , Long voucherId);

    @Modifying
    @Query("UPDATE Customer c SET c.numberPhone = :newNumberPhone WHERE c.numberPhone = :oldNumberPhone")
    void updateNumberPhone(String oldNumberPhone, String newNumberPhone);
}
