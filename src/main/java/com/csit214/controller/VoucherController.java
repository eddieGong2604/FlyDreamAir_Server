package com.csit214.controller;

import com.csit214.models.FrequentFlyerAccount;
import com.csit214.models.Voucher;
import com.csit214.payload.ApiResponse;
import com.csit214.payload.VoucherCreateRequest;
import com.csit214.repository.BookingRepository;
import com.csit214.repository.SeatingRepository;
import com.csit214.repository.UserRepository;
import com.csit214.repository.VoucherRepository;
import com.csit214.security.CurrentUser;
import com.csit214.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("api/voucher")
public class VoucherController {
    @Autowired
    private SeatingRepository seatingRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/me")
    public ResponseEntity<?> makeVoucher(@RequestBody VoucherCreateRequest voucherCreateRequest, @CurrentUser UserPrincipal currentUser) {


        FrequentFlyerAccount account = userRepository.findById(currentUser.getId()).orElse(null);
        double points = 0.0;
        try {
            points = voucherCreateRequest.getPoints();
        } catch (Exception e) {
            return new ResponseEntity(new ApiResponse(false, "points must be a number"),
                    HttpStatus.BAD_REQUEST);
        }

        if (points >= 10 && points <= account.getFfpoints()) {

            Voucher voucher = new Voucher("VOUCHER" + voucherRepository.findAll().size(), voucherCreateRequest.getPoints() / 100, true);
            voucher.setAccount(account);
            voucherRepository.save(voucher);

            Set<Voucher> vouchers = account.getVouchers();
            vouchers.add(voucher);
            account.setVouchers(vouchers);

            account.setFfpoints(account.getFfpoints() - voucherCreateRequest.getPoints());
            userRepository.save(account);
            return new ResponseEntity(new ApiResponse(true, "Your Voucher Code is: " + voucher.getVoucherCode()),
                    HttpStatus.valueOf(200));
        } else {
            return new ResponseEntity(new ApiResponse(false, "Your voucher points is less than 10 or it's greater than your current frequent flyer points"), HttpStatus.valueOf(200));
        }

    }

    @GetMapping("/user")
    public Set<Voucher> getUserVoucher(@CurrentUser UserPrincipal currentUser) {
        FrequentFlyerAccount account = userRepository.findById(currentUser.getId()).orElse(null);
        return new HashSet<>(account.getVouchers());
    }


    @PostMapping("/valid/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> setVoucherValidity(@PathVariable Long id) {
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        voucher.setValid(!voucher.isValid());
        voucherRepository.save(voucher);
        return new ResponseEntity(new ApiResponse(true, "Voucher Updated"), HttpStatus.valueOf(200));

    }
}
