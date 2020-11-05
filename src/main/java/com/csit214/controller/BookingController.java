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
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> makeBooking(@RequestBody BookingRequest bookingRequest, @CurrentUser UserPrincipal currentUser) {
        Seating seating = seatingRepository.findById(bookingRequest.getSeatingId()).orElse(null);

        Voucher voucher = voucherRepository.findByVoucherCode(bookingRequest.getVoucherCode()).orElse(null);
        System.out.println(voucher == null);
        FrequentFlyerAccount account = userRepository.findById(currentUser.getId()).orElse(null);
        //if voucher code is not null but not found
        if ((bookingRequest.getVoucherCode().length() > 0 && voucher == null) ) {
            return new ResponseEntity(new ApiResponse(false, "Voucher code is invalid!"),
                    HttpStatus.valueOf(200));
        }
        if(!voucher.isValid()){
            return new ResponseEntity(new ApiResponse(false, "Voucher code has been used!"),
                    HttpStatus.valueOf(200));
        }

        else {
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

            double statusPointsAdded = booking.getBookingPrice()*1.0/25;
            double newStatusPoints = statusPointsAdded + account.getStatusPoints();

            double ffpointsAdded = booking.getBookingPrice()*1.0/25 + account.getStatus().getValue();
            double newFFPoints = account.getFfpoints() + ffpointsAdded;

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
            if(voucher != null){
                voucher.setValid(false);
                voucherRepository.save(voucher);
            }
            return new ResponseEntity(new BookingInfoResponse(booking, initialPrice,bookingRequest.getVoucherCode(),priceAfter, statusPointsAdded, ffpointsAdded),
                    HttpStatus.valueOf(200));
        }
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")

    public Set<Booking> getUserBookings(@CurrentUser UserPrincipal currentUser) {
        FrequentFlyerAccount account = userRepository.findById(currentUser.getId()).orElse(null);
        return new HashSet<>(account.getBookings());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        try{
            Booking booking = bookingRepository.findById(id).orElse(null);
            FrequentFlyerAccount frequentFlyerAccount =
            booking.getAccount();

            bookingRepository.deleteById(id);
            frequentFlyerAccount.setFfpoints(
                    frequentFlyerAccount.getFfpoints() -
                            booking.getBookingPrice()/25
            );
            userRepository.save(frequentFlyerAccount);

            return new ResponseEntity(new ApiResponse(true, "delete sucessful"),
                    HttpStatus.valueOf(200));
        }
        catch (Exception e){
            return new ResponseEntity(new ApiResponse(false, "delete unsucessful"),
                    HttpStatus.valueOf(200));
        }
    }
}
