package com.csit214.controller;

import com.csit214.models.Booking;
import com.csit214.models.FrequentFlyerAccount;
import com.csit214.models.Seating;
import com.csit214.models.Voucher;
import com.csit214.payload.ApiResponse;
import com.csit214.payload.BookingRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private SeatingRepository seatingRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> makeBooking(@RequestBody BookingRequest bookingRequest, @CurrentUser UserPrincipal currentUser) {
        Long seatingId = bookingRequest.getSeatingId();
        String voucherCode = bookingRequest.getVoucherCode();
        Long userId = currentUser.getId();
        Seating seating = seatingRepository.findById(seatingId).orElse(null);
        Voucher voucher = voucherRepository.findByVoucherCode(voucherCode).orElse(null);
        FrequentFlyerAccount account = userRepository.findById(userId).orElse(null);

        Booking booking = new Booking(seating.getPrice() * 10.0 / 100);
        if(voucher != null){
            booking.setVoucher(voucher);
        }
        if (!voucher.isValid()) {
            return new ResponseEntity(new ApiResponse(false, "Voucher code is invalid!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            voucher.setValid(false);
            voucherRepository.save(voucher);
        }

        booking.setAccount(account);
        booking.setSeating(seating);

        Set<Booking> userBooking = account.getBookings();
        userBooking.add(booking);
        account.setBookings(userBooking);
        account.setFfpoints(account.getFfpoints() + booking.getBookingPrice()*10/100);

        bookingRepository.save(booking);
        userRepository.save(account);

        return new ResponseEntity(new ApiResponse(true, "Booking success"),
                HttpStatus.valueOf(200));

    }

}
