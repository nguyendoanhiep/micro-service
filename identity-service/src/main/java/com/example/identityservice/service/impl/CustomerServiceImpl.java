package com.example.identityservice.service.impl;

import com.example.identityservice.dto.request.CustomerRequest;
import com.example.identityservice.dto.request.OrdersRequest;
import com.example.identityservice.entity.Customer;
import com.example.identityservice.repository.CustomerRepository;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Page<Customer> getAll(Pageable pageable, String search, Integer status) {
        return customerRepository.getAll(pageable, search, status);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Customer addOrUpdate(CustomerRequest request) {
        Customer customer = request.getId() == null ? new Customer()
                : customerRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException(""));
        if (request.getId() != null) {
            userRepository.updateNumberPhone(customer.getNumberPhone(), request.getNumberPhone());
        }
        customer.setId(request.getId());
        customer.setName(request.getName());
        customer.setNumberPhone(request.getNumberPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setStatus(request.getStatus());
        customer.setLoyaltyPoints(request.getLoyaltyPoints() == null ? 0 : request.getLoyaltyPoints());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setStatus(request.getStatus());
        customer.setCreateDate(request.getId() == null ? new Date() : customer.getCreateDate());
        customer.setModifiedDate(new Date());
        return customerRepository.save(customer);
    }

    @Override
    public Long createOrUpdatePoint(OrdersRequest request) {
        Customer res;
        Customer customer = customerRepository.findByNumberPhone(request.getNumberPhone());
        if (customer == null) {
            res = customerRepository.save(Customer
                    .builder()
                    .id(null)
                    .numberPhone(request.getNumberPhone())
                    .status(1)
                    .loyaltyPoints(0L)
                    .createDate(new Date())
                    .modifiedDate(new Date())
                    .build());
        } else {
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + (request.getTotalValue()) / 100);
            res = customerRepository.save(customer);
        }
        return res.getId();
    }

    @Override
    public Boolean delete(Long id) {
        customerRepository.deleteById(id);
        return true;
    }

}
