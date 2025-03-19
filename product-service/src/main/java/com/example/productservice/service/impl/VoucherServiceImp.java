package com.example.productservice.service.impl;

import com.example.productservice.dto.request.AssignCusRequest;
import com.example.productservice.dto.request.VoucherRequest;
import com.example.productservice.entity.Voucher;
import com.example.productservice.entity.VoucherCustomer;
import com.example.productservice.repository.VoucherCustomerRepository;
import com.example.productservice.repository.VoucherRepository;
import com.example.productservice.service.VoucherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class VoucherServiceImp implements VoucherService {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherCustomerRepository voucherCustomerRepository;

    @Override
    public Page<Voucher> getAll(Pageable pageable, String search, Integer status) {
        return voucherRepository.getAll(pageable, search, status);
    }

    @Override
    public List<Long> getCustomerIdsById(Long voucherId) {
        return voucherCustomerRepository.getAllCustomerIdsById(voucherId);
    }

    @Override
    public Boolean save(VoucherRequest request) {
            Voucher voucher = request.getId() == null ? new Voucher() : voucherRepository.findById(request.getId()).get();
            voucher.setId(request.getId());
            voucher.setCode(request.getId() == null ? generateRandomCode() : request.getCode());
            voucher.setName(request.getName());
            voucher.setValue(request.getValue());
            voucher.setQuantity(request.getQuantity());
            voucher.setStatus(request.getStatus());
            voucher.setStartDate(request.getStartDate());
            voucher.setExpirationDate(request.getExpirationDate());
            voucher.setCreateDate(request.getId() == null ? new Date() : voucher.getCreateDate());
            voucher.setModifiedDate(new Date());
            voucherRepository.save(voucher);
            return true;

    }

    @Override
    @Transactional
    public Boolean AssignCus(AssignCusRequest assignCusRequest) {
            voucherCustomerRepository.deleteAllByVoucherId(assignCusRequest.getVoucherId());
            List<VoucherCustomer> voucherCustomers = assignCusRequest.getCustomerIds().stream().map(customerId -> {
                VoucherCustomer voucherCustomer = new VoucherCustomer();
                voucherCustomer.setVoucherId(assignCusRequest.getVoucherId());
                voucherCustomer.setCustomerId(customerId);
               return voucherCustomer;
            }).toList();
            voucherCustomerRepository.saveAll(voucherCustomers);
            return true;
    }

    @Override
    public Boolean delete(Long id) {
        voucherRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Voucher> getByCustomerId(Long customerId) {
        return voucherRepository.getByCustomerId(customerId);
    }

    @Override
    public Voucher findById(Long id) {
        return voucherRepository.findById(id).get();
    }

    @Override
    public Long useVoucher(Long id) {
        Optional<Voucher> voucher = voucherRepository.findById(id);
        voucher.ifPresent(entity -> {
            if (entity.getQuantity() > 0) {
                entity.setQuantity(entity.getQuantity() - 1);
                if (entity.getQuantity() == 0) {
                    entity.setStatus(2);
                }
                voucherRepository.save(entity);
            }
        });
        return voucher.get().getId();
    }

    private String generateRandomCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(candidateChars.length());
            sb.append(candidateChars.charAt(index));
        }
        return sb.append(timestamp).toString();
    }
}
