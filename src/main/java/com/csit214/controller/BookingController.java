package com.csit214.controller;

import com.csit214.models.*;
import com.csit214.payload.ApiResponse;
import com.csit214.payload.BookingInfoResponse;
import com.csit214.payload.BookingRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> makeBooking(@RequestBody BookingRequest bookingRequest, @CurrentUser UserPrincipal currentUser) {
        Seating seating = seatingRepository.findById(bookingRequest.getSeatingId()).orElse(null);

        Voucher voucher = voucherRepository.findByVoucherCode(bookingRequest.getVoucherCode()).orElse(null);
        FrequentFlyerAccount account = userRepository.findById(currentUser.getId()).orElse(null);
        //if voucher code is not null but not found
        if (bookingRequest.getVoucherCode().length() > 0 && voucher == null) {
            return new ResponseEntity(new ApiResponse(false, "Voucher code is invalid!"),
                    HttpStatus.BAD_REQUEST);
        } else {
            Booking booking = new Booking(seating.getPrice(), seating, account, voucher);
            //if
            double initialPrice = 0;
            double priceAfter = 0;
            if (voucher != null && bookingRequest.getVoucherCode().length() > 0) {
                initialPrice = booking.getBookingPrice();
                booking.applyVoucherCode(voucher, seating);
                priceAfter = booking.getBookingPrice();
                //voucher has been applied
                voucher.setValid(false);
                voucherRepository.save(voucher);
            } else if (bookingRequest.getVoucherCode().length()== 0) {
                initialPrice = booking.getBookingPrice();
                priceAfter = booking.getBookingPrice();
                System.out.println("Works");
            }
            Set<Booking> userBooking = account.getBookings();
            userBooking.add(booking);
            account.setBookings(userBooking);

            double newStatusPoints = booking.getBookingPrice()*10.0/100.0 + account.getStatusPoints();
            double newFFPoints = account.getFfpoints() + booking.getBookingPrice()*10/100 + account.getStatus().getValue();

            account.setStatusPoints(newStatusPoints);
            account.setFfpoints(newFFPoints);

            if (account.getStatusPoints() >= FFType.GOLD.getThreshold()) {
                account.setStatus(FFType.GOLD);
            }
            if (account.getStatusPoints() >= FFType.PLATINUM.getThreshold()) {
                account.setStatus(FFType.PLATINUM);
            }
            bookingRepository.save(booking);
            userRepository.save(account);
            return new ResponseEntity(new BookingInfoResponse(booking, initialPrice,bookingRequest.getVoucherCode(),priceAfter),
                    HttpStatus.valueOf(200));
        }
    }


    @PostMapping("/voucher")
    public ResponseEntity<?> makeVoucher(@RequestBody VoucherCreateRequest voucherCreateRequest, @CurrentUser UserPrincipal currentUser) {
        FrequentFlyerAccount account = userRepository.findById(currentUser.getId()).orElse(null);
        if(voucherCreateRequest.getPoints() >=100 && voucherCreateRequest.getPoints() <= account.getFfpoints()){

            Voucher voucher = new Voucher("VOUCHER" + voucherRepository.findAll().size(),voucherCreateRequest.getPoints() / 100,true);
            voucher.setAccount(account);
            voucherRepository.save(voucher);

            Set<Voucher> vouchers = account.getVouchers();
            vouchers.add(voucher);
            account.setVouchers(vouchers);

            account.setFfpoints(account.getFfpoints() - voucherCreateRequest.getPoints());
            userRepository.save(account);
            return new ResponseEntity(new ApiResponse(true, "Your Voucher Code is: " + voucher.getVoucherCode()),
                    HttpStatus.valueOf(200));
        }
        else{
            return new ResponseEntity(new ApiResponse(false, "Your voucher points is less than 100 or it's greater than your current frequent flyer points"),HttpStatus.BAD_REQUEST);
        }

    }

}
