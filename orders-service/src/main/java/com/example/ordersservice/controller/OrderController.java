package com.example.ordersservice.controller;

import com.example.ordersservice.dto.ApiResponse;
import com.example.ordersservice.dto.request.OrdersRequest;
import com.example.ordersservice.entity.Orders;
import com.example.ordersservice.repository.OrderRepository;
import com.example.ordersservice.service.OrderService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    ExecutorService executor = Executors.newFixedThreadPool(10);


    @GetMapping("/getAll")
    public ApiResponse<?> getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam String fromDate,
                                 @RequestParam String toDate,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(required = false) Integer status
    ) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date from  = sdf.parse(fromDate);
        Date to  = sdf.parse(toDate);
        return ApiResponse.SUCCESS(orderService.getAll(PageRequest.of(page - 1, size), search, status , from , to));
    }

    @PostMapping("/addOrUpdate")
    public ApiResponse<?> save(@RequestBody OrdersRequest request) {
        return ApiResponse.SUCCESS(orderService.save(request));
    }

    @GetMapping("/autoGenOrders")
    public ApiResponse<?> autoGenOrders(@RequestParam Long totalRecord ,@RequestParam String  createDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = sdf.parse(createDate);
        return ApiResponse.SUCCESS(orderService.autoGenOrders(totalRecord,date));
    }

    @PostMapping("/changeStatus")
    public ApiResponse<?> changeStatus(@RequestParam Long id) {
        return ApiResponse.SUCCESS(orderService.changeStatus(id));
    }

    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {

        // ✅ Chặn cache của trình duyệt
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // ✅ Header tải file Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=orders.xlsx");

        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        Sheet sheet = workbook.createSheet("Orders");

        // Header dòng đầu tiên
        Row header = sheet.createRow(0);
        String[] headers = {"ID", "Tên KH", "Mã đơn", "SĐT", "Voucher", "Giá gốc", "Giảm giá", "Tổng", "Trạng thái", "Ngày tạo"};
        for (int i = 0; i < headers.length; i++) {
            header.createCell(i).setCellValue(headers[i]);
        }

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        int pageSize = 100000;
        // Ghi dữ liệu
        for (int i = 0; i < 10; i++) {
            final int pageIndex = i;

            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                Page<Orders> orders = orderRepository.findAll(PageRequest.of(pageIndex, pageSize));
                return orders.getContent();
            }, executor).thenAccept(orders -> {
                synchronized (sheet) {
                    int rowIdx = pageIndex * pageSize + 1; // +1 vì dòng 0 là header

                    for (Orders o : orders) {
                        Row row = sheet.createRow(rowIdx++);
                        row.createCell(0).setCellValue(o.getId());
                        row.createCell(1).setCellValue(""); // Tên KH
                        row.createCell(2).setCellValue(o.getCode());
                        row.createCell(3).setCellValue(""); // SĐT
                        row.createCell(4).setCellValue(""); // Voucher
                        row.createCell(5).setCellValue(o.getOriginalTotalValue());
                        row.createCell(6).setCellValue(o.getDiscountAmount());
                        row.createCell(7).setCellValue(o.getTotalValue());
                        row.createCell(8).setCellValue(o.getStatus());
                        row.createCell(9).setCellValue(o.getCreateDate().toString());
                    }
                }
            });

            futures.add(future);
        }

// Chờ tất cả hoàn thành
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();

//        int rowIdx = 1;
//        for (Orders o : orders) {
//            Row row = sheet.createRow(rowIdx++);
//            row.createCell(0).setCellValue(o.getId());
//            row.createCell(1).setCellValue(""); // Tên KH
//            row.createCell(2).setCellValue(o.getCode());
//            row.createCell(3).setCellValue(""); // SĐT
//            row.createCell(4).setCellValue(""); // Voucher
//            row.createCell(5).setCellValue(o.getOriginalTotalValue());
//            row.createCell(6).setCellValue(o.getDiscountAmount());
//            row.createCell(7).setCellValue(o.getTotalValue());
//            row.createCell(8).setCellValue(o.getStatus());
//            row.createCell(9).setCellValue(o.getCreateDate().toString());
//        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] data = bos.toByteArray();
        response.setContentLength(data.length);
        // Ghi ra stream
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        workbook.dispose();
    }
}

